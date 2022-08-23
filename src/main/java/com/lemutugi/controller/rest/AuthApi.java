package com.lemutugi.controller.rest;

import com.lemutugi.controller.util.HttpUtil;
import com.lemutugi.payload.request.LoginRequest;
import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.payload.response.ApiResponse;
import com.lemutugi.payload.response.AuthResponse;
import com.lemutugi.security.TokenProvider;
import com.lemutugi.security.UserPrincipal;
import com.lemutugi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/*")
public class AuthApi extends HttpUtil {
    private UserService userService;
    private TokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthApi(UserService userService, TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("signup")
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
            return ResponseEntity.ok().body(apiResponse);
        }

        apiResponse = new ApiResponse(false, "Error while creating account");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        ApiResponse apiResponse = null;

        if (bindingResult.hasErrors()){
            apiResponse = new ApiResponse(false, "Failed to login. Please provide the correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            //generate jwt token
            String token = tokenProvider.createToken(authentication);
            //get user details
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        get user roles as a list
            List<String> roles = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            AuthResponse authResponse = new AuthResponse(token, userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getEmail(), roles );
            authResponse.setSuccess(true);
            authResponse.setMessage("You have logged in successfully.");
            return ResponseEntity.ok(authResponse);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            apiResponse = new ApiResponse(false, "Failed to login. Please provide the correct username and password.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
}
