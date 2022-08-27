package com.lemutugi.payload.request;

import com.lemutugi.validation.constraint.Password;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 3, message = "First name should contain at least 3 characters")
    @Size(max = 35, message = "First name should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "First name should contain only letters")
    private String fName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, message = "Last name should contain at least 3 characters")
    @Size(max = 35, message = "Last name should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
    private String lName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username should contain at least 3 characters")
    @Size(max = 35, message = "Username should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username should contain only letters and numbers. No special characters.")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email format")
    private String email;

    @NotBlank(message = "Password is required")
//    @Size(min = 8, max = 30, message = "Please enter a minimum password of 8 characters and maximum 30")
    @Password
    private String password;

    @NotBlank(message = "please confirm password")
    private String confirmPassword;

}
