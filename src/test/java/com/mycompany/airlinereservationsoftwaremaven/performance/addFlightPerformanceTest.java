package com.mycompany.airlinereservationsoftwaremaven.performance;


import com.mycompany.airlinereservationsoftwaremaven.addflight;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class addFlightPerformanceTest {
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

  /**
   * Test Case ID: UTest-validateFlightName1
   * Requirement: REQ-2 The account’s name input fields in the Airline
   * Reservation Software shall accept only alpha characters.
   * Purpose: To test that the input passed is valid input, ensuring that the
   * input only contains alphabetic characters.
   * Test setup: An object of the addCustomer class is created with the text
   * value of member field txtfirstname set to "JetBlue".
   * Test Strategy: Equivalence class testing
   *  Partition input space as follows:
   *  - character classes in input: [a-zA-Z], [\.](any character)
   * Input: set flight name to JetBlue
   * Expected output: method validateFlightName() returns true.
   */
  @Test
  public void testAddFlightPerformancePass() {
    addFlight.setTxtFlightName("Delta");
    addFlight.setSource("India");
    addFlight.setDestination("Uk");
    addFlight.setTxtDepTime("9.00PM");
    addFlight.setTxtArrTime("11.00PM");
    addFlight.setPrice("8500");
    JButton bookBtn = addFlight.getAddFlightButton();
    System.out.println(bookBtn);
    bookBtn.doClick();

    addFlight.autoID();
    addFlight.setTxtFlightName("Delta");
    addFlight.setSource("India");
    addFlight.setDestination("Uk");
    addFlight.setTxtDepTime("9.00PM");
    addFlight.setTxtArrTime("11.00PM");
    addFlight.setPrice("8500");
    JButton bookBtn2 = addFlight.getAddFlightButton();
    System.out.println(bookBtn2);
    bookBtn2.doClick();

  }

  /**
   * Test Case ID: UTest-validateFlightName2
   * Requirement: REQ-2 The account’s name input fields in the Airline
   * Reservation Software shall accept only alpha characters.
   * Purpose: To test that the input passed is invalid input.
   * Test setup: Value of member field txtflightname set to "JetBlu3".
   * Other invalid inputs
   * Test Strategy: Equivalence class testing
   * Input: set flight name to JetBlu3, invalid input
   * Expected output: method validation return false
   */
  @Test
  public void testAddFlightPerformanceFail() {
    addFlight.setTxtFlightName("JetBlu3");
    addFlight.setSource("India");
    addFlight.setDestination("Uk");
    addFlight.setTxtDepTime("9.00PM");
    addFlight.setTxtArrTime("11.00PM");
    addFlight.setPrice("8500");
    JButton bookBtn = addFlight.getAddFlightButton();
    System.out.println(bookBtn);
    bookBtn.doClick();

    addFlight.setTxtFlightName("JetBlue");
    addFlight.setSource("India");
    addFlight.setDestination("Uk");
    addFlight.setTxtDepTime("9.00BB");
    addFlight.setTxtArrTime("11.00PM");
    addFlight.setPrice("8500");
    JButton bookBtn2 = addFlight.getAddFlightButton();
    System.out.println(bookBtn2);
    bookBtn2.doClick();

    addFlight.setTxtFlightName("JetBlue");
    addFlight.setSource("India");
    addFlight.setDestination("Uk");
    addFlight.setTxtDepTime("9.00AM");
    addFlight.setTxtArrTime("11.00QQ");
    addFlight.setPrice("8500");
    JButton bookBtn3 = addFlight.getAddFlightButton();
    System.out.println(bookBtn3);
    bookBtn3.doClick();
  }






}
