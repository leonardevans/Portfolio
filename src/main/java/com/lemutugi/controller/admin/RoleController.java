package com.lemutugi.controller.admin;

import com.lemutugi.service.RoleService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/roles/")
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("role-list")
    private String showRoles(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE) int pageSize, @RequestParam(defaultValue = "name", required = false) String sortField, @RequestParam(defaultValue = "asc", required = false) String sortDir , Model model){
        model.addAttribute("roles", roleService.getAllRoles(pageNo, pageSize, sortField, sortDir));
        return "/admin/roles";
    }

    @GetMapping("privilege-list")
    private String showPrivileges(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE) int pageSize, @RequestParam(defaultValue = "name", required = false) String sortField, @RequestParam(defaultValue = "asc", required = false) String sortDir , Model model){
        model.addAttribute("privileges", roleService.getAllPrivileges(pageNo, pageSize, sortField, sortDir));
        return "/admin/privileges";
    }
}
