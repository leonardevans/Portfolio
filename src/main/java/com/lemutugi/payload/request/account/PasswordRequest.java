package com.lemutugi.payload.request.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class PasswordRequest {
//    @NotEmpty(message = "current password is required")
    private String currentPassword;

    @NotEmpty(message = "new password is required")
    private String newPassword;

    @NotEmpty(message = "please confirm new password")
    private String confirmNewPassword;
}
