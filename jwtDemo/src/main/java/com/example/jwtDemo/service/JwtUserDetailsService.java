package com.example.jwtDemo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("karthick".equals(username)) {
            return new User("karthick", "$2a$10$lkeZ5e9RIcLasT2KGTBRYOqDaB4Qnt7sTaAi5LKjvrXAYqkAZF9PK", new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
