package com.lemutugi.controller.admin;

import com.lemutugi.controller.BaseModel;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.payload.dto.UserDto;
import com.lemutugi.payload.request.RoleRequest;
import com.lemutugi.repository.RoleRepository;
import com.lemutugi.service.UserService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/admin/users/")
@RolesAllowed("ROLE_ADMIN")
@Controller
public class UserController extends BaseModel {
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = {"/list", "/"})
    public String showUsers(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = "fName", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            Model model){

        Page<User> userPage = userService.getAll(pageNo, pageSize, sortField, sortDir);
        List<User> users = userPage.getContent();

        model = this.addPagingAttributes(model, pageSize, pageNo, userPage.getTotalPages(), userPage.getTotalElements(), sortField, sortDir);

        model.addAttribute("users", users);
        return "/admin/users";
    }

    @GetMapping("/add")
    public String showAddUser(Model model){
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("authProviders" , AuthProvider.values());
        return "/admin/add-edit-user";
    }

    @GetMapping("/edit/{id}")
    public String showEditUser(@PathVariable("id") Long id, Model model){
        User user = userService.getUserById(id);
        UserDto userDto = new UserDto(user);
        model.addAttribute("userDto", userDto);
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("authProviders" , AuthProvider.values());
        return "/admin/add-edit-user";
    }

    @PostMapping("/add")
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

    @PostMapping("/update")
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

}
