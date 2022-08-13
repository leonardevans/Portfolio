package com.lemutugi.controller.admin;

import com.lemutugi.service.PrivilegeService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/admin/privileges/")
@RolesAllowed("ROLE_ADMIN")
public class PrivilegeController {
    private PrivilegeService privilegeService;

    @Autowired
    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping({"list", "/"})
    private String showPrivileges(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE) int pageSize, @RequestParam(defaultValue = "name", required = false) String sortField, @RequestParam(defaultValue = "asc", required = false) String sortDir , Model model){
        model.addAttribute("privileges", privilegeService.getAllPrivileges(pageNo, pageSize, sortField, sortDir));
        return "/admin/privileges";
    }
}
