package com.qrcheckin.qrcheckin.Controllers;


import com.qrcheckin.qrcheckin.Records.FlashMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index (Model model){
        return "views/index";
    }

}
