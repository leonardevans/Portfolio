package com.lemutugi.controller.admin;

import com.lemutugi.model.Privilege;
import com.lemutugi.model.Role;
import com.lemutugi.service.PrivilegeService;
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

@Controller
@RequestMapping("/admin/privileges/")
@RolesAllowed("ROLE_ADMIN")
public class PrivilegeController {
    private PrivilegeService privilegeService;

    @Autowired
    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping(value = {"/list", "/"})
    public String showRoles(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "name", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            Model model){
        int pageSize = Constants.SMALL_PAGE_SIZE;

        Page<Privilege> privilegePage = privilegeService.getAllPrivileges(pageNo, pageSize, sortField, sortDir);
        List<Privilege> privileges = privilegePage.getContent();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", privilegePage.getTotalPages());
        model.addAttribute("totalItems", privilegePage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("privileges", privileges);
        return "/admin/privileges";
    }
}
