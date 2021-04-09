package com.mycompany.airlinereservationsoftwaremaven;

public interface ticketService {
  public void execUpdateCalled();

  public String showRegistrationUpdatedMsg(String msg);

  public void showCustNotFoundMsg();
}