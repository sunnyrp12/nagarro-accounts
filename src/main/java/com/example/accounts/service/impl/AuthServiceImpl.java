package com.example.accounts.service.impl;

import com.example.accounts.dto.Response;
import com.example.accounts.entity.User;
import com.example.accounts.service.AuthService;
import com.example.accounts.session.JwtTokenFilter;
import com.example.accounts.session.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Response login(String username, String password) {
        Map<String, User> users = JwtTokenFilter.users;
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            final String token = jwtTokenUtil.generateJwtTokenForAdmin(user);
            return new Response(false, "Login Successful", token);
        }
        return new Response(true, "Login Failed", null);
    }
}
