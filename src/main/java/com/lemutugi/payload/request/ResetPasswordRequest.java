package com.lemutugi.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email format")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 30, message = "Please enter a minimum password of 8 characters and maximum 30")
    private String password;

    @NotEmpty(message = "please confirm password")
    private String confirmPassword;

    public ResetPasswordRequest(@Email @NotEmpty String email) {
        this.email = email;
    }
}