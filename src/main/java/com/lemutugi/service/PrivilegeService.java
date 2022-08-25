package com.lemutugi.service;

import com.lemutugi.model.Privilege;
import com.lemutugi.payload.request.PrivilegeRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PrivilegeService {
    Page<Privilege> getAllPrivileges(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Privilege> search(int pageNo, int pageSize, String sortField, String sortDirection, String search);

    Page<Privilege> getPrivilegesByRole(Long roleId, int pageNo, int pageSize, String sortField, String sortDirection);

    Privilege getPrivilegeById(Long id);


    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    Optional<Privilege> getByName(String name);

    Privilege updatePrivilege(PrivilegeRequest privilegeRequest);

    Privilege createPrivilege(PrivilegeRequest privilegeRequest);

    boolean deletePrivilegeById(Long id);

    Long getTotalPrivileges();
}
