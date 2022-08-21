package com.lemutugi;

import com.lemutugi.model.Privilege;
import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.model.enums.EPrivilege;
import com.lemutugi.model.enums.ERole;
import com.lemutugi.repository.PrivilegeRepository;
import com.lemutugi.repository.RoleRepository;
import com.lemutugi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ApplicationInitializer  implements CommandLineRunner {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public ApplicationInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData(){
        //create privileges
        Arrays.stream(EPrivilege.values()).forEach(ePrivilege -> {
            if (!privilegeRepository.existsByName(ePrivilege.name())){
                 privilegeRepository.save(new Privilege(ePrivilege.name()));
            }
        });

        List<Privilege> privileges = privilegeRepository.findAll();
        //create roles
        Arrays.stream(ERole.values()).forEach(eRole -> {
            if (!roleRepository.existsByName(eRole.name())){
                if (eRole.equals(ERole.ROLE_SUPERADMIN)){
                    Role superAdmin = new Role(eRole.name());
                    superAdmin.getPrivileges().addAll(privileges);
                    roleRepository.save(superAdmin);
                }else{
                    roleRepository.save(new Role(eRole.name()));
                }
            }
        });

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        String adminEmail = "lemutugi@gmail.com";
        // check if there's admin user, if not add user with role admin
        if (!userRepository.existsByEmail(adminEmail)){
            User admin = new User();
            admin.setUsername("lemutugi");
            admin.setFName("Leonard");
            admin.setLName("Mutugi");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("lemutugi2022"));
            admin.setEnabled(true);
            admin.setEmail_verified(true);
            admin.setProvider(AuthProvider.local);

            List<Role> roles = roleRepository.findAll();
            admin.getRoles().addAll(roles);

//            save this user
            userRepository.save(admin);
        }
    }
}