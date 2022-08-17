package com.lemutugi.controller.admin;

import com.lemutugi.model.Role;
import com.lemutugi.payload.request.PrivilegeRequest;
import com.lemutugi.payload.request.RoleRequest;
import com.lemutugi.service.RoleService;
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
import java.util.Optional;

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
            @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = "name", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            Model model){

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

    @GetMapping("/add")
    public String showAddRole(Model model){
        model.addAttribute("roleRequest", new RoleRequest());
        return "/admin/add-edit-role";
    }

    @GetMapping("/edit/{id}")
    public String showEditRole(@PathVariable("id") Long id, Model model){
        Role role = roleService.getRoleById(id);
        RoleRequest roleRequest = new RoleRequest(role);
        model.addAttribute("roleRequest", roleRequest);
        return "/admin/add-edit-role";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable("id") Long id){
        if (roleService.deleteRoleById(id)) return "redirect:/admin/roles/?delete_success";
        return "redirect:/admin/roles/?delete_error";
    }

    @PostMapping("/add")
    public String createRole(@Valid @ModelAttribute("privilegeRequest") RoleRequest roleRequest, BindingResult bindingResult){
        if (roleService.existsByName(roleRequest.getName())){
            bindingResult.addError(new FieldError("roleRequest", "name", "A role with this name already exist."));
        }

        if (bindingResult.hasErrors()) return "/admin/add-edit-role";

        roleService.createRole(roleRequest);

        return "redirect:/admin/roles/?add_success";
    }

    @PostMapping("/update")
    public String updateRole(@Valid @ModelAttribute("privilegeRequest") RoleRequest roleRequest, BindingResult bindingResult){
        Optional<Role> optionalRole = roleService.getRoleByName(roleRequest.getName());

        if (optionalRole.isPresent()){
            if (optionalRole.get().getId() != roleRequest.getId()){
                bindingResult.addError(new FieldError("roleRequest", "name", "A role with this name already exist."));
            }
        }

        if (bindingResult.hasErrors()) return "/admin/add-edit-role";

        roleService.updateRole(roleRequest);

        return "redirect:/admin/roles/?update_success";
    }
}
