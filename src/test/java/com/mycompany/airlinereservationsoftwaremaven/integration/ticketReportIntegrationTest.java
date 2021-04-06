package com.mycompany.airlinereservationsoftwaremaven.integration;

import com.mycompany.airlinereservationsoftwaremaven.ticketreport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ticketReportIntegrationTest {
  private static ticketreport testReport;
  private static Connection con;

  @BeforeAll
  public static void setUpClass() {
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
    testReport = null;
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Test Case ID: TicketReportIntegrationTest-001
   * Requirement: REQ-
   *
   *
   * Purpose: To test that the ticket report properly interacts with the database, and formats
   * and reports the table of tickets from the database.
   * Test setup: create a new mock that represents a scenario in which the employee inputs
   * a valid flight ID.
   * Test Strategy:
   * Input: Multiple ticket instances inside of ticket table.
   * Expected state: The ticket report LoadData() method creates a report from the sql ticket table.
   */
  @Test
  public void autoIdTest() {
    ticketreport testReport = new ticketreport();
    testReport.LoadData();
   assertEquals("[TO001, FO003, CS001, Economy, 9000, 1, 2019-06-15, TO002, FO003, CS001, "
       + "Economy, 9000, 2, 2019-06-15, TO003, FO001, CS003, Economy, 50000, 3, 2019-07-01, TO004, "
       + "FO001, CS001, Economy, 50000, 1, 2021-04-03, TO005, FO001, CS001, Economy, 50000, 1, "
       + "2021-04-05, TO001, FO003, CS001, Economy, 9000, 1, 2019-06-15, TO002, FO003, CS001, "
       + "Economy, 9000, 2, 2019-06-15, TO003, FO001, CS003, Economy, 50000, 3, 2019-07-01, TO004, "
       + "FO001, CS001, Economy, 50000, 1, 2021-04-03, TO005, FO001, CS001, Economy, 50000, 1, "
       + "2021-04-05]", testReport.testVector.toString());
  }

}
