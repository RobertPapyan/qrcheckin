package com.qrcheckin.qrcheckin.Controllers;

import com.qrcheckin.qrcheckin.Exception.DashboardException;
import com.qrcheckin.qrcheckin.Helpers.Hasher;
import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Records.FlashMessage;
import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import com.qrcheckin.qrcheckin.Services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.UUID;

@Controller
public class ApiKeyController {

    private final CustomUserDetailService userDetailService;
    private final UserRepository userRepo;

    @Autowired
    public ApiKeyController(UserRepository userRepo, CustomUserDetailService userDetailService){
        this.userRepo = userRepo;
        this.userDetailService = userDetailService;
    }

    @GetMapping("dashboard/api-key")
    public String index (@AuthenticationPrincipal User user, Model model){
        var apiKey = user.getApiKey();
        model.addAttribute("apiKey",apiKey);
        return "views/dashboard/api-key/index";
    }

    @PostMapping("dashboard/generate-key")
    public String generateKey(@AuthenticationPrincipal User user, Hasher hasher,
                              RedirectAttributes redirectAttributes){

        var hash = hasher.hash(Long.toString(new Date().getTime()));
        byte attempts = 0;

        while (this.userRepo.existsByApiKey(hash) ){
            if(attempts == 10) throw new DashboardException("Could not generate api-key for user:" + user.getEmail(),"Can not generate api-key, try later");
            hash = hasher.hash(Long.toString(new Date().getTime()) + UUID.randomUUID());
            attempts++;
        }

        user.setApiKey(hash);
        this.userRepo.save(user);

        this.userDetailService.refreshUserDetails();

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(FlashMessage.FlashStatuses.SUCCESS,"Enjoy your key!")
        );

        return "redirect:/dashboard/api-key";
    }
}
