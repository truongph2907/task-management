package com.example.task_management.DTO;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;

@Data
public class CustomUserPrincipal {
    
    private String username;
    private String email;

    public CustomUserPrincipal(Object principal) {
        if (principal instanceof OAuth2User oAuth2User) {
            this.email = oAuth2User.getAttribute("email");
            this.username = null;
        } else if (principal instanceof UserDetails userDetails) {
            this.username = userDetails.getUsername();
            this.email = null;
        }
    }
}
