package com.lemutugi.service.impl;

import com.lemutugi.exceptions.NotFoundException;
import com.lemutugi.model.Privilege;
import com.lemutugi.payload.request.PrivilegeRequest;
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
    public Privilege getPrivilegeById(Long id) {
        return privilegeRepository.findById(id).orElseThrow(() -> new NotFoundException("No privilege found with id: " + id));
    }

    @Override
    public boolean existsByName(String name) {
        return privilegeRepository.existsByName(name);
    }

    @Override
    public Optional<Privilege> getByName(String name){
        return privilegeRepository.findByName(name);
    }

    @Override
    public Privilege updatePrivilege(PrivilegeRequest privilegeRequest) {
        Optional<Privilege> optionalPrivilege = privilegeRepository.findByName(privilegeRequest.getName());

        if (optionalPrivilege.isPresent()) return optionalPrivilege.get();

        Privilege privilege = privilegeRepository.findById(privilegeRequest.getId()).orElseThrow(() -> new NotFoundException("No privilege found with id: " + privilegeRequest.getId()));
        privilege.setName(privilegeRequest.getName());
        return privilegeRepository.save(privilege);
    }

    @Override
    public Privilege createPrivilege(PrivilegeRequest privilegeRequest) {
        Privilege privilege = new Privilege(privilegeRequest.getName());
        return privilegeRepository.save(privilege);
    }

    @Override
    public boolean deletePrivilegeById(Long id) {
        if (!privilegeRepository.existsById(id)) throw new NotFoundException("No privilege found with id: " + id);

        try{
            privilegeRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Long getTotalPrivileges(){
        return privilegeRepository.count();
    }
}
