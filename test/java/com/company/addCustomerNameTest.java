package com.company;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class addCustomerNameTest {

public boolean validateName(String name) {
  String regex = "[A-z\\-A-z]";
  Pattern p = Pattern.compile(regex);
  Matcher m = p.matcher(name);
  System.out.println(m.matches());
  return m.matches();
}
  @Test
  public void testAddCustomerNameChecker() {
    assertEquals(true, validateName("Carlos"));
  }
}