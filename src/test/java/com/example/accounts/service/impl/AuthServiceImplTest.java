package com.example.accounts.service.impl;

import com.example.accounts.dto.Response;
import com.example.accounts.entity.User;
import com.example.accounts.service.AuthService;
import com.example.accounts.session.JwtTokenFilter;
import com.example.accounts.session.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_withValidCredentials_successResponse() {
        String username = "user1";
        String password = "password1";

        Map<String, User> users = new HashMap<>();
        users.put(username, new User(username, password));
        JwtTokenFilter.users = users;
        when(jwtTokenUtil.generateJwtToken(any(User.class))).thenReturn("jwtToken");
        Response result = authService.login(username, password);
        assertEquals(new Response(false, "Login Successful", "jwtToken"), result);

    }

    @Test
    void login_withInvalidCredentials_errorResponse() {
        String username = "user1";
        String password = "incorrectPassword";

        Map<String, User> users = new HashMap<>();
        users.put(username, new User(username, "password1"));

        JwtTokenFilter.users = users;
        Response result = authService.login(username, password);
        assertEquals(new Response(true, "Invalid Credentials", null), result);
    }


}