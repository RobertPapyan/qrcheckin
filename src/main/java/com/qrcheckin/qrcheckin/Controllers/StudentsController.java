package com.qrcheckin.qrcheckin.Controllers;

import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Requests.StudentsDataRequest;
import com.qrcheckin.qrcheckin.Services.StudentsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class StudentsController {
    private final StudentsService studentsService;

    public StudentsController(StudentsService studentsService){
        this.studentsService = studentsService;
    }

    @GetMapping("/dashboard/students")
    public String index(@ModelAttribute StudentsDataRequest request,
                        HttpSession session, Model model, @AuthenticationPrincipal User user) {

        var paginatedData = this.studentsService.paginate(user,request,session);

        model.addAttribute("students",paginatedData);

        return "views/dashboard/students/index";
    }
}
