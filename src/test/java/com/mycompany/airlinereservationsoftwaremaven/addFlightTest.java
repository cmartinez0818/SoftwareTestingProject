package com.mycompany.airlinereservationsoftwaremaven;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
  }

  @AfterAll
  public static void tearDownClass() {
    addFlight = null;
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Test Case ID: UTest-validateFlightName
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
    addFlight.setTxtFlightName("JetBlue");
    boolean result = addFlight.validateFlightName();
    assertTrue(result);
  }


  /**
   * Test Case ID: UTest-validateFlightName
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
    addFlight.setTxtFlightName("JetBlu3");
    boolean result = addFlight.validateFlightName();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-isValidPPID-001
   * Requirement: REQ-8 The account’s passport ID input field in the Airline
   * Reservation Software shall accept only alphanumeric and '<' characters.
   * Purpose: To test that the input passed does not contain invalid characters.
   * Test setup: set the addCustomer object passport ID member field using
   * method setPPID(). 1 test case is used as input.
   * Test Strategy: Equivalence class testing.
   *  Partition input space as follows:
   *  - set of passport ID values without any invalid characters
   *  - set of passport ID values with any invalid characters
   * All inputs:
   *  - partition input value: "abz029<<<"
   * Input: call method isValidPPID()
   * Expected output: method isValidPPID() returns true.
   */
  @Test
  public void testIsValidFlightID() {
    addFlight.setFLID("FO001");
    boolean result = addFlight.isValidFlightID();
    assertTrue(result);
  }

  /**
   * Test Case ID: UTest-isValidPPID-001
   * Requirement: REQ-8 The account’s passport ID input field in the Airline
   * Reservation Software shall accept only alphanumeric and '<' characters.
   * Purpose: To test that the input passed does not contain invalid characters.
   * Test setup: set the addCustomer object passport ID member field using
   * method setPPID(). 1 test case is used as input.
   * Test Strategy: Equivalence class testing.
   *  Partition input space as follows:
   *  - set of passport ID values without any invalid characters
   *  - set of passport ID values with any invalid characters
   * All inputs:
   *  - partition input value: "abz029<<<"
   * Input: call method isValidPPID()
   * Expected output: method isValidPPID() returns true.
   */
  @Test
  public void testIsInvalidFlightID() {
    addFlight.setFLID("FO00.");
    boolean result = addFlight.isValidFlightID();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-validateFlightName
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
    addFlight.setTxtArrTime("9.00AM");
    boolean result = addFlight.validateArrTime();
    assertTrue(result);
  }

  /**
   * Test Case ID: UTest-validateFlightName
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
    addFlight.setTxtArrTime("9.00BB");
    boolean result = addFlight.validateArrTime();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-validateFlightName
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
    addFlight.setTxtDepTime("9.00AM");
    boolean result = addFlight.validateDepTime();
    assertTrue(result);
  }

  /**
   * Test Case ID: UTest-validateFlightName
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
    addFlight.setTxtDepTime("9.00BB");
    boolean result = addFlight.validateDepTime();
    assertFalse(result);
  }

}