package com.example.task_management;

import java.time.OffsetDateTime;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.task_management.entity.RoleEntity;
import com.example.task_management.entity.TaskEntity;
import com.example.task_management.entity.UserEntity;
import com.example.task_management.entity.TaskEntity.Priority;
import com.example.task_management.repository.RoleRepository;
import com.example.task_management.repository.TaskRepository;
import com.example.task_management.repository.UserRepository;

@Configuration
public class DataInitializer {    
    void createUsers(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        RoleEntity userRole = roleRepository.findByName("USER"); 

        for (int i = 1; i <= 20; i++) {
            String username = "user" + i;
            userRepository.save(UserEntity.builder()
                    .name("User " + i)
                    .email(username + "@gmail.com")
                    .username(username)
                    .password(passwordEncoder.encode(username))
                    .roles(Set.of(userRole))
                    .build());
        }
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, TaskRepository taskRepository) {
        return args -> {
            RoleEntity userRole = RoleEntity.builder().name("USER").build();
            RoleEntity adminRole = RoleEntity.builder().name("ADMIN").build();

            roleRepository.save(userRole);
            roleRepository.save(adminRole);

            UserEntity user = UserEntity.builder()
                    .name("truong")
                    .email("truongpham290701@gmail.com")
                    .username("truong")
                    .password(passwordEncoder.encode("truong"))
                    .roles(Set.of(userRole))
                    .build();
            UserEntity admin = UserEntity.builder()
                    .name("admin")
                    .email("admin@abc.com")
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(adminRole, userRole))
                    .build();

            userRepository.save(admin);
            userRepository.save(user);

            taskRepository.save(TaskEntity.builder()
                    .name("display task")
                    .description("Project Alpha")
                    .priority(Priority.HIGH)
                    .dueDate(OffsetDateTime.now())
                    .isDone(false)
                    .owner(admin)
                    .build());

            taskRepository.save(TaskEntity.builder()
                    .name("add task")
                    .description("")
                    .priority(Priority.MEDIUM)
                    .dueDate(OffsetDateTime.now())
                    .isDone(false)
                    .owner(admin)
                    .build());

            createUsers(userRepository, roleRepository, passwordEncoder);
        };
    }
}
