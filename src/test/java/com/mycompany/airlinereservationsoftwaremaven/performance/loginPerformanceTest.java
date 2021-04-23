package com.mycompany.airlinereservationsoftwaremaven.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.mycompany.airlinereservationsoftwaremaven.Login;
import org.junit.jupiter.api.Test;


public class loginPerformanceTest {
  Login login = new Login();
  String[] args;

  /**
   * Test Case ID: testLogin
   * Purpose: To test main can open all views
   * Test setup: Setup login
   * Test Strategy: Stub
   * Input: login methods
   * Expected state: Pass
   */
  @Test
  public void testViewPerformanceTest() {
    login.main(args);
  }

  /**
   * Test Case ID: LoginTest1
   * Purpose: Test negative login input
   * Test setup: Set user and password invalid, call login button
   * Test Strategy: Equivalence class testing
   * Input: invalid login
   * Expected state: Passes, returns empty login
   */
  @Test
  public void loginPerformanceFailing() {
    login.setuser("");
    login.setpass("");
    login.jButton1.doClick();

    login.setuser("not");
    login.setpass("real");
    login.jButton1.doClick();

    login.setuser("");
    login.setpass("real");
    login.jButton1.doClick();

    login.setuser("not");
    login.setpass("");
    login.jButton1.doClick();
  }


  /**
   * Test Case ID: LoginTest3
   * Purpose: Test valid login input
   * Test setup: Set user and password invalid, call login button
   * Test Strategy: Stub
   * Input: valid login
   * Expected state: Passes
   */
  @Test
  public void loginPerformancePassing(){
    login.setuser("john");
    login.setpass("123");
    login.jButton1.doClick();
  }

}


