package com.lemutugi.controller;

import com.lemutugi.model.User;
import com.lemutugi.payload.request.ForgotPasswordRequest;
import com.lemutugi.payload.request.ResetPasswordRequest;
import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.service.RoleService;
import com.lemutugi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth/*")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
            bindingResult.addError(new FieldError("forgotPasswordRequest", "email", "No account associated with this email."));
        }


        if (bindingResult.hasErrors()) return "/auth/forgot-password";

        if (userService.forgotPassword(forgotPasswordRequest)){

            return "redirect:/auth/forgot-password?success";
        }

        return "redirect:/auth/forgot-password?error";
    }

    @GetMapping("/password-reset-token")
    public ModelAndView validateResetToken(@RequestParam("token") String token, ModelAndView modelAndView){
        User user = userService.validateResetToken(token);

        if (user == null) {
            modelAndView.setViewName("/auth/verify-token-message");
            modelAndView.addObject("errorMessage", "Failed to verify your password reset token. Please request another password reset token in the forgot password page");
        }else{
            modelAndView.setViewName("/auth/reset-password");
            modelAndView.addObject("resetPasswordRequest", new ResetPasswordRequest(user.getEmail()));
        }

        return modelAndView;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute("resetPasswordRequest") ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult){
        if(!resetPasswordRequest.getConfirmPassword().equals(resetPasswordRequest.getPassword())){
            bindingResult.addError(new FieldError("signUpRequest", "confirmPassword", "passwords should match."));
        }

        if(!userService.existsByEmail(resetPasswordRequest.getEmail())) {
            return "redirect:/auth/reset-password?error";
        }

        if (bindingResult.hasErrors()) return "/auth/reset-password";

        if (userService.resetPassword(resetPasswordRequest)){
            return "redirect:/auth/reset-password?success";
        }

        return "redirect:/auth/reset-password?error";
    }

    @GetMapping("/reset-password")
    public String showResetPassword(Model model){
        model.addAttribute("resetPasswordRequest", new ResetPasswordRequest());
        return "/auth/reset-password";
    }
}
