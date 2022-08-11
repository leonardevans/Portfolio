package com.lemutugi.controller.admin;


import com.lemutugi.payload.request.DashboardRequest;
import com.lemutugi.service.RoleService;
import com.lemutugi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@RequestMapping("/admin/*")
@RolesAllowed("ROLE_ADMIN")
@Controller
public class DashboardController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public DashboardController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = {"/dashboard", "/", "/home"})
    public String showDashboard(Model model) {
        DashboardRequest dashboardRequest = new DashboardRequest(userService.getTotalUsers(), roleService.getTotalRoles(), roleService.getTotalPrivileges());
        model.addAttribute("dashboardRequest", dashboardRequest);
        return "/admin/dashboard";
    }
}
