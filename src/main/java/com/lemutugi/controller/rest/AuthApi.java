package com.lemutugi.controller.rest;

import com.lemutugi.controller.HttpUtil;
import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.payload.response.ApiResponse;
import com.lemutugi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/*")
public class AuthApi extends HttpUtil {
    private UserService userService;

    @Autowired
    public AuthApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult){
        bindingResult = this.validateSignUpData(bindingResult, userService, signUpRequest);

        ApiResponse apiResponse = null;

        if (bindingResult.hasErrors()){
            apiResponse = new ApiResponse(false, "Failed to created account. Please provide correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        if (userService.registerUser(signUpRequest)){
            apiResponse = new ApiResponse(true, "Account created successfully. You can login with your credentials");
            return ResponseEntity.badRequest().body(apiResponse);
        }

        apiResponse = new ApiResponse(false, "Error while creating account");
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
