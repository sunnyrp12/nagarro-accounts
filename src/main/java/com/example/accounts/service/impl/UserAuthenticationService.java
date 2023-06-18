package com.example.accounts.service.impl;

import com.example.accounts.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserAuthenticationService {

    private Map<String, User> users;

    public UserAuthenticationService() {

        users = new HashMap<>();
        users.put("Admin", new User("admin", "admin"));
        users.put("User", new User("user", "user"));

    }
}
