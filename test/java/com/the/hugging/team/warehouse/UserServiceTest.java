package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Role;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.RoleService;
import com.the.hugging.team.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    private static Role admin;
    private static Role operator;

    @BeforeAll
    public static void setUp() {
        admin = RoleService.getInstance().getRoleBySlug("roles.admin");
        operator = RoleService.getInstance().getRoleBySlug("roles.operator");
    }

    @Test
    @DisplayName("Should get admin user")
    void shouldGetAdminUser() {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("");
        user.setUsername("admin");
        user.setRole(admin);
        user.setSex(1);
        user.setId(1);

        Assertions.assertEquals(user, UserService.getInstance().getUser(1));
    }

    @Test
    @DisplayName("Should get operator user")
    void shouldGetOperatorUser() {
        User user = new User();
        user.setFirstName("Operator");
        user.setLastName("");
        user.setUsername("operator");
        user.setRole(operator);
        user.setSex(1);
        user.setId(2);

        Assertions.assertEquals(user, UserService.getInstance().getUser(2));
    }

    @Test//TODO needs to be reworked
    @DisplayName("Should add new user")
    void shouldAddNewUser(){
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("");
        user.setUsername("admin");
        user.setRole(admin);
        user.setSex(1);
        user.setId(1);

        Assertions.assertEquals(user,UserService.getInstance().addUser(user));
    }
}
