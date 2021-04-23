package com.mycompany.airlinereservationsoftwaremaven.unit;

import com.mycompany.airlinereservationsoftwaremaven.addflight;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class addFlightTest {

  private static addflight addFlight;
  private static Connection con;

  public addFlightTest() {
  }

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

  /**
   * Test Case ID: UTest-validateFlightName2
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
  public void testValidateNextEntries() {
    addFlight.autoID();
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

  /**
   * Test Case ID: UTest-validateFlightName3
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
  public void testValidateNameTrue() {
    addFlight.setTxtArrTime("9.00AM");
    addFlight.setTxtFlightName("JetBlue");
    addFlight.setTxtDepTime("9.00AM");
    boolean result = addFlight.validateFlightName();
    assertTrue(result);
  }


  /**
   * Test Case ID: UTest-validateFlightName4
   * Requirement: REQ-2 The account’s name input fields in the Airline
   * Reservation Software shall accept only alpha characters.
   * Purpose: To test that the invalid input passed is returned as invalid
   * Test setup: An object of the addCustomer class is created with the text
   * value of member field txtfirstname set to "JetBlue".
   * Test Strategy: Negative testing
   *  Partition input space as follows:
   *  - character classes in input: [a-zA-Z[-]], [\.](any character)
   * All Inputs:
   *  - partition input values: "David-Steven", "David-Steven1", " "
   * Input: call method validateFirstName()
   * Expected output: method validateFirstName() returns true.
   */
  @Test
  public void testValidateNameFalse() {
    addFlight.setTxtArrTime("9.00AM");
    addFlight.setTxtFlightName("JetBlu3");
    addFlight.setTxtDepTime("9.00AM");
    boolean result = addFlight.validateFlightName();
    JButton bookBtn = addFlight.getAddFlightButton();
    System.out.println(bookBtn);
    bookBtn.doClick();
    String msg = "The entered flight name is invalid";
    assertEquals(msg, addFlight.errMsg);
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-validateFlightName5
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
  public void testValidateArrTimeTrue() {
    addFlight.setTxtFlightName("JetBlue");
    addFlight.setTxtArrTime("9.00AM");
    addFlight.setTxtDepTime("9.00AM");
    boolean result = addFlight.validateArrTime();
    assertTrue(result);
  }

  /**
   * Test Case ID: UTest-validateFlightName6
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
  public void testValidateArrTimeFalse() {
    addFlight.setTxtFlightName("JetBlue");
    addFlight.setTxtArrTime("9.00BB");
    addFlight.setTxtDepTime("9.00AM");
    boolean result = addFlight.validateArrTime();
    JButton bookBtn = addFlight.getAddFlightButton();
    System.out.println(bookBtn);
    bookBtn.doClick();
    String msg = "The entered arrival time is invalid";
    assertEquals(msg, addFlight.errMsg);
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-validateFlightName7
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
  public void testValidateDepTimeTrue() {
    addFlight.setTxtFlightName("JetBlue");
    addFlight.setTxtArrTime("9.00AM");
    addFlight.setTxtDepTime("9.00AM");
    boolean result = addFlight.validateDepTime();
    assertTrue(result);
  }

  /**
   * Test Case ID: UTest-validateFlightName8
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
  public void testValidateDepTimeFalse() {
    addFlight.setTxtFlightName("JetBlue");
    addFlight.setTxtArrTime("9.00AM");
    addFlight.setTxtDepTime("9.00BB");
    boolean result = addFlight.validateDepTime();
    JButton bookBtn = addFlight.getAddFlightButton();
    System.out.println(bookBtn);
    bookBtn.doClick();
    String msg = "The entered departure time is invalid";
    assertEquals(msg, addFlight.errMsg);
    assertFalse(result);
  }

}