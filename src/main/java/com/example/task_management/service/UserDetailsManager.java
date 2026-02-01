package com.example.task_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.task_management.entity.UserEntity;
import com.example.task_management.repository.UserRepository;

@Service
public class UserDetailsManager implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            return user.toUserDetails();
        }
        throw new UsernameNotFoundException("Not found user with username: " + username + ".");
    }
    
}
