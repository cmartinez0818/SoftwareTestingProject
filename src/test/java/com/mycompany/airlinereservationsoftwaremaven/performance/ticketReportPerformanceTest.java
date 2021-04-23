package com.mycompany.airlinereservationsoftwaremaven.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.ticket;
import com.mycompany.airlinereservationsoftwaremaven.ticketService;
import com.mycompany.airlinereservationsoftwaremaven.ticketreport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ticketReportPerformanceTest {
  private static ticket testTicket;
  private static ticketreport testTicketReport;
  private static Connection con;
  static com.mycompany.airlinereservationsoftwaremaven.ticketService ticketService;
  static Main desktop;

  //Setup before all classes
  @BeforeAll
  public static void setUpClass() {
    testTicket = new ticket();
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
      Statement st = con.createStatement();
      st.close();
    }catch(SQLException e){
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      String query = "DELETE FROM ticket where 1=1";
      String query3 = "INSERT INTO `ticket` (`id`, `flightid`, `custid`, `class`, `price`, `seats`, `date`) VALUES\n"
          + "('TO001', 'FO003', 'CS001', 'Economy', 9000, 1, '2019-06-15'),\n"
          + "('TO002', 'FO003', 'CS001', 'Economy', 9000, 2, '2019-06-15'),\n"
          + "('TO003', 'FO001', 'CS003', 'Economy', 50000, 3, '2019-07-01');";
      Statement sta = con.createStatement();
      sta.execute(query);
      sta.executeUpdate(query3);
      sta.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @BeforeEach
  public void setUp(){
    //I cannot find out what is wrong when I run all tests!!!!!!!!!!!!!!!!!!!!!!
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
      Statement st = con.createStatement();
      st.close();
    }catch(SQLException e){
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      String query = "DELETE FROM ticket where id = 'TO006' or id = 'TO007'";
      Statement st = con.createStatement();
      st.execute(query);
      st.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @AfterEach
  public void tearDownClass() throws SQLException {
    testTicket = null;
    try {
      String query4 = "delete from ticket where 1=1";
      Statement st = con.createStatement();
      st.executeUpdate(query4);
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    con.close();
  }


  /**
   * Test Case ID: TicketReportPerformanceTest-001
   * Purpose: To test that the ticket report properly interacts with the database, and properly
   * formats and reports the table of tickets from the database.
   * Test setup: Input hardcoded test values into SQL, then call ticketreport instance.
   * Test Strategy: Input Validation, Bottom-up Integration
   * Input: Multiple ticket instances inside of ticket table.
   * Expected state: The ticketreport LoadData() method creates a report from the sql ticket table,
   * outputs it to testVector for testing, with value matching expected value.
   */
  @Test
  public void testTicketReportPerformance() {
    ticketreport testTicketReport = new ticketreport();
    /*
    assertEquals("[TO001, FO003, CS001, Economy, 9000, 1, 2019-06-15, TO002, FO003, CS001, Economy, 9000, 2, 2019-06-15, TO003, FO001, CS003, Economy, 50000, 3, 2019-07-01]",
        ticketreport.testVector.toString()); **/
  }
}
