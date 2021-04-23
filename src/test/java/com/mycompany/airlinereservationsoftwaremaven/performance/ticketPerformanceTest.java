package com.mycompany.airlinereservationsoftwaremaven.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.ticket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ticketPerformanceTest {
  private static Main desktop;
  private static ticket ticket;
  private static Connection con;

  //Insert into customer so tests can retrieve necessary data.
  @BeforeAll
  public static void setUpClass() {

    desktop = new Main();
    ticket = new ticket();
    desktop.add(ticket).setVisible(true);
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
    }catch(SQLException e){
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      String query3 = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
          + " VALUES('CS001','9999999999','Cary','Last','123456789','123 there','','Female',987,00000000)";
      Statement st = con.createStatement();
      st.executeUpdate(query3);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    try {
      String query9 = "INSERT INTO `flight` (`id`, `flightname`, `source`, `depart`, `date`, `deptime`, `arrtime`, `flightcharge`) VALUES\n"
          + "('FO001', 'JetBlue', 'India', 'India', '2019-06-14', '8.00AM', '10.00PM', '50000'),\n"
          + "('FO002', 'Delta', 'India', 'India', '2019-06-15', '8.00PM', '2.00AM', '15000'),\n"
          + "('FO003', 'American Airlines', 'India', 'India', '2019-06-15', '9.00AM', '10.00AM', '9000');";
      Statement st = con.createStatement();
      st.executeUpdate(query9);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @AfterAll
  public static void tearDownClass() {
    try {
      String query = "delete from customer where 1=1";
      String query4 = "delete from ticket where 1=1";
      String query5 = "delete from flight where 1=1";
      Statement st = con.createStatement();
      st.executeUpdate(query);
      st.executeUpdate(query4);
      st.executeUpdate(query5);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    ticket = null;
    desktop = null;
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  public void clearErrMsg(){
    ticket.errMsg = "";
  }

  /**
   * Test Case ID: testBookTicket-001
   * Purpose: Test getUpdateButton adds ticket to mysql
   * Test setup: Manually set all input values to make dummy versions
   * of code dependencies
   * Test Strategy: Stub
   * Input: Fill out ticket with valid inputs
   * Expected state: Pass, Dialog box stating ticket added to database
   *
   */
  @Test
  public void testTicketBookingPerformancePass(){
    ticket.setCID("CS001");
    JButton custBtn = ticket.getSearchCustInfoButton();
    System.out.println(custBtn);
    custBtn.doClick();
    ticket.setDestination("Uk");
    ticket.setSource("India");
    ticket.autoID();
    ticket.setFLID("FO001");
    ticket.setSeats("1");

    ticket.setPrice("50000");
    JButton bookBtn = ticket.getUpdateButton();
    System.out.println(bookBtn);
    bookBtn.doClick();

    ticket.setCID("CS001");
    System.out.println(custBtn);
    custBtn.doClick();
    ticket.setDestination("Uk");
    ticket.setSource("India");
    ticket.autoID();
    ticket.setFLID("FO002");
    ticket.setSeats("1");
    ticket.setPrice("50000");
    JButton bookBtn2 = ticket.getUpdateButton();
    System.out.println(bookBtn2);
    bookBtn2.doClick();
  }

@Test
  public void testTicketFlightLookupPerformance(){
  ticket.setDestination("India");
  ticket.setSource("India");

  JButton tickBtn = ticket.getTicketSearchButton();
  System.out.println(tickBtn);
  tickBtn.doClick();
}

  /**
   * Test Case ID: UTest-isValidSearchCustID
   * Requirement: REQ-8 The account’s passport ID input field in the Airline
   * Reservation Software customer ID shall accept only alphanumeric characters.
   * Purpose: To test that the input passed does not contain invalid characters.
   * Test setup: set the search customer object passport ID member field using
   * method setCID(). 1 test case is used as input.
   * Test Strategy: Equivalence class testing.
   *
   *
   * All inputs:
   *  - partition input value: "CS00#"
   * Input: call method isValidCID()
   * Expected output: method isValidCID() returns false.
   */
@Test
  public void testTicketBookingPerformanceFail(){
  ticket.setCID("CS00#");
  boolean result = ticket.isValidSearchCustID();
  JButton custBtn = ticket.getSearchCustInfoButton();
  System.out.println(custBtn);
  custBtn.doClick();
  String msg = "The entered customer ID is Invalid";
  assertEquals(msg, ticket.errMsg);
  assertFalse(result);

  ticket.setCID("CS008");
  custBtn.doClick();
  String msg2 = "Record not Found";
  assertEquals(msg2, ticket.errMsg);
}


}
