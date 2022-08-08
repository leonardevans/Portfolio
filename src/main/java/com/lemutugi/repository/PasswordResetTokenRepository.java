package com.lemutugi.repository;

import com.lemutugi.model.PasswordResetToken;
import com.lemutugi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    boolean existsByUserAndToken(User user, String token);
}
