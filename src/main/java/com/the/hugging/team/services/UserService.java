package com.the.hugging.team.services;

import com.the.hugging.team.entities.User;
import com.the.hugging.team.repositories.UserRepository;

import java.util.List;

public class UserService {
    private static UserService INSTANCE = null;
    private final UserRepository userRepository = UserRepository.getInstance();

    public static UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }

        return INSTANCE;
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User getUser(int id) {
        return userRepository.getById(id).orElse(null);
    }

    private boolean checkPassword(User user, String password) {
        //to be changed in when we have hashed passwords in the db
        //return Hash.check(password, user.getPassword());
        return user.getPassword().equals(password);
    }

    public User getAuthUser(String username, String password) {
        User user = userRepository.getByUsername(username);
        if (user != null && checkPassword(user, password)) {
            return user;
        }
        return null;
    }
}
