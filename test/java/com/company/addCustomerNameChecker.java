package com.company;

import java.util.regex.*;

class addCustomerNameChecker {

  public boolean validateName(String name) {
      String regex = "[A-z\\-A-z]";
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(name);
      return m.matches();
  }
}