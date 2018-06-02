package com.lungesoft.architecture.oauth.server.service;

import com.lungesoft.architecture.oauth.server.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceMock implements UserService {

    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("admin");
        user1.setPassword("admin");
        user1.setRole("ADMIN");

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user");
        user2.setPassword("user");
        user2.setRole("USER");

        users.add(user1);
        users.add(user2);
    }

    @Override
    public User retrieveUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

}
