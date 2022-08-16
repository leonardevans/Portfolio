package com.lemutugi.controller.admin;

import com.lemutugi.model.Privilege;
import com.lemutugi.payload.request.PrivilegeRequest;
import com.lemutugi.service.PrivilegeService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
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

    @GetMapping("/add")
    public String showAddPrivilege(Model model){
        model.addAttribute("privilegeRequest", new PrivilegeRequest());
        return "/admin/add-edit-privilege";
    }

    @GetMapping("/edit/{id}")
    public String showEditPrivilege(@PathVariable("id") Long id, Model model){
        Privilege privilege = privilegeService.getPrivilegeById(id);
        PrivilegeRequest privilegeRequest = new PrivilegeRequest(privilege);
        model.addAttribute("privilegeRequest", privilegeRequest);
        return "/admin/add-edit-privilege";
    }

    @GetMapping("/delete/{id}")
    public String deletePrivilege(@PathVariable("id") Long id){
        if (privilegeService.deletePrivilegeById(id)) return "redirect:/admin/privileges/?delete_success";
        return "redirect:/admin/privileges/?delete_error";
    }

    @PostMapping("/add")
    public String createPrivilege(@Valid @ModelAttribute("privilegeRequest") PrivilegeRequest privilegeRequest, BindingResult bindingResult){
        if (privilegeService.existsByName(privilegeRequest.getName())){
            bindingResult.addError(new FieldError("privilegeRequest", "name", "A privilege with this name already exist."));
        }

        if (bindingResult.hasErrors()) return "/admin/add-edit-privilege";

        privilegeService.createPrivilege(privilegeRequest);

        return "redirect:/admin/privileges/?add_success";
    }

    @PostMapping("/update")
    public String updatePrivilege(@Valid @ModelAttribute("privilegeRequest") PrivilegeRequest privilegeRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "/admin/add-edit-privilege";

        privilegeService.updatePrivilege(privilegeRequest);

        return "redirect:/admin/privileges/?update_success";
    }
}
