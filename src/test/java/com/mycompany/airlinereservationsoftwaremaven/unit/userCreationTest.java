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

    @AfterAll
    public static void tearDownClass() {
        createUser = null;
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsUniqueID() {
        try{
            createUser.setID("UO009");
            boolean result = createUser.isUniqueID();
            String setSt = "INSERT INTO user (id,firstname,lastname,username,password) VALUES('','fName', 'lName', 'testUser','123');";
            Statement setQuery = con.createStatement();
            setQuery.executeUpdate(setSt);
            assertTrue(result);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testIsValidID() {
        createUser.setID("UO008");
        createUser.isValidID();
    }

    // Minimum eight characters, at least one letter and one number
    //pos
    @Test
    public void testIsValidPassword() {
        createUser.setPassword("ThisIsOver8Char");
        boolean result = createUser.isValidPassword();
        assertTrue(result);
    }

    @Test
    public void testIsValidPasswordNeg() {
        createUser.setPassword("not8");
        boolean result = createUser.isValidPassword();
        assertFalse(result);
    }
}