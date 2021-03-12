package com.mycompany.airlinereservationsoftwaremaven;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class searchCustomerTest {

  private static searchCustomer searchCust;
  private static Connection con;

  public searchCustomerTest() {
  }

  @BeforeAll
  public static void setUpClass() {
    searchCust = new searchCustomer();
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
    searchCust = null;
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
    searchCust.setTxtFirstName("David-Steven");
    boolean result = searchCust.validateFirstName();
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
    searchCust.setTxtFirstName(s);
    boolean result = searchCust.validateFirstName();
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
    searchCust.setTxtFirstName(input);
    boolean result = searchCust.validateFirstName();
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
    searchCust.setTxtFirstName(input);
    boolean result = searchCust.validateFirstName();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-isValidNIC-001
   * Requirement: REQ-6 The account’s NIC no. input field in the Airline
   * Reservation Software shall accept only exactly 10 digit characters.
   * Purpose: To test that the input passed is not the correct length,
   * ensuring that the value is not used.
   * Test setup: set the addCustomer object NIC member field using method
   *  setNIC(). 2 test cases are used as input.
   * Test Strategy: Equivalence class testing with BVA.
   *  Partition input space as follows:
   *  - set of NIC values of length 10
   *  - set of NIC values not of length 10
   * All inputs:
   *  - partition input values at boundaries: 123456789, 12345678900
   * Input: call method isValidNIC()
   * Expected output: method isValidNIC() returns false.
   */
  @ParameterizedTest
  @ValueSource(strings = {"123456789", "12345678900"})
  public void testIsValidNICNeg(String s) {
    searchCust.setNIC(s);
    boolean result = searchCust.isValidNIC();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-isValidNIC-002
   * Requirement: REQ-6 The account’s NIC no. input field in the Airline
   * Reservation Software shall accept only exactly 10 digit characters.
   * Purpose: To test that the input passed contains invalid characters,
   * ensuring that the value is not used.
   * Test setup: set the addCustomer object NIC member field using method
   *  setNIC(). 1 test case is used as input.
   * Test Strategy: Equivalence class testing with BVA.
   *  Partition input space as follows:
   *  - set of NIC values of length 10 with 0 invalid characters
   *  - set of NIC values of length 10 with more than 0 invalid characters
   * All inputs:
   *  - partition input values at boundaries: 12345A6789
   * Input: call method isValidNIC()
   * Expected output: method isValidNIC() returns false.
   */
  @Test
  public void testIsValidNICInvld() {
    searchCust.setNIC("12345A6789");
    boolean result = searchCust.isValidNIC();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-isValidNIC-003
   * Requirement: REQ-6 The account’s NIC no. input field in the Airline
   * Reservation Software shall accept only exactly 10 digit characters.
   * Purpose: To test that the input passed contains valid characters,
   * and that the length is 10 characters long.
   * Test setup: set the addCustomer object NIC member field using method
   *  setNIC(). 1 test case is used as input.
   * Test Strategy: Equivalence class testing.
   *  Partition input space as follows:
   *  - set of NIC values of length 10 with 0 invalid characters
   *  - set of NIC values of length 10 with more than 0 invalid characters
   * All inputs:
   *  - partition input value: 1234567890
   * Input: call method isValidNIC()
   * Expected output: method isValidNIC() returns true.
   */
  @Test
  public void testIsValidNIC() {
    searchCust.setNIC("1234567890");
    boolean result = searchCust.isValidNIC();
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
  public void testIsValidPPID() {
    searchCust.setPPID("1234567890");
    boolean result = searchCust.isValidPPID();
    assertTrue(result);
  }

  /**
   * Test Case ID: UTest-isValidPPID-002
   * Requirement: REQ-8 The account’s passport ID input field in the Airline
   * Reservation Software shall accept only alphanumeric and '<' characters.
   * Purpose: To test that the input passed contains invalid characters.
   * Test setup: set the addCustomer object passport ID member field using
   * method setPPID(). 2 test cases are used as input.
   * Test Strategy: Equivalence class testing.
   *  Partition input space as follows:
   *  - set of passport ID values without any invalid characters
   *  - set of passport ID values with any invalid characters
   * All inputs:
   *  - partition input values: "abz02!9<<<", ""
   * Input: call method isValidPPID()
   * Expected output: method isValidPPID() returns false.
   */
  @ParameterizedTest
  @ValueSource(strings = {"abz!029<<<", ""})
  public void testIsValidPPID(String s) {
    searchCust.setPPID(s);
    boolean result = searchCust.isValidPPID();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-isValidPhoneNo-001
   * Requirement: REQ-15 The account’s phone number input field in the
   * Airline Reservation Software shall accept only numeric characters.
   * Purpose: To test that the input passed does not contain invalid characters.
   * Test setup: set the addCustomer object phone number member field using
   * method setPhoneNo(). 1 test case is used as input.
   * Test Strategy: Equivalence class testing.
   *  Partition input space as follows:
   *  - set of possible phone numbers without any invalid characters
   *  - set of possible phone numbers with any invalid characters
   * All inputs:
   *  - partition input values: "0123456789"
   * Input: call method isValidPhoneNo()
   * Expected output: method isValidPhoneNo() returns true.
   */
  @Test
  public void testIsValidPhoneNo() {
    searchCust.setPhoneNo("0123456789");
    boolean result = searchCust.isValidPhoneNo();
    assertTrue(result);
  }

  /**
   * Test Case ID: UTest-isValidPhoneNo-002
   * Requirement: REQ-15 The account’s phone number input field in the
   * Airline Reservation Software shall accept only numeric characters.
   * Purpose: To test that the input passed contains invalid characters.
   * Test setup: set the addCustomer object phone number member field using
   * method setPhoneNo(). 2 test cases are used as input.
   * Test Strategy: Equivalence class testing.
   *  Partition input space as follows:
   *  - set of possible phone numbers without any invalid characters
   *  - set of possible phone numbers with any invalid characters
   * All inputs:
   *  - partition input values: "012345a789", ""
   * Input: call method isValidPhoneNo()
   * Expected output: method isValidPhoneNo() returns false.
   */
  @ParameterizedTest
  @ValueSource(strings = {"012345a789", ""})
  public void testIsValidPhoneNo(String s) {
    searchCust.setPhoneNo(s);
    boolean result = searchCust.isValidPhoneNo();
    assertFalse(result);
  }


}