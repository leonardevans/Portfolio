package com.lemutugi.controller.admin;

import com.lemutugi.controller.util.HttpUtil;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.payload.dto.UserDto;
import com.lemutugi.repository.RoleRepository;
import com.lemutugi.service.UserService;
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

@RequestMapping("/admin/users/")
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
@Controller
public class UserController extends HttpUtil {
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String showUsers(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = "fName", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            @RequestParam(required = false) String search,
            Model model){
        Page<User> userPage = Page.empty();

        if (search != null){
            userPage = userService.searchUserByAllFields(pageNo, pageSize, sortField, sortDir, search);
            model.addAttribute("search", "&search=" + search);
        }else{
            userPage = userService.getAll(pageNo, pageSize, sortField, sortDir);
            model.addAttribute("search", "");
        }

        List<User> users = userPage.getContent();

        model = this.addPagingAttributes(model, pageSize, pageNo, userPage.getTotalPages(), userPage.getTotalElements(), sortField, sortDir);

        model.addAttribute("users", users);
        return "/admin/users";
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @GetMapping("add")
    public String showAddUser(Model model){
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("authProviders" , AuthProvider.values());
        return "/admin/add-edit-user";
    }

    @PreAuthorize("hasAuthority('EDIT_USER')")
    @GetMapping("edit/{id}")
    public String showEditUser(@PathVariable("id") Long id, Model model){
        User user = userService.getUserById(id);
        UserDto userDto = new UserDto(user);
        model.addAttribute("userDto", userDto);
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("authProviders" , AuthProvider.values());
        return "/admin/add-edit-user";
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping("add")
    public String createUser(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model){
        if(userService.existsByEmail(userDto.getEmail())) {
            bindingResult.addError(new FieldError("userDto", "email", "Email address already in use."));
        }

        if(userService.existsByUsername(userDto.getUsername())) {
            bindingResult.addError(new FieldError("userDto", "username", "Username already in use."));
        }

        if(userDto.getMobile() != null && userService.existsByMobile(userDto.getMobile())) {
            bindingResult.addError(new FieldError("userDto", "mobile", "Mobile number is already in use."));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            model.addAttribute("authProviders" , AuthProvider.values());
            return "/admin/add-edit-user";
        }

        userService.createUser(userDto);

        return "redirect:/admin/users/?add_success";
    }

    @PreAuthorize("hasAuthority('EDIT_USER')")
    @PostMapping("update")
    public String updateUser(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model){
        if(userService.existsByEmailAndIdNot(userDto.getEmail(), userDto.getId())) {
            bindingResult.addError(new FieldError("userDto", "email", "Email address already in use."));
        }

        if(userService.existsByUsernameAndIdNot(userDto.getUsername(), userDto.getId())) {
            bindingResult.addError(new FieldError("userDto", "username", "Username already in use."));
        }

        if(userDto.getMobile() != null && userService.existsByMobileAndIdNot(userDto.getMobile(), userDto.getId())) {
            bindingResult.addError(new FieldError("userDto", "mobile", "Mobile number is already in use."));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            model.addAttribute("authProviders" , AuthProvider.values());
            return "/admin/add-edit-user";
        }

        userService.updateUser(userDto);

        return "redirect:/admin/users/?update_success";
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        if (userService.deleteUserById(id)) return "redirect:/admin/users/?delete_success";
        return "redirect:/admin/users/?delete_error";
    }

}
