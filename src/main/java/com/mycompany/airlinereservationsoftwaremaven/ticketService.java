package com.mycompany.airlinereservationsoftwaremaven;

public interface ticketService {
  //  call update for tickets in test
  public void execUpdateCalled();
// show message 
  public String showRegistrationUpdatedMsg(String msg);
// show not found
  public void showCustNotFoundMsg();
}
