package com.lemutugi.service.impl;

import com.lemutugi.model.User;
import com.lemutugi.repository.UserRepository;
import com.lemutugi.security.AuthUtil;
import com.lemutugi.service.MyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyAccountServiceImpl implements MyAccountService {
    private UserRepository userRepository;
    private AuthUtil authUtil;
    private PasswordEncoder passwordEncoder;
    private User loggedInUser;

    @Autowired
    public MyAccountServiceImpl(UserRepository userRepository, AuthUtil authUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authUtil = authUtil;
        this.passwordEncoder = passwordEncoder;
        loggedInUser = authUtil.getLoggedInUser();
    }

    @Override
    public boolean isMyPassword(String password){
        loggedInUser = authUtil.getLoggedInUser();
        if (passwordEncoder.matches(password, loggedInUser.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public User updatePassword(String password){
        loggedInUser = authUtil.getLoggedInUser();
        loggedInUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(loggedInUser);
    }
}
