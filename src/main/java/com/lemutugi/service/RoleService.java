package com.lemutugi.service;

import com.lemutugi.model.Privilege;
import com.lemutugi.model.Role;
import com.lemutugi.model.enums.ERole;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoleService {
    Page<Role> getAllRoles(int pageNo, int pageSize, String sortField, String sortDirection);
    Page<Privilege> getAllPrivileges(int pageNo, int pageSize, String sortField, String sortDirection);
    Page<Privilege> getPrivilegesByRole(Long roleId, int pageNo, int pageSize, String sortField, String sortDirection);
    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleByName(String name);
    Optional<Privilege> getPrivilegeById(Long id);
    Optional<Privilege> getPrivilegeByName(String name);
    Privilege savePrivilege( Privilege privilege);
    Role saveRole( Role role);
    boolean deleteRoleById(Long id);
    boolean deletePrivilegeById(Long id);

    Long getTotalRoles();

    Long getTotalPrivileges();
}
