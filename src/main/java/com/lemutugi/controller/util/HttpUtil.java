package com.lemutugi.controller.util;

import com.lemutugi.payload.request.ForgotPasswordRequest;
import com.lemutugi.payload.request.PrivilegeRequest;
import com.lemutugi.payload.request.ResetPasswordRequest;
import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.service.PrivilegeService;
import com.lemutugi.service.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    public Model addPagingAttributes(Model model, int pageSize, int pageNo, int totalPages, long totalItems, String sortField, String sortDir){
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return model;
    }

    public Map<String, Object> addPagingAttributes(Map<String, Object> data, int pageSize, int pageNo, int totalPages, long totalItems, String sortField, String sortDir){
        data.put("pageSize", pageSize);
        data.put("currentPage", pageNo);
        data.put("totalPages", totalPages);
        data.put("totalItems", totalItems);

        data.put("sortField", sortField);
        data.put("sortDir", sortDir);
        data.put("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return data;
    }

    public Map<String, String> getErrors(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();

        bindingResult.getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return errors;
    }

    public BindingResult validateSignUpData(BindingResult bindingResult, UserService userService, SignUpRequest signUpRequest){
        if(!signUpRequest.getConfirmPassword().equals(signUpRequest.getPassword())){
            bindingResult.addError(new FieldError("signUpRequest", "confirmPassword", "passwords should match."));
        }

        if(userService.existsByEmail(signUpRequest.getEmail())) {
            bindingResult.addError(new FieldError("signUpRequest", "email", "Email address already in use."));
        }

        if(userService.existsByUsername(signUpRequest.getUsername())) {
            bindingResult.addError(new FieldError("signUpRequest", "username", "Username already in use."));
        }

        return bindingResult;
    }

    public BindingResult validateForgotPasswordData(BindingResult bindingResult, UserService userService, ForgotPasswordRequest forgotPasswordRequest){
        if(!bindingResult.hasErrors() && !userService.existsByEmail(forgotPasswordRequest.getEmail())) {
            bindingResult.addError(new FieldError("forgotPasswordRequest", "email", "No account associated with this email."));
        }

        return bindingResult;
    }

    public BindingResult validateResetPasswordData(BindingResult bindingResult, UserService userService, ResetPasswordRequest resetPasswordRequest){
        if(!resetPasswordRequest.getConfirmPassword().equals(resetPasswordRequest.getPassword())){
            bindingResult.addError(new FieldError("resetPasswordRequest", "confirmPassword", "passwords should match."));
        }

        if(!bindingResult.hasErrors() && !userService.existsByEmail(resetPasswordRequest.getEmail())) {
            bindingResult.addError(new FieldError("resetPasswordRequest", "email", "This email does not exist."));
        }

        return bindingResult;
    }

    protected BindingResult validateCreatePrivilegeData(BindingResult bindingResult, PrivilegeService privilegeService, PrivilegeRequest privilegeRequest) {
        if (privilegeService.existsByName(privilegeRequest.getName())){
            bindingResult.addError(new FieldError("privilegeRequest", "name", "A privilege with this name already exist."));
        }

        return bindingResult;
    }

    protected BindingResult validateUpdatePrivilegeData(BindingResult bindingResult, PrivilegeService privilegeService, PrivilegeRequest privilegeRequest) {
        if (privilegeService.existsByNameAndIdNot(privilegeRequest.getName(), privilegeRequest.getId())){
            bindingResult.addError(new FieldError("privilegeRequest", "name", "A privilege with this name already exist."));
        }

        return bindingResult;
    }
}
