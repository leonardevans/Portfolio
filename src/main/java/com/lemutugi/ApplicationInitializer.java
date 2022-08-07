package com.lemutugi;

import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.model.enums.ERole;
import com.lemutugi.repository.RoleRepository;
import com.lemutugi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationInitializer  implements CommandLineRunner {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData(){
        //check if USER role exists, if not create it and save
        if (!roleRepository.existsByName(ERole.ROLE_USER.name())){
            roleRepository.save(new Role(ERole.ROLE_USER.name()));
        }

        //check if ADMIN role exists, if not create it and save
        if (!roleRepository.existsByName(ERole.ROLE_ADMIN.name())){
            roleRepository.save(new Role(ERole.ROLE_ADMIN.name()));
        }

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

            //            get user role and add this role to admin
            Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER.name());
            userRole.ifPresent(role -> admin.getRoles().add(role));

//            get admin role and add this role to admin
            Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN.name());
            adminRole.ifPresent(role -> admin.getRoles().add(role));

//            save this user
            userRepository.save(admin);
        }
    }
}