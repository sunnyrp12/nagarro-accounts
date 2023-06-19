package com.example.accounts.session;

import com.example.accounts.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static Map<String, User> users = new HashMap<>();

    public static final String ADMIN = "admin";
    public static final String USER = "user";

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private JwtTokenFilter() {
        users.put(ADMIN, new User(ADMIN, ADMIN));
        users.put(USER, new User(USER, USER));

    }

    public String extractToken(HttpServletRequest request) {
        String token = null;
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
        } else {
            logger.info("Bearer String not found in token");
        }
        return token;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = null;
        String token = extractToken(request);

        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (IllegalArgumentException e) {
            logger.error("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired");
        }

        if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = users.get(username);
            if (jwtTokenUtil.validateJwtToken(token, user)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, new ArrayList<>());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
