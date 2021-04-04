package com.mycompany.airlinereservationsoftwaremaven;

/**
 *This is a testing interface. Do not include in production build.
 * @author drose
 */
public interface SearchCustomerService {
    public void showCustNotFoundMsg();
    
    public void execUpdateCalled();
    
    public String showRegistrationUpdatedMsg(String msg);
}
