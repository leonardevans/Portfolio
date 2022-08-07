package com.lemutugi.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private final String tokenType = "Bearer";
    private Integer id;
    private String userName;
    private String email;
    private List<String> roles;

    public AuthResponse(String token, Integer id, String userName, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }

    public AuthResponse(String token) {
        this.token = token;
    }
}
