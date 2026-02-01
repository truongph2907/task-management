package com.example.task_management.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.task_management.entity.UserEntity;
import com.example.task_management.repository.RoleRepository;
import com.example.task_management.repository.UserRepository;

@Service
public class OAuth2UserManager implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User user = delegate.loadUser(userRequest);

        UserEntity userEntity = userRepository.findByEmail(user.getAttribute("email"));
        if (userEntity == null) {
            // create new user
            userEntity = UserEntity.builder().name(user.getAttribute("name")).email(user.getAttribute("email"))
                .username(UUID.randomUUID().toString())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .roles(Set.of(roleRepository.findByName("USER")))
                .build();
            userRepository.save(userEntity);
        }
        return user;
    }
    
}
