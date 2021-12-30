package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Role;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.repositories.UserRepository;
import com.the.hugging.team.services.RoleService;
import com.the.hugging.team.services.UserService;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    public static String generateRandomString(int from, int to) {
        StringBuilder randomString = new StringBuilder();
        int length = (int) (Math.random() * (to - from) + from);
        String characters = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }

    private static User newUser;
    private final UserService userService = UserService.getInstance();

    private static Role admin;
    private static Role operator;

    @BeforeAll
    public static void setUp() {
        admin = RoleService.getInstance().getRoleBySlug("roles.admin");
        operator = RoleService.getInstance().getRoleBySlug("roles.operator");
    }

    @Test
    @Order(1)
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
    @Order(2)
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

    @Test
    @Order(3)
    @DisplayName("Should add new user")
    void shouldAddNewUser() {
        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setUsername(generateRandomString(1,5));
        user.setRole(operator);
        user.setSex(1);
        user.setCreatedBy(userService.getUser(1));
        user.setPassword("test");
        user.setUpdatedBy(userService.getUser(1));
        newUser = userService.addUser(user);

        Assertions.assertTrue(userService.getAllUsers().contains(newUser));
    }

    @Test
    @Order(4)
    @DisplayName("Should update user")
    void shouldUpdateUser(){
        newUser.setUsername("testUPDATE");
        userService.updateUser(newUser);

        Assertions.assertEquals("testUPDATE",newUser.getUsername());
    }
}
