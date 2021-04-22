package com.mycompany.airlinereservationsoftwaremaven.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.addCustomer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;
import javax.swing.JButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class addCustomerPerfromanceTest {
  private static Main desktop;
  private static addCustomer addCust;
  private static Connection con;

  public addCustomerPerfromanceTest() {
  }

  @BeforeAll
  public static void setUpClass() {
    desktop = new Main();
    addCust = new addCustomer();
    desktop.add(addCust).setVisible(true);
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
    try {
      String query = "delete from Customer where 1=1;";
      Statement st = con.createStatement();
      st.executeUpdate(query);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    addCust = null;
    desktop = null;
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  public void emptyCustomerTable(){
    try {
      String query = "delete from Customer where 1=1;";
      Statement st = con.createStatement();
      st.executeUpdate(query);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @AfterEach
  public void clearErrMsg(){
    addCust.errMsg = "";
    addCust.unitMsg = "";
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
    addCust.setTxtFirstName("David-Steven");
    boolean result = addCust.isValidFirstName();
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
    addCust.setTxtFirstName(s);
    boolean result = addCust.isValidFirstName();
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
    addCust.setTxtFirstName(input);
    boolean result = addCust.isValidFirstName();
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
    addCust.setTxtFirstName(input);
    boolean result = addCust.isValidFirstName();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-isUniqueNIC-001
   * Requirement: REQ-5 The Airline Reservation System shall only accept the
   * new customer’s NIC number if the number is not used by any other account
   * NIC number.
   * Purpose: To test that the input passed is a unique value, ensuring that
   * the value is not already assigned to another user in the database.
   * Test setup: set the addCustomer object nic member field using method
   *  setNIC(). 1 test case is used as input.
   * Test Strategy: Output equivalence class testing
   *  Partition output space as follows:
   *  - set of NIC values not already taken
   *  - set of NIC values already taken
   * All outputs:
   *  - partition output values: true, false
   * Input: call method isUniqueNIC()
   * Expected output: method isUniqueNIC() returns true.
   */
  @Test
  public void testIsUniqueNIC() {
    try{
      String setSt = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
          + " VALUES('abc123','9234567890','','','','','','',1,00000000);";
      Statement setQuery = con.createStatement();
      setQuery.executeUpdate(setSt);
      addCust.setNIC("1234567890");
      boolean result = addCust.isUniqueNIC();
      assertTrue(result);
    }catch(SQLException e){
      e.printStackTrace();
    }
  }

  /**
   * Test Case ID: UTest-isUniqueNIC-002
   * Requirement: REQ-5 The Airline Reservation System shall only accept the
   * new customer’s NIC number if the number is not used by any other account
   * NIC number.
   * Purpose: To test that the input NIC passed is already in use by the
   * database, ensuring that the value is not assigned to another user in the
   * database.
   * Test setup: Insert a dummy record with an ID and the NIC input. Set the
   * addCustomer object NIC member field using method setNIC(). 1 test case
   * is used as input. Remove the record with the given ID before assertion.
   * Test Strategy: Output equivalence class testing
   *  Partition output space as follows:
   *  - set of NIC values not already taken
   *  - set of NIC values already taken
   * All outputs:
   *  - partition output values: true, false
   * Input: call method isUniqueNIC()
   * Expected output: method isUniqueNIC() returns false.
   */
  @Test
  public void testIsUniqueNICNeg() {
    try {
      String setSt = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
          + " VALUES('abc123','1234567890','','','','','','',1,00000000);";
      String remSt = "DELETE FROM Customer WHERE ID = 'abc123';";
      Statement setQuery = con.createStatement();
      setQuery.executeUpdate(setSt);
      addCust.setNIC("1234567890");
      boolean result = addCust.isUniqueNIC();
      Statement delQuery = con.createStatement();
      delQuery.executeUpdate(remSt);
      assertFalse(result);
      setQuery.close();
      delQuery.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
    addCust.setNIC(s);
    boolean result = addCust.isValidNIC();
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
    addCust.setNIC("12345A6789");
    boolean result = addCust.isValidNIC();
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
    addCust.setNIC("1234567890");
    boolean result = addCust.isValidNIC();
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
    addCust.setPPID("1234567890");
    boolean result = addCust.isValidPPID();
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
    addCust.setPPID(s);
    boolean result = addCust.isValidPPID();
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
    addCust.setPhoneNo("0123456789");
    boolean result = addCust.isValidPhoneNo();
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
    addCust.setPhoneNo(s);
    boolean result = addCust.isValidPhoneNo();
    assertFalse(result);
  }

  /**
   * Test Case ID: UTest-cancelCustomerCreation-001
   * Requirement: REQ-18 Before a new customer account is made, the booking
   * agent shall cancel the creation of a new account.
   * Purpose: To test that the SW returns to the empty desktop state without
   * a new customer being created from the customer account creation tab.
   * Test setup: Open a customer tab virtually in memory.
   * Test Strategy: Decision table testing.
   *  Rule 1:
   *  - condition 1: the addCustomer tab is open
   *  - action 1: addCustomer tab is closed
   *  - action 2: no new customer is made
   * Input: simulate cancel button click
   * Expected state: The desktop is hiding the addCustomer tab from display.
   */
  @Test
  public void testCancelButtonActionPerformed() {
    desktop.getDesktop().add(addCust);
    addCust.show();
    String cid = addCust.getTxtId();
    addCust.getCancelButton().doClick();
    assertFalse(addCust.isShowing());
    desktop.getDesktop().remove(addCust);
    String query = "select count(*) as ct from Customer where ID=?";
    try(PreparedStatement st = con.prepareStatement(query)){
      st.setString(1, cid);
      ResultSet rs = st.executeQuery();
      rs.next();
      int no = rs.getInt("ct");
      assertEquals(0, no);
    }catch(SQLException se){
      se.printStackTrace();
    }
  }

  /**
   * Test Case ID: UTest-generateNewCustID-001
   * Requirement: REQ-45 The Airline Reservation software shall assign a
   * unique ID to new customers in format CSXXX, where XXX are digit
   * characters.
   * Purpose: To test that a new, unique customer ID is displayed to the user,
   * which will be assigned to the customer upon creation.
   * Test setup: Open the addCustomer tab virtually in memory by adding it to
   * the desktop component. Add 0, 1, 2, and 3 dummy records. A copy of the DB
   * in production is used.
   * Test Strategy: Output equivalence class testing with BVA.
   *  Partitioned output classes:
   *  - class of valid unused customer IDs CS001-CS999.
   *  - class of invalid unused customer IDs CS000, CS1000 and above.
   * Input: call method autoID(), which is called when component added to
   *  desktop
   * Expected state: the addCustomer tab displays a unique, unused customer ID
   */
  @ParameterizedTest
  @ValueSource(strings = {"-1", "001", "997", "998"})
  public void testAssignUniqueCustomerID(String id) {
    if(!id.equals("-1")){
      String query = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
          + " VALUES('CS"+id+"','1234567890','Johnny','','','','','',1,00000000);";
      try {
        Statement st = con.createStatement();
        st.executeUpdate(query);
        st.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
    desktop.getDesktop().add(addCust);
    addCust.autoID();
    addCust.show();
    String cid = addCust.getTxtId();
    if(id.equals("-1")){
      assertEquals("CS001", cid);
    }else{
      int no = Integer.parseInt(id)+1;
      String res = "CS" + String.format("%03d", no);
      assertEquals(res, cid);
    }
    addCust.hide();
    desktop.getDesktop().remove(addCust);
  }

  /**
   * Test Case ID: UTest-invalidCustomerCreationInput-001
   * Requirement: REQ-49 Upon any invalid input, the Airline Reservation
   * software shall display where in the GUI invalid input occurred.
   * Purpose: To test that the SW does not create a new customer account using
   * negative input provided by the booking agent using error handling.
   * Test setup: Open an add customer tab virtually in memory.
   * Test Strategy: Output equivalence class testing
   * Partitions:
   * - Set of positive inputs that allow customer creation
   * - Set of invalid inputs that do not allow customer creation
   * Input: enter input for Nic no. Then simulate add button click.
   * Expected state: The desktop is displaying "The entered NIC is already in
   * use by an existing customer".
   */
  @Test
  public void testNegCustomerCreationInput() {
    try {
      String setSt = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
          + " VALUES('abc123','1234567890','','','','','','',1,00000000);";
      String remSt = "DELETE FROM Customer WHERE ID = 'abc123';";
      Statement setQuery = con.createStatement();
      setQuery.executeUpdate(setSt);
      addCust.setNIC("1234567890");
      JButton addBtn = addCust.getAddButton();
      addBtn.doClick();
      Statement delQuery = con.createStatement();
      delQuery.executeUpdate(remSt);
      String msg = "The entered NIC is already in use by an existing customer";
      assertEquals(msg, addCust.errMsg);
      setQuery.close();
      delQuery.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Test Case ID: UTest-invalidCustomerCreationInput-002
   * Requirement: REQ-49 Upon any invalid input, the Airline Reservation
   * software shall display where in the GUI invalid input occurred.
   * Purpose: To test that the SW does not create a new customer account using
   * invalid input provided by the booking agent using error handling.
   * Test setup: Open an add customer tab virtually in memory.
   * Test Strategy: Output equivalence class testing
   * Partitions:
   * - Set of positive inputs that allow customer creation
   * - Set of invalid inputs that do not allow customer creation
   * Input: enter input for Nic no. Then simulate add button click.
   * Expected state: The desktop is displaying "Invalid NIC input. Enter
   * exactly 10 digits only."
   */
  @Test
  public void testInvalidCustomerCreationInput() {
    addCust.setNIC("15ht6");
    JButton addBtn = addCust.getAddButton();
    System.out.println(addBtn);
    addBtn.doClick();
    String msg = "Invalid NIC input. Enter exactly 10 digits only.";
    assertEquals(msg, addCust.errMsg);
  }

  /**
   * Test Case ID: UTest-selectGenderTest-001
   * Requirement: REQ-12 The account’s gender input field in the Airline
   * Reservation Software shall accept one of only two options: ‘male’ and
   * ‘female’.
   * Purpose: To test that the user cannot select both male and female options
   * simultaneously.
   * Test setup: Open an add customer tab virtually in memory.
   * Test Strategy: Equivalence class testing
   * Partitions:
   * 1. select Male when none selected
   * 2. select Female when none selected
   * 3. select Male when Female selected
   * 4. select Female when Male selected
   * Input: either Male or Female are selected, or both unselected
   * Expected state: 1,3: only male is selected
   * 2,4: only female is selected
   */
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4})
  public void testMaleFemaleInput(int c) {
    switch(c){
      //select male, none selected
      case 1:
        addCust.getMaleR().doClick();
        assertTrue(addCust.getMaleR().isSelected());
        assertFalse(addCust.getFemaleR().isSelected());
        break;
      //select female, none selected
      case 2:
        addCust.getFemaleR().doClick();
        assertTrue(addCust.getFemaleR().isSelected());
        assertFalse(addCust.getMaleR().isSelected());
        break;
      //select male, female selected
      case 3:
        addCust.getFemaleR().doClick();
        addCust.getMaleR().doClick();
        assertTrue(addCust.getMaleR().isSelected());
        assertFalse(addCust.getFemaleR().isSelected());
        break;
      //select female, male selected
      case 4:
        addCust.getMaleR().doClick();
        addCust.getFemaleR().doClick();
        assertTrue(addCust.getFemaleR().isSelected());
        assertFalse(addCust.getMaleR().isSelected());
        break;
    }
  }

  /**
   * Test Case ID: UTest-validCustomerAddInput-001
   * Requirement: REQ-17 The booking agent shall create a new customer account
   * using the customer’s information outlined in this requirement’s
   * dependencies.
   * Purpose: To test that the SW is able to add a new customer when correct
   * input added.
   * Test setup: Open an add customer tab virtually in memory.
   * Test Strategy: Pairwise testing with best fit orthogonal array
   * Input: enter the following data in each test case (record)
   * specifically:
   * gender   NIC   FN      LN     PPID   Addr   dob   phone#  img
   |  male  |unique|vald  |vald  |vald  |vald  |vald  |vald  |vald|
   |female  |unique|vald  |vald  |vald  |vald  |vald  |vald  |vald|
   * Expected state: The desktop shall be in the 'New customer added'
   * state.
   */
  @ParameterizedTest
  @ValueSource(strings = {"0", "1"})
  public void testValidNewCustomer(String choice) {
    try{
      byte [] blob = {00000000};
      String testNIC = "045685632"+choice;
      addCust.fillAddCustomer("dave","smith",testNIC,"11<<59","over there", "2000-01-01", choice, "1234567892", blob);

      addCust.getAddButton().doClick();
    }catch(Exception e){
      e.printStackTrace();
    }
    assertEquals("New customer added", addCust.unitMsg);
  }

  /**
   * Test Case ID: UTest-validCustomerAddInput-002
   * Requirement: REQ-17 The booking agent shall create a new customer account
   * using the customer’s information outlined in this requirement’s
   * dependencies.
   * Purpose: To test that the SW does not add a new customer when incorrect
   * input added.
   * Test setup: Open an add customer tab virtually in memory.
   * Test Strategy: Pairwise testing with best fit orthogonal array
   * Input: enter the following data in each test case (record)
   * specifically:
   * gender   NIC     FN      LN      PPID    Addr    dob    phone#   img
   none	unique	vld	vld	vld	vld	vld	vld	vld
   none	invld	vld	vld	vld	invld	invld	invld	invld
   none	taken	invld	invld	invld	vld	vld	vld	invld
   none	unique	invld	invld	invld	invld	invld	invld	vld
   male	unique	vld	invld	invld	vld	invld	invld	vld
   male	invld	vld	invld	invld	invld	vld	vld	invld
   male	taken	invld	vld	vld	vld	invld	invld	invld
   male	taken	invld	vld	vld	invld	vld	vld	vld
   female	unique	invld	vld	invld	invld	vld	invld	invld
   female	invld	invld	vld	invld	vld	invld	vld	vld
   female	taken	vld	invld	vld	invld	vld	invld	vld
   female	invld	vld	invld	vld	vld	invld	vld	invld
   both	unique	invld	invld	vld	invld	invld	vld	invld
   both	invld	invld	invld	vld	vld	vld	invld	vld
   both	taken	vld	vld	invld	invld	invld	vld	vld
   both	invld	vld	vld	invld	vld	vld	invld	invld
   * Expected state: The desktop shall be in the 'No new customer made'
   * state.
   */
  @ParameterizedTest
  @MethodSource("tc2Provider")
  public void testValidNewCustomerNeg(String[] args) {
    byte[] blob = {00000000};
    if(args[8].equals("2")){
      blob = new byte[0];
    }
    addCust.fillAddCustomer(args[2],args[3],args[1],args[4],
        args[5], args[6], args[0], args[7], blob);
    addCust.getAddButton().doClick();
    assertEquals("No new customer made", addCust.unitMsg);
  }

  static Stream<Arguments> tc2Provider() {
    String nic = "6278465824";
    String badnic = "9876543210";
    String inic = "3f4";
    String fn = "john-k";
    String ifn = "jack$";
    String ppid = "1<<2";
    String ippid = "1>g4";
    String addr = "there";
    String iaddr = "";
    DateFormat da = new SimpleDateFormat("yyyy-MM-dd");
    String dob = da.format(new java.util.Date());
    String idob = "5xf16h8248";
    String pn = "1234567890";
    String ipn = "1-800-granola";
    String blob = "1";
    String iblob = "2";
    return Stream.of(
        Arguments.of((Object) new String[]{"-1", nic, fn, fn, ppid, addr, dob, pn, blob}),
        Arguments.of((Object) new String[]{"-1", inic, fn, fn, ppid, iaddr, idob, ipn, iblob}),
        Arguments.of((Object) new String[]{"-1", badnic, ifn, ifn, ippid, addr, dob, pn, iblob}),
        Arguments.of((Object) new String[]{"-1", nic, ifn, ifn, ippid, iaddr, idob, ipn, blob}),
        Arguments.of((Object) new String[]{"0", nic, fn, ifn, ippid, addr, idob, ipn, blob}),
        Arguments.of((Object) new String[]{"0", inic, fn, ifn, ippid, iaddr, dob, pn, iblob}),
        Arguments.of((Object) new String[]{"0", badnic, ifn, fn, ppid, addr, idob, ipn, iblob}),
        Arguments.of((Object) new String[]{"0", badnic, ifn, fn, ppid, iaddr, dob, pn, blob}),
        Arguments.of((Object) new String[]{"1", nic, ifn, fn, ippid, iaddr, dob, ipn, iblob}),
        Arguments.of((Object) new String[]{"1", inic, ifn, fn, ippid, addr, idob, pn, blob}),
        Arguments.of((Object) new String[]{"1", badnic, fn, ifn, ppid, iaddr, dob, ipn, blob}),
        Arguments.of((Object) new String[]{"1", inic, fn, ifn, ppid, addr, idob, pn, iblob}),
        Arguments.of((Object) new String[]{"2", nic, ifn, ifn, ppid, iaddr, idob, pn, iblob}),
        Arguments.of((Object) new String[]{"2", inic, ifn, ifn, ppid, addr, dob, ipn, blob}),
        Arguments.of((Object) new String[]{"2", badnic, fn, fn, ippid, iaddr, idob, pn, blob}),
        Arguments.of((Object) new String[]{"2", inic, fn, fn, ippid, addr, dob, ipn, iblob})
    );
  }
}
