package com.example.accounts.service.impl;

import com.example.accounts.dto.Response;
import com.example.accounts.entity.User;
import com.example.accounts.service.AuthService;
import com.example.accounts.session.JwtTokenFilter;
import com.example.accounts.session.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public Response login(String username, String password) {
        logger.info("Enter into login for user: {}", username);
        Map<String, User> users = JwtTokenFilter.users;
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            final String token = jwtTokenUtil.generateJwtToken(user);
            logger.info("Login successful for user: {}", username);
            return new Response(false, "Login Successful", token);
        }

        logger.warn("Login failed for user: {}", username);
        return new Response(true, "Invalid Credentials", null);
    }
}
