package com.lemutugi.service.impl;

import com.lemutugi.model.Privilege;
import com.lemutugi.model.Role;
import com.lemutugi.repository.PrivilegeRepository;
import com.lemutugi.repository.RoleRepository;
import com.lemutugi.service.RoleService;
import com.lemutugi.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Page<Role> getAllRoles(int pageNo, int pageSize, String sortField, String sortDirection) {
        return roleRepository.findAll(Pager.createPageable(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Page<Privilege> getAllPrivileges(int pageNo, int pageSize, String sortField, String sortDirection) {
        return privilegeRepository.findAll(Pager.createPageable(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Page<Privilege> getPrivilegesByRole(Long roleId, int pageNo, int pageSize, String sortField, String sortDirection) {
        return privilegeRepository.findByRoleId(roleId, Pager.createPageable(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Optional<Privilege> getPrivilegeById(Long id) {
        return privilegeRepository.findById(id);
    }

    @Override
    public Optional<Privilege> getPrivilegeByName(String name) {
        return privilegeRepository.findByName(name);
    }

    @Override
    public Privilege savePrivilege(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public boolean deleteRoleById(Long id) {
        try{
            roleRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletePrivilegeById(Long id) {
        try{
            privilegeRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Long getTotalRoles(){
        return roleRepository.count();
    }

    @Override
    public Long getTotalPrivileges(){
        return privilegeRepository.count();
    }
}
