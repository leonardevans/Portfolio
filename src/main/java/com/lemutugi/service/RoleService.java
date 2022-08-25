package com.lemutugi.service;

import com.lemutugi.model.Role;
import com.lemutugi.payload.request.RoleRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoleService {
    Page<Role> getAllRoles(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Role> search(int pageNo, int pageSize, String sortField, String sortDirection, String search);

    Role getRoleById(Long id);

    boolean existsByName(String name);

    Optional<Role> getRoleByName(String name);
    Role createRole(RoleRequest roleRequest);

    Role updateRole(RoleRequest roleRequest);

    boolean deleteRoleById(Long id);
    Long getTotalRoles();

    boolean existsByNameAndIdNot(String name, Long id);
}
