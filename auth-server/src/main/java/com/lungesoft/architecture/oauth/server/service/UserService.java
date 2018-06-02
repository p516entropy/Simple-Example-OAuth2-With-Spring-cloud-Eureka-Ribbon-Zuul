package com.lungesoft.architecture.oauth.server.service;

import com.lungesoft.architecture.oauth.server.model.User;

public interface UserService {
    User retrieveUserByUsername(String username);
}
