package com.example.accounts.session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.accounts.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JwtTokenFilterTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    private static final String TOKEN = "validToken";
    private static final String USERNAME = "testUser";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        JwtTokenFilter.users = new HashMap<>();
        JwtTokenFilter.users.put(USERNAME, new User(USERNAME, "password"));

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void extractToken_ValidTokenHeader_ReturnsToken() {
        String tokenHeader = "Bearer " + TOKEN;
        when(request.getHeader("Authorization")).thenReturn(tokenHeader);

        String extractedToken = jwtTokenFilter.extractToken(request);

        assertEquals(TOKEN, extractedToken);
    }

    @Test
    void extractToken_InvalidTokenHeader_ReturnsNull() {
        String tokenHeader = "InvalidToken";
        when(request.getHeader("Authorization")).thenReturn(tokenHeader);

        String extractedToken = jwtTokenFilter.extractToken(request);

        assertNull(extractedToken);
    }

    @Test
    void doFilterInternal_ValidTokenAndUsername_TokenValidationSuccessful() throws ServletException, IOException {
        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn(USERNAME);
        when(jwtTokenUtil.validateJwtToken(any(), any(User.class))).thenReturn(true);

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_InvalidToken_TokenValidationFailed() throws ServletException, IOException {
        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn(USERNAME);
        when(jwtTokenUtil.validateJwtToken(any(), any(User.class))).thenReturn(false);

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

}