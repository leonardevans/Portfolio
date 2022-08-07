package com.lemutugi.controller;

import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.service.RoleService;
import com.lemutugi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/auth/*")
public class AuthController {
    private UserService userService;
    private RoleService roleService;

    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "/auth/login";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model){
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "/auth/signup";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage(){
        return "/auth/forgot-password";
    }
}
