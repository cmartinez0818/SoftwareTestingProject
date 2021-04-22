package com.mycompany.airlinereservationsoftwaremaven.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import java.lang.reflect.Array;
import org.junit.jupiter.api.Test;

public class mainPerformanceTest {
  Main main = new Main();
  String[] args;
  @Test
  public void testMainMenuItemsPerformance() {

    main.main(args);
    main.getDesktop();

    main.getjMenuItem2().doClick();
    main.getjMenuItem3().doClick();
    main.getjMenuItem4().doClick();
    main.getjMenuItem5().doClick();
    main.getjMenuItem6().doClick();

  }
}
