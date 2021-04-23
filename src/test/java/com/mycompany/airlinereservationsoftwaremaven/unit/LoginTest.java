package com.mycompany.airlinereservationsoftwaremaven.unit;

import static org.junit.jupiter.api.Assertions.*;
import com.mycompany.airlinereservationsoftwaremaven.Login;
import com.mycompany.airlinereservationsoftwaremaven.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class LoginTest {

    Login login = new Login();
    private static Connection con;
    String [] args;
    static com.mycompany.airlinereservationsoftwaremaven.ticketService ticketService;

    //Test setup before all tests
    @BeforeAll
    public static void setUpClass() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
            Statement st = con.createStatement();
            st.close();
        }catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String query3 = "INSERT INTO `user` (`id`, `firstname`, `lastname`, `username`, `password`) VALUES\n"
                + "('John', 'Peter', 'john', '123'),\n"
                + "('Bill', 'Billing', 'test', 'testpass'),\n";
            Statement sta = con.createStatement();
            sta.executeUpdate(query3);
            sta.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDownClass() throws SQLException {

        try {
            String query3 = "DELETE FROM user";
            Statement st = con.createStatement();
            st.executeUpdate(query3);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.close();
    }

    /**
     * Test Case ID: LoginTest1
     * Purpose: Test negative login input
     * Test setup: Set user and password invalid, call login button
     * Test Strategy: Equivalence class testing
     * Input: invalid login
     * Expected state: Passes, returns empty login
     */
    @Test
    public void testIsPoplutatedPos() {
        login.main(args);
        login.setuser("there is something");
        login.setpass("here");
        assertEquals(true,!login.isEmpty());
    }

    /**
     * Test Case ID: LoginTest2
     * Purpose: Test null login input
     * Test setup: Set user and password invalid, call login button
     * Test Strategy: Equivalence class testing
     * Input: invalid login
     * Expected state: Passes, returns empty login
     */
    @Test
    public void testIsPopulatedNeg() {
        login.setuser("");
        login.setpass("");
        assertEquals(true,login.isEmpty());
    }

    /**
     * Test Case ID: LoginTest3
     * Purpose: Test valid login input
     * Test setup: Set user and password invalid, call login button
     * Test Strategy: Stub
     * Input: valid login
     * Expected state: Passes
     */
    @Test
    public void testValidLoginPos() {
        login.setuser("john");
        login.setpass("123");
        login.jButton1.doClick();
    }

    /**
     * Test Case ID: LoginTest4
     * Purpose: Test null login input
     * Test setup: Set user and password invalid, call login button
     * Test Strategy: Equivalence class testing
     * Input: invalid login
     * Expected state: Passes, returns empty login
     */
    @Test
    public void testValidLoginNeg() {
        login.setuser("not");
        login.setpass("real");
        login.jButton1.doClick();
        assertEquals(true,login.isEmpty());
    }

    /**
     * Test Case ID: LoginTest5
     * Purpose: Test null login input
     * Test setup: Set user and password invalid, call login button
     * Test Strategy: Equivalence class testing
     * Input: invalid login
     * Expected state: Passes, returns empty login
     */
    @Test
    public void testValidLoginNegUser() {
        login.setuser("");
        login.setpass("real");
        login.jButton1.doClick();
        assertEquals(true,login.isEmpty());
    }

    /**
     * Test Case ID: LoginTest6
     * Purpose: Test null login input
     * Test setup: Set user and password invalid, call login button
     * Test Strategy: Equivalence class testing
     * Input: invalid login
     * Expected state: Passes, returns empty login
     */
    @Test
    public void testValidLoginNegPass() {
        login.setuser("not");
        login.setpass("");
        login.jButton1.doClick();
        assertEquals(true,login.isEmpty());
    }
}