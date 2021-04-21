package com.mycompany.airlinereservationsoftwaremaven.unit;

import static org.junit.jupiter.api.Assertions.*;
import com.mycompany.airlinereservationsoftwaremaven.Login;
import org.junit.jupiter.api.Test;


class LoginTest {

    Login login = new Login();

    @Test
    public void testIsPoplutatedPos() {
        login.setuser("there is something");
        login.setpass("here");
        assertEquals(true,!login.isEmpty());
    }

    @Test
    public void testIsPopulatedNeg() {
        login.setuser("");
        login.setpass("");
        assertEquals(true,login.isEmpty());
    }

    @Test
    public void testValidLoginPos() {
        login.setuser("john");
        login.setpass("123");
        login.jButton1.doClick();
    }

    @Test
    public void testValidLoginNeg() {
        login.setuser("not");
        login.setpass("real");
        login.jButton1.doClick();
    }
}