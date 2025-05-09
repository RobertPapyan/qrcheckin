package com.qrcheckin.qrcheckin.Controllers;

import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Requests.DashboardDataRequest;
import com.qrcheckin.qrcheckin.Requests.StudentsDataRequest;
import com.qrcheckin.qrcheckin.Services.GroupService;
import com.qrcheckin.qrcheckin.Services.StudentsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping("/dashboard/groups")
    public String index(@ModelAttribute DashboardDataRequest request,
                        HttpSession session, Model model, @AuthenticationPrincipal User user) {

        var paginatedData = this.groupService.paginate(user,request,session);

        model.addAttribute("groups",paginatedData);

        return "views/dashboard/groups/index";
    }
}
