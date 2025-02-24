package com.mycompany.airlinereservationsoftwaremaven.unit;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.userCreation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class userCreationTest {

    private static userCreation createUser;
    private static Connection con;
//opens mysql connection
    @BeforeAll
    public static void setUpClass() {
        createUser = new userCreation();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/airline", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

//Deletes from mysql table user and closes connection
    @AfterAll
    public static void tearDownClass() {
        try{
            String setSt = "DELETE FROM user";
            Statement setQuery = con.createStatement();
            setQuery.executeUpdate(setSt);
        }catch(SQLException e){
            e.printStackTrace();
        }
        createUser = null;
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Test Case ID: testUserCreation
     * Purpose: Test create user
     * Test setup: Input valid user info, then click add user button
     * Test Strategy: Stub
     * Input: Valid user input
     * Expected state: Passes
     */
    @Test
    public void testUserCreationMethod() {
        createUser.autoID();
        //createUser.isUniqueID();
        createUser.setFirstName("John");
        createUser.setLastName("Peter");
        createUser.setUsername("john");
        createUser.setPassword("123");
        JButton addUserBtn = createUser.getAddUserButton();
        addUserBtn.doClick();

        createUser.autoID();
        createUser.setFirstName("Bill");
        createUser.setLastName("Smith");
        createUser.setUsername("BillSmith");
        createUser.setPassword("BillSmith");
        //createUser.isValidPassword();
        JButton addUserBtn2 = createUser.getAddUserButton();
        addUserBtn2.doClick();
    }
    /**
     * Test Case ID: testUniqueID
     * Purpose: Input validation for customerID
     * Test setup: Set ID as unique unit
     * Test Strategy: Equivalence class
     * Input: UO009
     * Expected state: Pass
     */
    @Test
    public void testIsUniqueID() {
        createUser.setID("UO009");
        boolean result = createUser.isUniqueID();
        assertTrue(result);
    }
    /**
     * Test Case ID: testIsValidID
     * Purpose: Input validation for password
     * Test setup: Set password as invalid unit
     * Test Strategy: Equivalence class
     * Input: UO008
     * Expected state: Pass
     */
    @Test
    public void testIsValidID() {
        createUser.setID("UO008");
        boolean result = createUser.isValidID();
        assertTrue(result);
    }

    /**
     * Test Case ID: testIsValidPassword
     * Purpose: Input validation for password
     * Test setup: Set password as valid unit
     * Test Strategy: Equivalence class
     * Input: ThisIsOver8Char
     * Expected state: Pass
     */
    @Test
    public void testIsValidPassword() {
        createUser.setPassword("ThisIsOver8Char");
        boolean result = createUser.isValidPassword();
        assertTrue(result);
    }

    /**
     * Test Case ID: testIsValidPassword
     * Purpose: Input validation for password
     * Test setup: Set password as invalid unit
     * Test Strategy: Equivalence class
     * Input: Password = not8
     * Expected state: true
     */
    @Test
    public void testIsValidPasswordNeg() {
        createUser.setPassword("not8");
        boolean result = createUser.isValidPassword();
        assertFalse(result);
    }
}