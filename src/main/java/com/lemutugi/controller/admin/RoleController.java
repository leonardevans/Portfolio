package com.lemutugi.controller.admin;

import com.lemutugi.model.Role;
import com.lemutugi.service.RoleService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequestMapping("/admin/roles/")
@RolesAllowed("ROLE_ADMIN")
@Controller
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = {"/list", "/"})
    public String showRoles(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "name", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            Model model){
        int pageSize = Constants.SMALL_PAGE_SIZE;

        Page<Role> rolePage = roleService.getAllRoles(pageNo, pageSize, sortField, sortDir);
        List<Role> roles = rolePage.getContent();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", rolePage.getTotalPages());
        model.addAttribute("totalItems", rolePage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("roles", roles);
        return "/admin/roles";
    }
}
