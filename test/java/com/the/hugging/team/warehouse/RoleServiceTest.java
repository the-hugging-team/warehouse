package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Role;
import com.the.hugging.team.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RoleServiceTest {

    @ParameterizedTest
    @DisplayName("Should get role by slug")
    @ValueSource(strings = {"roles.admin", "roles.operator"})
    void shouldGetRoleBySlug(String slug) {
        Role role = new Role();
        role.setSlug(slug);
        role.setName(slug.contains("admin") ? "Admin" : "Operator");
        Assertions.assertEquals(role, RoleService.getInstance().getRoleBySlug(slug));
    }
}
