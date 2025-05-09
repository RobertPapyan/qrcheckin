package com.qrcheckin.qrcheckin.Services;

import com.qrcheckin.qrcheckin.Config.AppConfig;
import com.qrcheckin.qrcheckin.Exception.DashboardException;
import com.qrcheckin.qrcheckin.Helpers.Hasher;
import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import com.qrcheckin.qrcheckin.Requests.Profile.UpdateProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ProfileService {
    private final Hasher hasher;
    private final AppConfig config;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userDetailService;

    @Autowired
    public ProfileService(Hasher hasher, AppConfig config, UserRepository userRepo, PasswordEncoder passwordEncoder, CustomUserDetailService userDetailService){
        this.hasher = hasher;
        this.config = config;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    public void updateProfile(UpdateProfileRequest request) throws DashboardException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        var principal = (User) auth.getPrincipal();

        var user = this.userRepo.findByEmail(principal.getEmail())
                    .orElseThrow(() -> new DashboardException("User not found", "Cant find your account"));

        var image = request.getImage();

        if((image != null && !image.isEmpty()) || request.getDelete_image()) {

            var currentPath = user.getImage();
            if (currentPath != null) {
                var currImage = new File(config.getPublicPath() + "/images/" + currentPath);
                try {
                    Files.delete(currImage.toPath());
                    user.setImage(null);
                } catch (IOException e) {
                    System.out.println("Could not delete old profile image:" + e.getMessage());
                }

            }

            if (!image.isEmpty()) {
                try {
                    String originalFilename = image.getOriginalFilename();
                    var dotIndex = originalFilename.lastIndexOf(".");

                    String extension = dotIndex != -1 ? originalFilename.substring(dotIndex) : "";

                    String hashedName = hasher.hash(originalFilename + System.currentTimeMillis());
                    var image_path = hashedName + extension;

                    image.transferTo(new File(config.getPublicPath() + "/images/" + image_path));
                    user.setImage(image_path);
                } catch (IOException e) {
                    var dashboardException  = new DashboardException(
                            request.getEmail() + " : users image failed to upload",
                            "Failed to upload image"
                    );
                    dashboardException.initCause(e);
                    throw dashboardException;
                }
            }
        }

        user.setSurname(request.getSurname());
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        if(request.getPassword() != null){
            user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        }

        userRepo.save(user);

        this.userDetailService.refreshUserDetails();
    }
}
