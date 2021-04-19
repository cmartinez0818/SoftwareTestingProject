package com.mycompany.airlinereservationsoftwaremaven.unit;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.ticket;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mycompany.airlinereservationsoftwaremaven.ticketService;
import com.mycompany.airlinereservationsoftwaremaven.ticketreport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ticketTest {

  private static Main desktop;
  private static ticket ticket;
  private static Connection con;
  static ticketService ticketService;

  public ticketTest() {
  }

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
      Statement st = con.createStatement();
      st.executeUpdate(query);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    try {
      String query4 = "delete from ticket where 1=1";
      Statement st = con.createStatement();
      st.executeUpdate(query4);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    try {
      String query5 = "delete from flight where 1=1";
      Statement st = con.createStatement();
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
   * Test Case ID:
   * Requirement:
   *
   * Purpose:
   *
   * Test setup:
   * Test Strategy:
   * Partitions:
   * -
   * -
   * Input:
   * Expected state:
   *
   */
  @Test
  public void testSearchCustById() {
    ticket.setCID("CS001");
    JButton custBtn = ticket.getSearchCustInfoButton();
    System.out.println(custBtn);
    custBtn.doClick();

    assertEquals("Cary", ticket.getCustFirstName());
    assertEquals("Last", ticket.getCustLastName());
    assertEquals("123456789", ticket.getPsp());
  }

  @Test
  public void testSearchCustByIdNotFound() {
    ticket.setCID("CS008");
    JButton custBtn = ticket.getSearchCustInfoButton();
    System.out.println(custBtn);
    custBtn.doClick();
    String msg = "Record not Found";
    assertEquals(msg, ticket.errMsg);
  }

  /**
   * Test Case ID:
   * Requirement:
   *
   * Purpose:
   *
   * Test setup:
   * Test Strategy:
   * Partitions:
   * -
   * -
   * Input:
   * Expected state:
   *
   */
  @Test
  public void testTicketInfoBySourceDest() {
    ticket.setDestination("India");
    ticket.setSource("India");

    JButton tickBtn = ticket.getTicketSearchButton();
    System.out.println(tickBtn);
    tickBtn.doClick();

  }

  /**
   * Test Case ID:
   * Requirement:
   *
   * Purpose:
   *
   * Test setup:
   * Test Strategy:
   * Partitions:
   * -
   * -
   * Input:
   * Expected state:
   *
   */
  @Test
  public void testBookTicket() {
    ticket.setCID("CS001");
    ticket.setTicketFirstName("Steve");
    ticket.setTicketLastName("Stevenson");
    ticket.setDestination("Uk");
    ticket.setSource("India");
    ticket.autoID();
    ticket.setFLID("FO001");
    ticket.setSeats("1");
    ticket.setPrice("50000");
    JButton bookBtn = ticket.getUpdateButton();
    System.out.println(bookBtn);
    bookBtn.doClick();

    assertEquals("jLabel4", ticket.getTotal());
  }

  @Test
  public void testBookTicketNext() {
    ticket.setCID("CS001");
    ticket.setTicketFirstName("Steve");
    ticket.setTicketLastName("Stevenson");
    ticket.setDestination("Uk");
    ticket.setSource("India");
    ticket.autoID();
    ticket.setFLID("FO002");
    ticket.setSeats("1");
    ticket.setPrice("50000");
    JButton bookBtn = ticket.getUpdateButton();
    System.out.println(bookBtn);
    bookBtn.doClick();

    assertEquals("jLabel4", ticket.getTotal());
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
  public void testIsInvalidSearchCustID() {
    ticket.setCID("CS00#");
    boolean result = ticket.isValidSearchCustID();
    JButton custBtn = ticket.getSearchCustInfoButton();
    System.out.println(custBtn);
    custBtn.doClick();
    String msg = "The entered customer ID is Invalid";
    assertEquals(msg, ticket.errMsg);
    assertFalse(result);
  }
}