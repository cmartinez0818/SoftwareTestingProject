package com.mycompany.airlinereservationsoftwaremaven.integration;

import com.mycompany.airlinereservationsoftwaremaven.ticket;
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

public class ticketIntegrationTest {
  private static ticket testTicket;
  private static ticketreport testReport;
  private static Connection con;

  @BeforeAll
  public static void setUpClass() {
    testTicket = new ticket();
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
    testTicket = null;
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
    ticket mock = mock(ticket.class);
    when(mock.getFLTID()).thenCallRealMethod();
    doCallRealMethod().when(mock).setFLTID("TO001");
    testTicket.setFLTID("TO001");
    testTicket.getUpdateButton().doClick();
    assertEquals("TO001", testTicket.getFLTID());
  }

}
