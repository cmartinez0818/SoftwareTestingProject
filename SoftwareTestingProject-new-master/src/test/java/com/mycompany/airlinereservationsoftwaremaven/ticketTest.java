package com.mycompany.airlinereservationsoftwaremaven;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ticketTest {

    private static ticket ticket;
    private static Connection con;

    public ticketTest() {
    }

    @BeforeAll
    public static void setUpClass() {
      ticket = new ticket();
      try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/mysql","root","");
      }catch(SQLException e){
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    @AfterAll
    public static void tearDownClass() {
      ticket = null;
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    /**
     * Test Case ID: UTest-validateName-001
     * Requirement: REQ-2 The account’s name input fields in the Airline
     * Reservation Software shall accept only alpha characters and the
     * character ‘-‘.
     * Purpose: To test that the input passed is valid input, ensuring that the
     * input only contains alphabetic characters and/or '-'.
     * Test setup: An object of the addCustomer class is created with the text
     * value of member field txtfirstname set to "David-Steven".
     * Test Strategy: Equivalence class testing
     *  Partition input space as follows:
     *  - character classes in input: [a-zA-Z[-]], [\.](any character)
     * All Inputs:
     *  - partition input values: "David-Steven", "David-Steven1", " "
     * Input: call method validateFirstName()
     * Expected output: method validateFirstName() returns true.
     */
    @Test
    public void testValidateNameTrue() {
      ticket.setTicketFirstName("David-Steven");
      boolean result = ticket.validateTicketFirstName();
      assertTrue(result);
    }

    /**
     * Test Case ID: UTest-validateName-002
     * Requirement: REQ-2 The account’s name input fields in the Airline
     * Reservation Software shall accept only alpha characters and the
     * character ‘-‘.
     * Purpose: To test that the input passed is not valid input, ensuring that
     * the input only contains alphabetic characters and/or '-'.
     * Test setup: An object of the addCustomer class is created with the text
     * value of member field txtfirstname set to "David-Steven1".
     * Test Strategy: Equivalence class testing
     *  Partition input space as follows:
     *  - character classes in input: [a-zA-Z[-]], [\.](any character)
     * All Inputs:
     *  - partition input values: "David-Steven", "David-Steven1", " "
     * Input: call method validateFirstName()
     * Expected output: method validateFirstName() returns false.
     */
    @ParameterizedTest
    @ValueSource(strings = {"David-Steven1"," "})
    public void testValidateNameFalse(String s) {
      ticket.setTicketFirstName(s);
      boolean result = ticket.validateTicketFirstName();
      assertFalse(result);
    }

    /**
     * Test Case ID: UTest-validateName-003
     * Requirement: REQ-2 The account’s name input fields in the Airline
     * Reservation Software shall accept any amount of characters in between 1
     * and 64 inclusive.
     * Purpose: To test that the input passed is a valid size, ensuring that
     * the input has a length between 1 and 64 inclusive.
     * Test setup: An object of the addCustomer class is created. 6 tests are
     * passed as parameterized tests.
     * Test Strategy: Equivalence class testing w/ BVA
     *  Partition input space as follows:
     *  - character classes in input: {0}, {1,2,...63,64}, {65,...}
     *  - select the test case at the boundary, and the adjacent case that's in
     *      the positive class. Select the test case at the other boundary.
     *      Select a test case inside the positive input class.
     * All Inputs:
     *  - partition input values: “D”, “DR”,
     “DJvjggOVQYOOwKKJRXJVBYzBPnRLvzEqrlPvidyfeIhEuOjzDaUngbXsgfurKaBo”,
     “JvjggOVQYOO”
     * Input: call method validateFirstName()
     * Expected output: method validateFirstName() returns true.
     */
    @ParameterizedTest
    @ValueSource(strings = {"D","DR", "JvjggOVQYOO","DJvjggOVQYOOwKKJRXJVBYzBPnRLvzEqrlPvidyfeIhEuOjzDaUngbXsgfurKaBo"})
    public void testValidateNameLength(String input) {
      ticket.setTicketFirstName(input);
      boolean result = ticket.validateTicketFirstName();
      assertTrue(result);
    }

    /**
     * Test Case ID: UTest-validateName-004
     * Requirement: REQ-2 The account’s name input fields in the Airline
     * Reservation Software shall accept any amount of characters in between 1
     * and 64 inclusive.
     * Purpose: To test that the input passed is a valid size, ensuring that
     * the input has a length between 1 and 64 inclusive.
     * Test setup: An object of the addCustomer class is created. 2 tests are
     * passed as parameterized tests.
     * Test Strategy: Equivalence class testing w/ BVA
     *  Partition input space as follows:
     *  - character classes in input: {0}, {1,2,...63,64}, {65,...}
     *  - select the test case at the boundary, and the adjacent case that's in
     *      the negative class. Select the test case at the boundary of the
     *      other negative input class.
     * All Inputs:
     *  - partition input values: “”,
     “DJvjggOVQYOOwKKJRXJVBYzBPnRLvzEqrlPvidyfeIhEuOjzDaUngbXsgfurKaBoA”
     * Input: call method validateFirstName()
     * Expected output: method validateFirstName() returns false.
     */
    @ParameterizedTest
    @ValueSource(strings = {"","DJvjggOVQYOOwKKJRXJVBYzBPnRLvzEqrlPvidyfeIhEuOjzDaUngbXsgfurKaBoA"})
    public void testValidateNameLengthNeg(String input) {
      ticket.setTicketFirstName(input);
      boolean result = ticket.validateTicketFirstName();
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
  public void testValidateTicketNameTrue() {
    ticket.setTicketFlightName("JetBlue");
    boolean result = ticket.validateTicketFlightName();
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
  public void testValidateTicketNameFalse() {
    ticket.setTicketFlightName("JetBlu3");
    boolean result = ticket.validateTicketFlightName();
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
  public void testIsValidTicketFlightID() {
    ticket.setFLTID("TO001");
    boolean result = ticket.isValidTicketFlightID();
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
  public void testIsInvalidTicketFlightID() {
    ticket.setFLTID("TO001.");
    boolean result = ticket.isValidTicketFlightID();
    assertFalse(result);
  }
}