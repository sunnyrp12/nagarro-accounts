package com.example.accounts.controller;

import com.example.accounts.dto.Response;
import com.example.accounts.service.AuthService;
import com.example.accounts.session.TokenCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private TokenCacheManager tokenCacheManager;
    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping
    public Response login(@RequestParam String username,
                          @RequestParam String password) {
        logger.info("Received login request for username: {}", username);

        String token = tokenCacheManager.retrieveToken(username);
        if (token != null) {
            logger.info("User '{}' has an active session", username);
            return new Response(true, "User has an Active session", null);
        }

        logger.info("Login Successful for user: {}", username);
        return authService.login(username, password);
    }



}
