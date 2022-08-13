package com.lemutugi.service;

import com.lemutugi.model.Role;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoleService {
    Page<Role> getAllRoles(int pageNo, int pageSize, String sortField, String sortDirection);
    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleByName(String name);
    Role saveRole( Role role);
    boolean deleteRoleById(Long id);
    Long getTotalRoles();
}
