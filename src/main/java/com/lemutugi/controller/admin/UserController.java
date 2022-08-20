package com.lemutugi.controller.admin;

import com.lemutugi.controller.BaseModel;
import com.lemutugi.model.Role;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequestMapping("/admin/users/")
@RolesAllowed("ROLE_ADMIN")
@Controller
public class UserController extends BaseModel {
    private UserService userService;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

}
