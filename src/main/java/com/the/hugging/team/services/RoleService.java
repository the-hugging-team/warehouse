package com.the.hugging.team.services;

import com.the.hugging.team.entities.Role;
import com.the.hugging.team.repositories.RoleRepository;

public class RoleService {
    private static RoleService INSTANCE = null;
    private final RoleRepository roleRepository = RoleRepository.getInstance();

    public static RoleService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleService();
        }

        return INSTANCE;
    }

    public Role getRoleBySlug(String slug) {
        return roleRepository.getBySlug(slug).orElse(null);
    }
}
