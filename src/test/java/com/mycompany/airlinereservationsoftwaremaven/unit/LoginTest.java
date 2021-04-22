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
                + "('Bill', 'Billing', 'test', 'testpass', 9000, 2, '2019-06-15'),\n";
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


    @Test
    public void testIsPoplutatedPos() {
        login.main(args);
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

    @Test
    public void testValidLoginNegUser() {
        login.setuser("");
        login.setpass("real");
        login.jButton1.doClick();
    }

    @Test
    public void testValidLoginNegPass() {
        login.setuser("not");
        login.setpass("");
        login.jButton1.doClick();
    }
}