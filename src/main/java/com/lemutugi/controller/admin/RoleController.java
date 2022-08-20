package com.lemutugi.controller.admin;

import com.lemutugi.controller.BaseModel;
import com.lemutugi.model.Role;
import com.lemutugi.payload.request.RoleRequest;
import com.lemutugi.repository.PrivilegeRepository;
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
public class RoleController extends BaseModel {
    private RoleService roleService;
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleController(RoleService roleService, PrivilegeRepository privilegeRepository) {
        this.roleService = roleService;
        this.privilegeRepository = privilegeRepository;
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

        model = this.addPagingAttributes(model, pageSize, pageNo, rolePage.getTotalPages(), rolePage.getTotalElements(), sortField, sortDir);

        model.addAttribute("roles", roles);
        return "/admin/roles";
    }

    @GetMapping("/add")
    public String showAddRole(Model model){
        model.addAttribute("roleRequest", new RoleRequest());
        model.addAttribute("allPrivileges", privilegeRepository.findAll());
        return "/admin/add-edit-role";
    }

    @GetMapping("/edit/{id}")
    public String showEditRole(@PathVariable("id") Long id, Model model){
        Role role = roleService.getRoleById(id);
        RoleRequest roleRequest = new RoleRequest(role);
        model.addAttribute("roleRequest", roleRequest);
        model.addAttribute("allPrivileges", privilegeRepository.findAll());
        return "/admin/add-edit-role";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable("id") Long id){
        if (roleService.deleteRoleById(id)) return "redirect:/admin/roles/?delete_success";
        return "redirect:/admin/roles/?delete_error";
    }

    @PostMapping("/add")
    public String createRole(@Valid @ModelAttribute("roleRequest") RoleRequest roleRequest, BindingResult bindingResult, Model model){
        if (roleService.existsByName(roleRequest.getName())){
            bindingResult.addError(new FieldError("roleRequest", "name", "A role with this name already exist."));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allPrivileges", privilegeRepository.findAll());
            return "/admin/add-edit-role";
        }

        roleService.createRole(roleRequest);

        return "redirect:/admin/roles/?add_success";
    }

    @PostMapping("/update")
    public String updateRole(@Valid @ModelAttribute("roleRequest") RoleRequest roleRequest, BindingResult bindingResult, Model model){
        Optional<Role> optionalRole = roleService.getRoleByName(roleRequest.getName());

        if (optionalRole.isPresent()){
            if (optionalRole.get().getId() != roleRequest.getId()){
                bindingResult.addError(new FieldError("roleRequest", "name", "A role with this name already exist."));
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allPrivileges", privilegeRepository.findAll());
            return "/admin/add-edit-role";
        }

        roleService.updateRole(roleRequest);

        return "redirect:/admin/roles/?update_success";
    }
}
