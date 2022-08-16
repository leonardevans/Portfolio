package com.lemutugi.security;

import com.lemutugi.model.User;
import com.lemutugi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUtil {
    private UserRepository userRepository;

    @Autowired
    public AuthUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedInUser(){
        User user = null;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if ( authentication == null || !authentication.isAuthenticated()) {
                return null;
            }
            Object principal = authentication.getPrincipal();

            UserDetails loggedInUser = (UserDetails) principal;
            user = userRepository.findByUsername(loggedInUser.getUsername()).orElse(null);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    public String getLoggedInUsername(){
        String username = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if ( authentication == null || !authentication.isAuthenticated()) {
                return null;
            }
            Object principal = authentication.getPrincipal();

            UserDetails loggedInUser = (UserDetails) principal;
            username = loggedInUser.getUsername();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return username;
    }
}
