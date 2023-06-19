package com.example.accounts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.example.accounts.dto.Response;
import com.example.accounts.service.AuthService;
import com.example.accounts.session.TokenCacheManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletResponse;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private TokenCacheManager tokenCacheManager;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testLogin_WithActiveSession() throws IOException {
        String username = "testUser";
        String password = "testPassword";
        String activeToken = "testToken";

        when(tokenCacheManager.retrieveToken(username)).thenReturn(activeToken);

        Response response = authController.login(username, password);

        assertTrue(response.isErrorStatus());
        assertEquals("User has an Active session", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testLogin_WithoutActiveSession() throws IOException {
        String username = "testUser";
        String password = "testPassword";
        String generatedToken = "testGeneratedToken";

        when(tokenCacheManager.retrieveToken(username)).thenReturn(null);
        when(authService.login(username, password)).thenReturn(new Response(false, "Login Successful", generatedToken));

        Response response = authController.login(username, password);

        assertFalse(response.isErrorStatus());
        assertEquals("Login Successful", response.getMessage());
        assertEquals(generatedToken, response.getData());
    }

}