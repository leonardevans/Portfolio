package com.lemutugi.controller.admin;

import com.lemutugi.controller.util.HttpUtil;
import com.lemutugi.model.Privilege;
import com.lemutugi.payload.request.PrivilegeRequest;
import com.lemutugi.service.PrivilegeService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
public class PrivilegeController extends HttpUtil {
    private PrivilegeService privilegeService;

    @Autowired
    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping
    public String showPrivileges(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = "name", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            @RequestParam(required = false) String search,
            Model model){
        Page<Privilege> privilegePage = Page.empty();

        if (search != null){
            privilegePage = privilegeService.search(pageNo, pageSize, sortField, sortDir, search);
            model.addAttribute("search", "&search=" + search);
        }else{
            privilegePage = privilegeService.getAllPrivileges(pageNo, pageSize, sortField, sortDir);
            model.addAttribute("search", "");
        }
        List<Privilege> privileges = privilegePage.getContent();

        model = this.addPagingAttributes(model, pageSize, pageNo, privilegePage.getTotalPages(), privilegePage.getTotalElements(), sortField, sortDir);

        model.addAttribute("privileges", privileges);
        return "/admin/privileges";
    }

    @PreAuthorize("hasAuthority('CREATE_PRIVILEGE')")
    @GetMapping("add")
    public String showAddPrivilege(Model model){
        model.addAttribute("privilegeRequest", new PrivilegeRequest());
        return "/admin/add-edit-privilege";
    }

    @PreAuthorize("hasAuthority('EDIT_PRIVILEGE')")
    @GetMapping("edit/{id}")
    public String showEditPrivilege(@PathVariable("id") Long id, Model model){
        Privilege privilege = privilegeService.getPrivilegeById(id);
        PrivilegeRequest privilegeRequest = new PrivilegeRequest(privilege);
        model.addAttribute("privilegeRequest", privilegeRequest);
        return "/admin/add-edit-privilege";
    }

    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    @GetMapping("delete/{id}")
    public String deletePrivilege(@PathVariable("id") Long id){
        if (privilegeService.deletePrivilegeById(id)) return "redirect:/admin/privileges/?delete_success";
        return "redirect:/admin/privileges/?delete_error";
    }

    @PreAuthorize("hasAuthority('CREATE_PRIVILEGE')")
    @PostMapping("add")
    public String createPrivilege(@Valid @ModelAttribute("privilegeRequest") PrivilegeRequest privilegeRequest, BindingResult bindingResult){
        bindingResult = this.validateCreatePrivilegeData(bindingResult, privilegeService, privilegeRequest);

        if (bindingResult.hasErrors()) return "/admin/add-edit-privilege";

        privilegeService.createPrivilege(privilegeRequest);

        return "redirect:/admin/privileges/?add_success";
    }

    @PreAuthorize("hasAuthority('EDIT_PRIVILEGE')")
    @PostMapping("update")
    public String updatePrivilege(@Valid @ModelAttribute("privilegeRequest") PrivilegeRequest privilegeRequest, BindingResult bindingResult){
        bindingResult = this.validateUpdatePrivilegeData(bindingResult, privilegeService, privilegeRequest);

        if (bindingResult.hasErrors()) return "/admin/add-edit-privilege";

        privilegeService.updatePrivilege(privilegeRequest);

        return "redirect:/admin/privileges/?update_success";
    }
}
