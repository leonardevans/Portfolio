package com.lemutugi.payload.request.account;

import com.lemutugi.validation.constraint.Password;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PasswordRequest {
    @NotBlank(message = "Please provide your current password")
    private String currentPassword;

    @Password
    private String newPassword;

    @NotBlank(message = "Please confirm your new password")
    private String confirmNewPassword;
}
