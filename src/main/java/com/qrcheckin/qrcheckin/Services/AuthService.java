package com.qrcheckin.qrcheckin.Services;

import com.qrcheckin.qrcheckin.Config.AppConfig;
import com.qrcheckin.qrcheckin.Helpers.Hasher;
import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import com.qrcheckin.qrcheckin.Requests.Auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class AuthService {


    private final Hasher hasher;
    private final AppConfig config;
    private final  UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(Hasher hasher, AppConfig config, UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.hasher = hasher;
        this.config = config;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void storeUser(RegisterRequest request){
        var image = request.getImage();
        String image_path = null;
       ;
        if(!Objects.isNull(image) && !image.isEmpty()){
           try{
               String originalFilename = image.getOriginalFilename();
               String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

               String hashedName = hasher.hash(originalFilename + System.currentTimeMillis());
               image_path = hashedName + extension;

               image.transferTo(new File(config.getPath() + "/src/main/resources/static/public/images/" + image_path));

           }catch (IOException e){
               System.out.println(request.getEmail() + " : users image failed to upload");
           }
        }

        var user = User.builder()
                    .name(request.getName())
                    .surname(request.getSurname())
                    .email(request.getEmail())
                    .image(image_path)
                    .enabled(true)
                    .apiKey(null)
                    .rememberToken(null)
                    .password(this.passwordEncoder.encode(request.getPassword()))
                    .build();

        userRepo.save(user);

    }
}
