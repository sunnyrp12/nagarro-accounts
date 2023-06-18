package com.example.accounts.controller;

import com.example.accounts.dto.Response;
import com.example.accounts.service.AuthService;
import com.example.accounts.session.TokenCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private TokenCacheManager tokenCacheManager;
    @Autowired
    private AuthService authService;

    @PostMapping
    public Response login(@RequestParam String username,
                          @RequestParam String password,
                          HttpServletResponse httpServletResponse) {
        String token = tokenCacheManager.retrieveToken(username);
        if (token != null) {
            return new Response(true, "User has an Active session", null);
        }
        return authService.login(username, password);
    }



}
