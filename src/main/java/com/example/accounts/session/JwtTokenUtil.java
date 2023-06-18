package com.example.accounts.session;

import com.example.accounts.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long EXPIRATION_TIME_MS = 5 * 60 * 1000; // 5 minutes

    @Value("${secret}")
    private String jwtSecret;

    @Autowired
    private TokenCacheManager tokenCacheManager;

    public String generateJwtTokenForAdmin(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("Role", "admin");
        String token = Jwts.builder().setClaims(claims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

        tokenCacheManager.storeToken(user.getUsername(), token);
        return token;
    }

//    public String generateJwtToken(Customer customer) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("First Name", customer.getFirstName());
//        claims.put("Last Name", customer.getFirstName());
//        claims.put("Mobile", customer.getMobile());
//        return Jwts.builder().setClaims(claims).setSubject(customer.getEmail())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()  + TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//    }

//    public Boolean validateJwtToken(String token, Customer customer) {
//        String email = getUsernameFromToken(token);
//        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
//        Boolean isTokenExpired = claims.getExpiration().before(new Date());
//        return (email.equals(customer.getEmail()) && !isTokenExpired);
//    }

    public Boolean validateJwtTokenForAdmin(String token, User user) {
        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(user.getUsername()) && !isTokenExpired);
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean checkAdminFromToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        if (claims.get("Role") != null && claims.get("Role").equals("admin")) {
            return true;
        }
        return false;
    }

}
