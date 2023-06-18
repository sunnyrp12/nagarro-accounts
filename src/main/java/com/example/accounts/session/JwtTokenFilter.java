package com.example.accounts.session;

import com.example.accounts.entity.User;
import com.example.accounts.service.impl.UserAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
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


//    UserAuthenticationService userAuthenticationService;

    public static Map<String, User> users;

    public JwtTokenFilter() {

        users = new HashMap<>();
        users.put("admin", new User("admin", "admin"));
        users.put("user", new User("user", "user"));

    }

    public static String extractToken(HttpServletRequest request) {
        String token = null;
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
        } else {
            System.out.println("Bearer String not found in token");
        }
        return token;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = null;
        String token = extractToken(request);
//        Customer customer= null;

        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
//                if (!jwtTokenUtil.checkAdminFromToken(token)) {
//                    String username = Integer.valueOf(request.getParameter("username"));
//                    customer = customerRepository.findByCustomerId(customerId);
//                }

//                username = jwtTokenUtil.getUsernameFromToken(token);

        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }

        if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.checkAdminFromToken(token)) {
//                Admin admin = adminRepository.findByEmail(username);
                User user = users.get(username);
                if (jwtTokenUtil.validateJwtTokenForAdmin(token, user)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, new ArrayList<>());
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
//            else {
////                Customer customer = customerRepository.findByEmail(username);
//                if (jwtTokenUtil.validateJwtToken(token, customer)) {
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                            customer, null, new ArrayList<>());
//                    authenticationToken.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(request)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            }
        }
        filterChain.doFilter(request, response);
    }
}