package com.example.accounts.service;

import com.example.accounts.dto.Response;

public interface AuthService {
    Response login(String username, String password);
}
