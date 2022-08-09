package com.lemutugi.repository;

import com.lemutugi.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    boolean existsByToken(String token);
    boolean existsByTokenAndType(String token, String type);
    Optional<Token> findByToken(String token);
    Optional<Token> findByTokenAndType(String token, String type);
}
