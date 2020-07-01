package com.SmartHouse.Services;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService = new UserService();



    @Test
    public void sendEmail() {
        assertEquals(true, userService.sendEmail("nawarsyr@hotmail.com"));
    }
}