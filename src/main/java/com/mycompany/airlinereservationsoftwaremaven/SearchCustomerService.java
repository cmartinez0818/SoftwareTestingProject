package com.mycompany.airlinereservationsoftwaremaven;

/**
 *This is a testing interface. Do not include in production build.
 * @author drose
 */
public interface SearchCustomerService {
    //call for not found message
    public void showCustNotFoundMsg();
    // call for updating search
    public void execUpdateCalled();
    // call to show message once updated
    public String showRegistrationUpdatedMsg(String msg);
}
