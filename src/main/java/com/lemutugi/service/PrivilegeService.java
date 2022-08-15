package com.lemutugi.service;

import com.lemutugi.model.Privilege;
import com.lemutugi.payload.request.PrivilegeRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PrivilegeService {
    Page<Privilege> getAllPrivileges(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Privilege> getPrivilegesByRole(Long roleId, int pageNo, int pageSize, String sortField, String sortDirection);

    Optional<Privilege> getPrivilegeById(Long id);

    Optional<Privilege> getPrivilegeByName(String name);

    Privilege updatePrivilege(PrivilegeRequest privilegeRequest);

    Privilege createPrivilege(PrivilegeRequest privilegeRequest);

    boolean deletePrivilegeById(Long id);

    Long getTotalPrivileges();
}
