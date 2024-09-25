package com.backend;

import com.models.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    User sampleUser = new User("testUser123", "Test", "User", "test", false);
    @Test
    @Order(1)
    void addUser() {
        var userController = new UserController();
        boolean status = userController.addUser(sampleUser.getUsername(), sampleUser.getFirstname(),
                sampleUser.getLastname(), sampleUser.getPassword());
        assertEquals(true, status);
    }

    @Test
    @Order(2)
    void tryLogin() {
        var userController = new UserController();
        int wrongCreds = userController.tryLogin(sampleUser.getUsername(), "wrongpassword");
        assertEquals(1, wrongCreds);
        int correctCreds = userController.tryLogin(sampleUser.getUsername(), sampleUser.getPassword());
        assertEquals(0, correctCreds);
    }

    @Test
    @Order(2)
    void upgradeToVip() {
        var userController  = new UserController();
        assertEquals(true, userController.upgradeToVip(sampleUser.getUsername()));
    }

    @Test
    @Order(3)
    void deleteUser(){
        var userController = new UserController();
        assertEquals(true, userController.isUsernameTaken(sampleUser.getUsername()));
        assertEquals(true, userController.deleteUser(sampleUser.getUsername()));
        assertEquals(false, userController.isUsernameTaken(sampleUser.getUsername()));
    }
}