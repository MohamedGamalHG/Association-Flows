package com.mg.Association_Flows.security;

import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.domain.repo.UserRepository;
import com.mg.Association_Flows.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails userDetails = new User();
        return new CustomUserDetailsService(userRepository);
    }
}
