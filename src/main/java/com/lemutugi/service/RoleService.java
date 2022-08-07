package com.lemutugi.service;

import com.lemutugi.model.Role;
import com.lemutugi.model.enums.ERole;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoleService {
    Page<Role> getAll(int pageNo, int pageSize, String sortField, String sortDirection);
    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleByName(String name);
    Role saveRole( Role role);
    boolean deleteRoleById(Long id);
}
