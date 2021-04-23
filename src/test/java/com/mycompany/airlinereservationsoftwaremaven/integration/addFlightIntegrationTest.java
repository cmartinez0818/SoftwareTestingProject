package com.mycompany.airlinereservationsoftwaremaven.integration;

import com.mycompany.airlinereservationsoftwaremaven.addflight;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class addFlightIntegrationTest {

  private static addflight addFlight;
  private static Connection con;

  @BeforeAll
  public static void setUpClass() {
    addFlight = new addflight();
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
    }catch(SQLException e){
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      String query = "delete from flight where 1=1";
      Statement st = con.createStatement();
      st.executeUpdate(query);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @AfterAll
  public static void tearDownClass() {
    try {
      String query2 = "delete from flight where 1=1";
      Statement st = con.createStatement();
      st.executeUpdate(query2);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    addFlight = null;
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testValidateAutoID() {
    addFlight.setTxtFlightName("Delta");
    addFlight.setSource("India");
    addFlight.setDestination("Uk");
    addFlight.setTxtDepTime("9.00PM");
    addFlight.setTxtArrTime("11.00PM");
    addFlight.setPrice("8500");
    JButton bookBtn = addFlight.getAddFlightButton();
    System.out.println(bookBtn);
    bookBtn.doClick();
  }
}
