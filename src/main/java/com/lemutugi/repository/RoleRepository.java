package com.lemutugi.repository;

import com.lemutugi.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);

    @Query(value = "SELECT r FROM Role r WHERE r.name LIKE %?1%", nativeQuery = false)
    Page<Role> searchByName(Pageable pageable, String search);
}
