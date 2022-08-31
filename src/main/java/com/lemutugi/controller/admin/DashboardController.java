package com.lemutugi.controller.admin;


import com.lemutugi.payload.request.DashboardRequest;
import com.lemutugi.service.PrivilegeService;
import com.lemutugi.service.RoleService;
import com.lemutugi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@RequestMapping("/admin/")
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPERADMIN", "ROLE_EDITOR"})
@Controller
public class DashboardController {
    private UserService userService;
    private RoleService roleService;
    private PrivilegeService privilegeService;

    @Autowired
    public DashboardController(UserService userService, RoleService roleService, PrivilegeService privilegeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    @GetMapping
    public String showDashboard(Model model) {
        DashboardRequest dashboardRequest = new DashboardRequest(userService.getTotalUsers(), roleService.getTotalRoles(), privilegeService.getTotalPrivileges());
        model.addAttribute("dashboardRequest", dashboardRequest);
        return "/admin/dashboard";
    }
}
