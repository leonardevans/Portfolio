package com.lemutugi.service;

import com.lemutugi.model.User;
import com.lemutugi.payload.dto.UserDto;
import com.lemutugi.payload.request.ForgotPasswordRequest;
import com.lemutugi.payload.request.ResetPasswordRequest;
import com.lemutugi.payload.request.SignUpRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    Page<User> getAll(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<User> searchUserByAllFields(int pageNo, int pageSize, String sortField, String sortDirection, String search);

    User getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String email);
    User createUser(UserDto userDto);

    User updateUser(UserDto userDto);

    User saveUser(User user);

    boolean registerUser(SignUpRequest signUpRequest);

    boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest, String path);

    boolean deleteUserById(Long id);
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);

    boolean existsByMobile(Long mobile);

    boolean existsByMobileAndIdNot(Long mobile, Long id);

    User validatePasswordResetToken(String token);

    boolean validateEmailToken(String token);

    boolean resetPassword(ResetPasswordRequest resetPasswordRequest);

    Long getTotalUsers();
}
