package com.mycompany.airlinereservationsoftwaremaven.unit;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import org.junit.jupiter.api.Test;

public class mainTest {
  Main main = new Main();
  String[] args;

  /**
   * Test Case ID: testMainMenu
   * Purpose: To test main can open all views
   * Test setup: Setup main, setup each view
   * Test Strategy: Stub
   * Input: main and doClick methods
   * Expected state: Pass
   */
  @Test
  public void testMainMenuItemsPerformance() {

    main.main(args);
    main.getDesktop();
    main.getCustMenu();

    main.getjMenuItem2().doClick();
    main.getjMenuItem3().doClick();
    main.getjMenuItem4().doClick();
    main.getjMenuItem5().doClick();
    main.getjMenuItem6().doClick();

  }
}
