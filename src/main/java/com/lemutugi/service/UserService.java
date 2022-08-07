package com.lemutugi.service;

import com.lemutugi.model.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    Page<User> getAll(int pageNo, int pageSize, String sortField, String sortDirection);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String email);
    User saveUser(User user);
    boolean deleteUserById(Long id);
}
