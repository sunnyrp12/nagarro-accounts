package com.example.accounts.session;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.accounts.entity.User;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JwtTokenUtilTest {

    @Mock
    private TokenCacheManager tokenCacheManager;

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    private static final String JWT_SECRET = "test-secret";
    private static final long EXPIRATION_TIME_MS = 5 * 60 * 1000L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtTokenUtil, "jwtSecret", JWT_SECRET);
    }

    @Test
    void generateJwtToken_ValidUser_ReturnsToken() {
        User user = new User();
        user.setUsername("testUser");

        String token = jwtTokenUtil.generateJwtToken(user);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void validateJwtToken_ValidTokenAndUser_ReturnsTrue() {
        User user = new User();
        user.setUsername("testUser");

        String token = generateValidToken(user.getUsername());

        boolean isValid = jwtTokenUtil.validateJwtToken(token, user);

        assertTrue(isValid);
    }

    @Test
    void getUsernameFromToken_ValidToken_ReturnsUsername() {
        String username = "testUser";
        String token = generateValidToken(username);

        String extractedUsername = jwtTokenUtil.getUsernameFromToken(token);

        assertEquals(username, extractedUsername);
    }

    private String generateValidToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
    }

}