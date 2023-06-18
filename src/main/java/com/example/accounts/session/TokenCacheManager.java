package com.example.accounts.session;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TokenCacheManager {

    private final Cache<String, String> tokenCache;

    public TokenCacheManager() {
        // Initialize the cache with a desired configuration
        tokenCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES) // Token expiration time
                .maximumSize(1000) // Maximum number of tokens to store
                .build();
    }

    public void storeToken(String username, String token) {
        tokenCache.put(username, token);
    }

    public String retrieveToken(String username) {
        return tokenCache.getIfPresent(username);
    }

    public void removeToken(String username) {
        tokenCache.invalidate(username);
    }
}
