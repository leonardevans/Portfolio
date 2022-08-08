package com.lemutugi.repository;

import com.lemutugi.model.PasswordResetToken;
import com.lemutugi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    boolean existsByToken(String token);
    Optional<PasswordResetToken> findByToken(String token);
}
