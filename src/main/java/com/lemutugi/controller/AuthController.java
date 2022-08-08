package com.lemutugi.controller;

import com.lemutugi.payload.request.ForgotPasswordRequest;
import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.service.RoleService;
import com.lemutugi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signUpRequest") SignUpRequest signUpRequest, BindingResult bindingResult){
        if(!signUpRequest.getConfirmPassword().equals(signUpRequest.getPassword())){
            bindingResult.addError(new FieldError("signUpRequest", "confirmPassword", "passwords should match."));
        }

        if(userService.existsByEmail(signUpRequest.getEmail())) {
            bindingResult.addError(new FieldError("signUpRequest", "email", "Email address already in use."));
//            throw new BadRequestException("Email address already in use.");
        }

        if(userService.existsByUsername(signUpRequest.getUsername())) {
            bindingResult.addError(new FieldError("signUpRequest", "username", "Username already in use."));
        }

        if (bindingResult.hasErrors()) return "/auth/signup";

        if (userService.registerUser(signUpRequest)){

            return "redirect:/auth/signup?success";
        }

        return "redirect:/auth/signup?error";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage(Model model){
        model.addAttribute("forgotPasswordRequest", new ForgotPasswordRequest());
        return "/auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @ModelAttribute("forgotPasswordRequest") ForgotPasswordRequest forgotPasswordRequest, BindingResult bindingResult){

        if(!userService.existsByEmail(forgotPasswordRequest.getEmail())) {
            bindingResult.addError(new FieldError("forgotPasswordRequest", "email", "Email address does not exist."));
        }


        if (bindingResult.hasErrors()) return "/auth/forgot-password";

        if (userService.forgotPassword(forgotPasswordRequest)){

            return "redirect:/auth/forgot-password?success";
        }

        return "redirect:/auth/forgot-password?error";
    }
}
