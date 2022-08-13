package com.lemutugi.service.impl;

import com.lemutugi.model.Privilege;
import com.lemutugi.repository.PrivilegeRepository;
import com.lemutugi.service.PrivilegeService;
import com.lemutugi.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
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
    public Long getTotalPrivileges(){
        return privilegeRepository.count();
    }
}
