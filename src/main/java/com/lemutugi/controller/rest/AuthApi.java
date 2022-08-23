package com.lemutugi.controller.rest;

import com.lemutugi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth/*")
public class AuthApi {
    private UserService userService;

    @Autowired
    public AuthApi(UserService userService) {
        this.userService = userService;
    }
}
