package com.lemutugi.service;

import com.lemutugi.model.User;

public interface MyAccountService {
    boolean isMyPassword(String password);

    User updatePassword(String password);
}
