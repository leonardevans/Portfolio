package com.lemutugi.repository;

import com.lemutugi.model.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByName(String name);
    boolean existsByName(String name);

    @Query(value = "SELECT * FROM Privilege p INNER jOIN roles_privileges ON roles_privileges.role_id = ?1 ", nativeQuery = true)
    Page<Privilege> findByRoleId(@Param("roleId") Long roleId, Pageable pageable);
}
