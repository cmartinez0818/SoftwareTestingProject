package com.mycompany.airlinereservationsoftwaremaven.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.mycompany.airlinereservationsoftwaremaven.Login;
import org.junit.jupiter.api.Test;


public class loginPerformanceTest {
  Login login = new Login();
  String[] args;

  @Test
  public void testViewPerformanceTest() {
    login.main(args);
  }

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

  @Test
  public void loginPerformancePassing(){
    login.setuser("john");
    login.setpass("123");
    login.jButton1.doClick();
  }

}


