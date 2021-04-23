package com.mycompany.airlinereservationsoftwaremaven.integration;

import com.mycompany.airlinereservationsoftwaremaven.userCreation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class userCreationIntegrationTest {
  private static userCreation createUser;
  private static Connection con;


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

  @Test
  public void testUserCreationDatabaseImplementation() {
    createUser = new userCreation();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/airline", "root", "");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

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
