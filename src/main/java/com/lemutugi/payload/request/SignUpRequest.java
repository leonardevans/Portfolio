package com.lemutugi.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 6, message = "Username too short")
    @Size(max = 30, message = "Username too long")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email format")
    private String email;

    @NotEmpty(message = "password is required")
    private String password;

    @NotEmpty(message = "please confirm password")
    private String confirmPassword;

    private LocationRequest location;

    private Set<String> role;
}
