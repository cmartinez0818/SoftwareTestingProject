package com.mycompany.airlinereservationsoftwaremaven.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.SearchCustomerService;
import com.mycompany.airlinereservationsoftwaremaven.searchCustomer;
import com.mycompany.airlinereservationsoftwaremaven.ticket;
import com.mycompany.airlinereservationsoftwaremaven.ticketService;
import com.mycompany.airlinereservationsoftwaremaven.ticketreport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

public class ticketIntegrationTest {
  private static ticket testTicket;
  private static ticketreport testTicketReport;
  private static Connection con;
  static ticketService ticketService;
  static Main desktop;

  @BeforeAll
  public static void setUpClass() {
    String query1 = "INSERT INTO Ticket (ID,flightid,custid,class,price,seats,date)"
            + " VALUES('TO006','FO003','CS001','Economy','9000','1','2019-07-01')";
    String query2 = "INSERT INTO Ticket (ID,flightid,custid,class,price,seats,date)"
            + " VALUES('TO007','FO003','CS002','Economy','9000','1','2019-07-01')";
    String query3 = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
            + " VALUES('CS016','9999999999','Cary','Last','1<3<2','123 there','','Female',987,00000000)";

    //testTicketReport = new ticketreport();
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
      Statement st = con.createStatement();
      st.executeUpdate(query1);
      st.executeUpdate(query2);
      st.executeUpdate(query3);
      st.close();
    }catch(SQLException e){
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @AfterAll
  public static void tearDownClass() throws SQLException {
    //testTicket = null;
    try {
      String query = "delete from Customer where id='CS016'";
      String query2 = "delete from ticket where id='TO006' or id='TO007'";
      Statement st = con.createStatement();
      st.executeUpdate(query);
      st.executeUpdate(query2);
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    con.close();
  }


  @BeforeEach
  public void setUp(){
    //create mocks, which searchCustomer is dependent on.
    ticketService  = Mockito.mock(ticketService.class);
    desktop = new Main();
    testTicket = new ticket(ticketService);
    desktop.getDesktop().add(testTicket);
    testTicket.setVisible(true);
  }

  @AfterEach
  public void tearDown(){
    //create mocks, which searchCustomer is dependent on.
    ticketService  = null;
    desktop.getDesktop().remove(testTicket);
    testTicket = null;
    desktop = null;
  }


  /**
   * Test Case ID: TicketIntegrationTest-001
   * Requirement: REQ-
   *
   *
   * Purpose: To test that inputting the flight ID is handled correctly and that
   * it updates the DataBase, while changing flight ID field.
   * Test setup: create a new mock that represents a scenario in which the employee inputs
   * a valid flight ID.
   * Test Strategy: Mock testing
   * Input: user enters "TO001" in text box. call method updateButtonActionPerformed()
   * Expected state: The update module writes to flight ID field once.
   */
  @Test
  public void testFlightIDStub() {
    testTicket = new ticket();

    ticket mock = mock(ticket.class);
    when(mock.getFLTID()).thenCallRealMethod();
    doCallRealMethod().when(mock).setFLTID("TO001");

    testTicket.setFLTID("TO001");
    testTicket.getUpdateButton().doClick();
  }

  /**
   * Test Case ID:
   * Requirement: REQ-
   *
   * Purpose: To test that the SW correctly finds and outputs the correct, corresponding
   * information without erroneous behavior.
   * Test setup: Input the Customer ID and perform the click operation on the button to
   * pull up the corresponding customer data from the stubbed customer sql table.
   * Test Strategy: Mock behavior testing with complete branch coverage.
   * Input: user enters "CS016" in search box. call method getSearchCustInfoButton()
   * Expected state: The desktop shall not display any pop-up and output the
   * correct customer name that corresponds with the customer ID in the stubbed sql.
   */

  @Test
  public void verifyFindWithMockPos(){
    testTicket.setCID("CS016");
    testTicket.getSearchCustInfoButton().doClick();

    verify(ticketService, times(0)).showCustNotFoundMsg();
    assertEquals("Cary", testTicket.getCustFirstName());
  }

}
