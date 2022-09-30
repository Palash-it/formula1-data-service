package com.recommit.assignment.formula1.formula1dataservice.configurations;

import com.recommit.assignment.formula1.formula1dataservice.entity.RoleEntity;
import com.recommit.assignment.formula1.formula1dataservice.entity.UserEntity;
import com.recommit.assignment.formula1.formula1dataservice.repository.RoleRepository;
import com.recommit.assignment.formula1.formula1dataservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoaded implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // insert initial user: admin pass: admin if its not exist
        if (!userRepository.existsByUsername("admin")) {
            RoleEntity role = new RoleEntity(1, "ADMIN", "Full permission");
            role = roleRepository.save(role);

            UserEntity user = new UserEntity(1L, "admin", passwordEncoder.encode("admin"),
                    true, Collections.singleton(role));
            user = userRepository.save(user);
            log.info("====[DB changes: User data has been inserted: User ID: {}]=====", user.getId());
        }
    }
}
