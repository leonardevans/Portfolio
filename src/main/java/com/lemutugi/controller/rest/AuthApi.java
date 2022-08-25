package com.lemutugi.controller.rest;

import com.lemutugi.controller.util.HttpUtil;
import com.lemutugi.model.User;
import com.lemutugi.payload.request.ForgotPasswordRequest;
import com.lemutugi.payload.request.LoginRequest;
import com.lemutugi.payload.request.ResetPasswordRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Object> signup(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult){
        bindingResult = this.validateSignUpData(bindingResult, userService, signUpRequest);

        ApiResponse apiResponse = null;

        if (bindingResult.hasErrors()){
            apiResponse = new ApiResponse(false, "Failed to create account. Please provide correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        User user = userService.registerUser(signUpRequest, "/api");

        if (user != null){
            Map<String, Object> data = new HashMap<>();
            data.put("user", user);
            apiResponse = new ApiResponse(true, "Account created successfully. You can login with your credentials");
            apiResponse.setData(data);
            return ResponseEntity.ok().body(apiResponse);
        }

        apiResponse = new ApiResponse(false, "Error while creating account");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
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

    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest, BindingResult bindingResult){
        ApiResponse apiResponse = null;
        bindingResult = this.validateForgotPasswordData(bindingResult, userService, forgotPasswordRequest);

        if (bindingResult.hasErrors()){
            apiResponse = new ApiResponse(false, "Failed to process forgot password. Please provide the correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }


        if (userService.forgotPassword(forgotPasswordRequest, "/api")){
            apiResponse = new ApiResponse(true, "We have sent you am email with instructions to reset your password.");
            return ResponseEntity.ok().body(apiResponse);
        }

        apiResponse = new ApiResponse(false, "Failed to email you reset password instructions!");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @GetMapping("password-reset-token")
    public ResponseEntity<ApiResponse> validateResetToken(@RequestParam("token") String token){
        ApiResponse apiResponse = null;
        User user = userService.validatePasswordResetToken(token);

        if (user == null){
            apiResponse = new ApiResponse(false, "Failed to verify your password reset token. Please request another password reset token in the forgot password page.");
        }else{
            apiResponse = new ApiResponse(true, "Password reset token verified.");
            HashMap<String, String> data = new HashMap<>();
            data.put("email", user.getEmail());
            apiResponse.setData(data);
        }

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult){
        ApiResponse apiResponse = null;
        bindingResult = this.validateResetPasswordData(bindingResult, userService, resetPasswordRequest);

        if (bindingResult.hasErrors()) {
            apiResponse = new ApiResponse(false, "Failed to reset your password! Please provide correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        if (userService.resetPassword(resetPasswordRequest)){
            apiResponse = new ApiResponse(true, "You've successfully reset your password. You can now Login with your credentials!");
            return ResponseEntity.ok().body(apiResponse);
        }

        apiResponse = new ApiResponse(false, "Failed to email you reset password instructions!");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @GetMapping("verify-email")
    public ResponseEntity<ApiResponse> validateEmailToken(@RequestParam("token") String token){
        ApiResponse apiResponse = null;

        if (userService.validateEmailToken(token)) {
            apiResponse = new ApiResponse(true, "You have successfully verified your email address.");
            return ResponseEntity.ok().body(apiResponse);
        }else{
            apiResponse = new ApiResponse(false, "Failed to verify your email address. Please contact us if this problem persists.");
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
}
