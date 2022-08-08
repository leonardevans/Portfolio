package com.lemutugi.service.impl;

import com.lemutugi.model.PasswordResetToken;
import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.model.enums.ERole;
import com.lemutugi.payload.request.ForgotPasswordRequest;
import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.repository.PasswordResetTokenRepository;
import com.lemutugi.repository.RoleRepository;
import com.lemutugi.repository.UserRepository;
import com.lemutugi.service.EmailSenderService;
import com.lemutugi.service.UserService;
import com.lemutugi.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    PasswordResetTokenRepository passwordResetTokenRepository;
    EmailSenderService emailSenderService;

    @Value("${BASE_URL}")
    private String BASE_URL;

    @Value("${notifications.to}")
    String notificationsTo;

    @Value("${notifications.from}")
    String notificationsFrom;

    @Value("${notifications.cc}")
    String notificationsToCc;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository passwordResetTokenRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailSenderService = emailSenderService;
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

            Role userRole = roleRepository.findByName(ERole.ROLE_USER.name()).orElse(null);

            if (userRole == null){
                userRole = roleRepository.save(new Role(ERole.ROLE_USER.name()));
            }
            user.getRoles().add(userRole);

            this.saveUser(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        try {
            User user = userRepository.findByEmail(forgotPasswordRequest.getEmail()).get();

            PasswordResetToken passwordResetToken = new PasswordResetToken(user);

            while (true){
                if (passwordResetTokenRepository.existsByToken(passwordResetToken.getToken())){
                    passwordResetToken.setToken(java.util.UUID.randomUUID().toString());
                    continue;
                }
                break;
            }

            passwordResetToken = passwordResetTokenRepository.save(passwordResetToken);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom(notificationsFrom);
            mailMessage.setText("To complete the password reset process, please click here: "
                    + BASE_URL +"/auth/password-reset-token?token=" + passwordResetToken.getToken());

            // Send the email
            emailSenderService.sendEmail(mailMessage);

        }catch (Exception e){
            System.out.println(e.getMessage());
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
