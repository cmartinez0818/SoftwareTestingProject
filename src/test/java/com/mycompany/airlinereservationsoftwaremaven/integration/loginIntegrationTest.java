package com.mycompany.airlinereservationsoftwaremaven.integration;

import com.mycompany.airlinereservationsoftwaremaven.Login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class loginIntegrationTest {
  Login login = new Login();
  private static Connection con;

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


  @Test
  public void testValidLoginDatabaseIntegration() {
    login.setuser("john");
    login.setpass("123");
    login.jButton1.doClick();
  }
}
