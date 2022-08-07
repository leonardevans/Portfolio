package com.lemutugi.payload.request.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AccountRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 6, message = "Username too short")
    @Size(max = 30, message = "Username too long")
    private String userName;

//    @NotBlank(message = "Email is required")
//    @Email(message = "Enter valid email format")
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
