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

public class ticketReportIntegrationTest {
  private static ticket testTicket;
  private static ticketreport testTicketReport;
  private static Connection con;
  static ticketService ticketService;
  static Main desktop;

  @BeforeAll
  public static void setUpClass() {
    String query3 = "INSERT INTO Ticket (ID,flightid,custid,class,price,seats,date)"
            + " VALUES('TO006','FO003','CS001','Economy','9000','1','2019-07-01')";
    String query2 = "INSERT INTO Ticket (ID,flightid,custid,class,price,seats,date)"
            + " VALUES('TO007','FO003','CS002','Economy','9000','1','2019-07-01')";
    //String query1 = "delete from ticket where id= 'TO006' or 'TO007'";
    //testTicketReport = new ticketreport();
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
      Statement st = con.createStatement();
      st.executeUpdate(query3);
      st.executeUpdate(query2);
      //st.executeUpdate(query1);
      st.close();
    }catch(SQLException e){
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @AfterAll
  public static void tearDownClass() throws SQLException {
    testTicket = null;
    try {
      String query3 = "delete from ticket where id='TO006' or id='TO007'";
      Statement st = con.createStatement();
      st.executeUpdate(query3);
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
   * Test Case ID: TicketReportIntegrationTest-001
   * Requirement: REQ-
   *
   * Purpose: To test that the ticket report properly interacts with the database, and properly
   * formats and reports the table of tickets from the database.
   * Test setup: Input hardcoded test values into SQL, then call ticketreport instance.
   * Test Strategy: Input Validation, Bottom-up Integration
   * Input: Multiple ticket instances inside of ticket table.
   * Expected state: The ticketreport LoadData() method creates a report from the sql ticket table,
   * outputs it to testVector for testing, with value matching expected value.
   */
  @Test
  public void reportTest() {
    ticketreport testTicketReport = new ticketreport();

    assertEquals("[TO001, FO003, CS001, Economy, 9000, 1, 2019-06-15, TO002, FO003, "
                    + "CS001, Economy, 9000, 2, 2019-06-15, TO003, FO001, CS003, Economy, 50000, 3, "
                    + "2019-07-01, TO006, FO003, CS001, Economy, 9000, 1, "
                    + "2019-07-01, TO007, FO003, CS002, Economy, 9000, 1, 2019-07-01]",
            testTicketReport.testVector.toString());
  }
}
