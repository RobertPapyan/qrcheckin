package com.qrcheckin.qrcheckin.Controllers;

import com.qrcheckin.qrcheckin.Records.FlashMessage;
import com.qrcheckin.qrcheckin.Requests.Auth.RegisterRequest;
import com.qrcheckin.qrcheckin.Services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Stream;

@Controller
public class AuthController {

    AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "views/auth/login";
    }

    @GetMapping("/auth/register")
    public String registerPage(Model model){
        if(!model.containsAttribute("registerRequest")){
            model.addAttribute("registerRequest", new RegisterRequest());
        }
        return "views/auth/register";
    }

    @PostMapping("/auth/register")
    public String register(@Valid @ModelAttribute("registerRequest") RegisterRequest request,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerRequest", request);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerRequest", bindingResult);
            return "redirect:/auth/register";
        }

        this.authService.storeUser(request);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(
                        FlashMessage.FlashStatuses.SUCCESS,
                        "You have been registered successfully!")
        );
        return "redirect:/auth/login";
    }
}
