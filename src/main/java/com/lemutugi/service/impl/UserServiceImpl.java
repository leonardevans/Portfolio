package com.lemutugi.service.impl;

import com.lemutugi.exceptions.NotFoundException;
import com.lemutugi.model.Token;
import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.model.enums.ERole;
import com.lemutugi.model.enums.TokenType;
import com.lemutugi.payload.dto.UserDto;
import com.lemutugi.payload.request.ForgotPasswordRequest;
import com.lemutugi.payload.request.ResetPasswordRequest;
import com.lemutugi.payload.request.SignUpRequest;
import com.lemutugi.repository.TokenRepository;
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
    TokenRepository tokenRepository;
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
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public Page<User> getAll(int pageNo, int pageSize, String sortField, String sortDirection) {
        return userRepository.findAll(Pager.createPageable(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Page<User> searchUserByAllFields(int pageNo, int pageSize, String sortField, String sortDirection, String search) {
        return userRepository.search(Pager.createPageable(pageNo, pageSize, sortField, sortDirection), search);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow( () -> new NotFoundException("No user found with id: " + id));
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
    public User createUser(UserDto userDto) {
        User newUser = new User(userDto);
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(()-> new NotFoundException("No user found with id: " + userDto.getId()));

        user.update(userDto);
        return userRepository.save(user);
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

            user = this.saveUser(user);

            Token token = this.createToken(user, TokenType.EMAIL_VERIFIATION);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Verify Email!");
            mailMessage.setFrom(notificationsFrom);
            mailMessage.setText("To verify your email, please click here: "
                    + BASE_URL +"/auth/verify-email?token=" + token.getToken());

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
    public boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        try {
            User user = userRepository.findByEmail(forgotPasswordRequest.getEmail()).get();

            Token token = createToken(user, TokenType.PASSWORD_RESET);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom(notificationsFrom);
            mailMessage.setText("To complete the password reset process, please click here: "
                    + BASE_URL +"/auth/password-reset-token?token=" + token.getToken());

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
        if (!userRepository.existsById(id)) throw new NotFoundException("No user found with id: " + id);
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return userRepository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByUsernameAndIdNot(String username, Long id) {
        return userRepository.existsByUsernameAndIdNot(username, id);
    }

    @Override
    public boolean existsByMobile(Long mobile) {
        return userRepository.existsByMobile(mobile);
    }

    @Override
    public boolean existsByMobileAndIdNot(Long mobile, Long id) {
        return userRepository.existsByMobileAndIdNot(mobile, id);
    }

    @Override
    public User validatePasswordResetToken(String token){
        Optional<Token> passwordResetToken = tokenRepository.findByTokenAndType(token, TokenType.PASSWORD_RESET.name());

        if (passwordResetToken.isEmpty()) return null;

        return passwordResetToken.get().getUser();
    }

    @Override
    public boolean validateEmailToken(String token){
        Optional<Token> optionalToken = tokenRepository.findByTokenAndType(token, TokenType.EMAIL_VERIFIATION.name());

        if (optionalToken.isEmpty()) return false;

        User user = optionalToken.get().getUser();
        user.setEmail_verified(true);
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean resetPassword(ResetPasswordRequest resetPasswordRequest) {
        try {
            // Use email to find user
            Optional<User> optionalUser = userRepository.findByEmail(resetPasswordRequest.getEmail());
            User tokenUser = optionalUser.get();

            //set password
            tokenUser.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));

//            save the user
            userRepository.save(tokenUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private Token createToken(User user, TokenType tokenType){
        Token token = new Token(user, tokenType.name());

        while (true){
            if (tokenRepository.existsByTokenAndType(token.getToken() , tokenType.name())){
                token.setToken(java.util.UUID.randomUUID().toString());
                continue;
            }
            break;
        }

        return tokenRepository.save(token);
    }

    @Override
    public Long getTotalUsers(){
        return userRepository.count();
    }
}
