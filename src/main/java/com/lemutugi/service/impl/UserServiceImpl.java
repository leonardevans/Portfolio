package com.lemutugi.service.impl;

import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.model.enums.ERole;
import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.repository.RoleRepository;
import com.lemutugi.repository.UserRepository;
import com.lemutugi.service.UserService;
import com.lemutugi.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<User> getAll(int pageNo, int pageSize, String sortField, String sortDirection) {
        return userRepository.findAll(Pager.createPageable(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean registerUser(SignUpRequest signUpRequest) {
        try {
            User user = new User(signUpRequest, passwordEncoder.encode(signUpRequest.getPassword()), true, false, AuthProvider.local);

            Role userRole = roleRepository.findByName(ERole.ROLE_EDITOR.name()).orElse(null);

            if (userRole == null){
                userRole = roleRepository.save(new Role(ERole.ROLE_USER.name()));
            }
            user.getRoles().add(userRole);

            this.saveUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteUserById(Long id) {
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByMobile(Long mobile) {
        return userRepository.existsByMobile(mobile);
    }
}
