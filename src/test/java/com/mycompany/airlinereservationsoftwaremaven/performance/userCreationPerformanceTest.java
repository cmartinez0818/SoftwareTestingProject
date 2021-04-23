package com.mycompany.airlinereservationsoftwaremaven.performance;

import com.mycompany.airlinereservationsoftwaremaven.userCreation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class userCreationPerformanceTest {
  private static userCreation createUser;
  private static Connection con;

  @BeforeAll
  public static void setUpClass() {
    createUser = new userCreation();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/airline", "root", "");
      String query = "delete from User where 1=1;";
      Statement st = con.createStatement();
      st.executeUpdate(query);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  @AfterAll
  public static void tearDownClass() {
    createUser = null;
  }

  /**
   * Test Case ID: testUserCreationPerformance
   * Purpose: Test create user
   * Test setup: Input valid user info, then click add user button
   * Test Strategy: Stub
   * Input: Valid user input
   * Expected state: Passes
   */
  @Test
  public void testUserCreationPerformance() {
    createUser.autoID();
    createUser.isUniqueID();
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
    createUser.isValidPassword();
    JButton addUserBtn2 = createUser.getAddUserButton();
    addUserBtn2.doClick();
  }



}
