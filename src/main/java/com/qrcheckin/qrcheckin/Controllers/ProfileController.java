package com.qrcheckin.qrcheckin.Controllers;

import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Records.FlashMessage;
import com.qrcheckin.qrcheckin.Requests.Profile.UpdateProfileRequest;
import com.qrcheckin.qrcheckin.Services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @GetMapping("/dashboard/profile")
    public String index (){
        return "views/dashboard/profile/index";
    }

    @GetMapping("/dashboard/profile/edit")
    public String edit (@AuthenticationPrincipal User user, Model model) {
       if(!model.containsAttribute("updateProfileRequest")){
           model.addAttribute("updateProfileRequest", new UpdateProfileRequest(user));
       }
        return "views/dashboard/profile/edit";
    }

    @PostMapping("/dashboard/profile/update")
    public String update (@Valid @ModelAttribute("updateProfileRequest") UpdateProfileRequest request,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateProfileRequest", request);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateProfileRequest", bindingResult);
            return "redirect:/dashboard/profile/edit";
        }

        this.profileService.updateProfile(request);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(
                        FlashMessage.FlashStatuses.SUCCESS,
                        "Profile updated successfully!")
        );
        return "redirect:/dashboard/profile";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
