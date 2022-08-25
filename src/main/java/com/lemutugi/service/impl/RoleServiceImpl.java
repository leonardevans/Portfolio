package com.lemutugi.service.impl;

import com.lemutugi.exceptions.NotFoundException;
import com.lemutugi.model.Role;
import com.lemutugi.payload.request.RoleRequest;
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

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> getAllRoles(int pageNo, int pageSize, String sortField, String sortDirection) {
        return roleRepository.findAll(Pager.createPageable(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Page<Role> search(int pageNo, int pageSize, String sortField, String sortDirection, String search) {
        return roleRepository.searchByName(Pager.createPageable(pageNo, pageSize, sortField, sortDirection), search);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("No role found with id: " + id));
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role createRole(RoleRequest roleRequest) {
        Role role = new Role(roleRequest);
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(RoleRequest roleRequest) {

        Role role = roleRepository.findById(roleRequest.getId()).orElseThrow(() -> new NotFoundException("No role found with id: " + roleRequest.getId()));
        role.setName(roleRequest.getName());
        role.setPrivileges(roleRequest.getPrivileges());
        return roleRepository.save(role);
    }

    @Override
    public boolean deleteRoleById(Long id) {
        if (!roleRepository.existsById(id)) throw new NotFoundException("No role found with id: " + id);

        try{
            roleRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Long getTotalRoles(){
        return roleRepository.count();
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return roleRepository.existsByNameAndIdNot(name, id);
    }
}
