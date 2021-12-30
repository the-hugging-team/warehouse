package com.the.hugging.team.services;

import com.the.hugging.team.entities.Role;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.repositories.UserRepository;
import com.the.hugging.team.utils.Hasher;

import java.util.List;
import java.util.Set;

public class UserService {
    private static UserService INSTANCE = null;
    private final UserRepository userRepository = UserRepository.getInstance();
    private final RoleService roleService = RoleService.getInstance();

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
        return Hasher.check(password, user.getPassword());
    }

    public User getAuthUser(String username, String password) {
        User user = userRepository.getByUsername(username);
        if (user != null && checkPassword(user, password)) {
            return user;
        }
        return null;
    }

    public User addUser(User user) {
        if (user.getRole() == null) {
            Role role = roleService.getRoleBySlug(Role.ROLE_OPERATOR);
            user.setRole(role);
        }

        userRepository.save(user);
        return user;
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public List<User> getUsersByRoles(Set<Role> roles) {
        return userRepository.getByRoles(roles);
    }
}
