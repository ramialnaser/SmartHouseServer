package com.SmartHouse.Database;

import com.SmartHouse.Models.Device;
import org.junit.Test;


import static org.junit.Assert.*;

public class DBConnectionTest {

    private DBConnection dbConnection = new DBConnection();
    @Test
    public void emailExists() {
        assertEquals(true, dbConnection.emailExists("nawar@hotmail.com"));
    }

    @Test
    public void validateLogin() {
        assertEquals(true, dbConnection.validateLogin("nawar@hotmail.com", "test123"));
    }

    @Test
    public void getPassword() {

        assertEquals("test12345",dbConnection.getPassword("nawar@hotmail.com"));
    }

    @Test
    public void getFullName() {
        assertEquals("Nawar Aghi",dbConnection.getFullName("nawar@hotmail.com"));
    }

    @Test
    public void updatePassword() {
        assertEquals(true,dbConnection.updatePassword("nawar@hotmail.com", "test12345"));
    }


    @Test
    public void sendEmail() {
        assertEquals(true, dbConnection.sendEmail("nawarsyr@hotmail.com"));
    }
}