package com.mycompany.airlinereservationsoftwaremaven;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.SearchCustomerService;
import com.mycompany.airlinereservationsoftwaremaven.searchCustomer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *searchCustomer integration testing
 * tests: 5
 * 
 * @author drose
 */
public class CustomerIntegrationTest {
    
    static SearchCustomerService service;
    static Main desktop;
    static searchCustomer searchCust;
    static Connection con;
    
    @BeforeAll
    public static void setUpClass(){
        String query1 = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
                    + " VALUES('CS014','4569871230','Johnny','Last','1<3<2','123 there','','Male',987,00000000);";
        String query2 = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
                    + " VALUES('CS015','9999999999','Johnny','Last','1<3<2','123 there','','Male',987,00000000);";
        String query3 = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
                    + " VALUES('CS016','9999999999','Cary','Last','1<3<2','123 there','','Female',987,00000000);";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
            //let's assume 9876543210 and 8876543210 are unique.
            Statement st = con.createStatement();
            st.executeUpdate(query1);
            st.executeUpdate(query2);
            st.executeUpdate(query3);
            st.close();         
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
    }
    
    @AfterAll
    public static void tearDownClass() throws SQLException{
        try {
            String query = "delete from Customer where id='CS014' or id='CS015';";  
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        con.close();
    }
    
    @BeforeEach
    public void setUp(){
        //create mocks, which searchCustomer is dependent on.
        service  = Mockito.mock(SearchCustomerService.class);      
        desktop = new Main();
        searchCust = new searchCustomer(service);        
        desktop.getDesktop().add(searchCust);
        searchCust.setVisible(true);
    }    
    
    @AfterEach
    public void tearDown(){
        //create mocks, which searchCustomer is dependent on.
        service  = null;
        desktop.getDesktop().remove(searchCust);
        searchCust = null;
        desktop = null;
    }
    
    /**
     * Test Case ID: ITest-searchCustomer-001
     * Requirement: REQ-19 The booking agent shall search for a customer account
     * by using the customer account’s ID as search input.
     * Purpose: To test that the SW correctly finds and outputs the correct 
     * information without erroneous behavior
     * Test setup: create a new mock that represents a dialogue informing the
     * customer was not found.
     * Test Strategy: Mock behavior testing with complete branch coverage.
     * Input: user enters "CS015" in search box. call method
     * findButtonActionPerformed()
     * Expected state: The desktop shall not display any pop-up and output the
     * correct NIC information.
     */
    @ParameterizedTest
    @ValueSource(strings={"CS015", "CS016"})
    public void verifyFindWithMockPos(String cid){
        searchCust.setCustIdTxt(cid);
        searchCust.getFindButton().doClick();
        verify(service, times(0)).showCustNotFoundMsg();
        assertEquals("9999999999", searchCust.getTxtNic());
    }
    
    /**
     * Test Case ID: ITest-searchCustomer-002
     * Requirement: REQ-19 The booking agent shall search for a customer account
     * by using the customer account’s ID as search input.
     * Purpose: To test that the SW correctly handles and notifies the user when
     * a customer is not found.
     * Test setup: create a new mock that represents a dialogue informing the
     * customer was not found.
     * Test Strategy: Mock behavior testing. 
     * Input: user enters "CS00x" in search box. call method
     * findButtonActionPerformed()
     * Expected state: The desktop shall display a pop-up only once.
     */
    @Test
    public void verifyFindWithMockNeg(){     
        searchCust.setCustIdTxt("CS00x");
        searchCust.getFindButton().doClick();
        verify(service, times(1)).showCustNotFoundMsg();
    }
    
    /**
     * Test Case ID: ITest-updateCustomer-001
     * Requirement: REQ-28 The booking agent shall update the customer’s account
     * information that has been displayed as a result of the agent’s search
     * query, which is outlined in this statement’s dependencies.
     * Purpose: To test that the SW correctly finds and outputs the correct 
     * information, modifies the output, re-inputs it, and updates the DataBase. 
     * Test setup: create a new mock that represents a dialogue informing the
     * customer was not found.
     * Test Strategy: Mock testing
     * Input: user enters "CS006" in search box. call method
     * findButtonActionPerformed() and updateButtonActionPerformed()
     * Expected state: The update module writes to 8 fields in a record from
     * the database once. The system also notifies the user that 'not all fields
     *      were updated.'
     */
    @Test
    public void verifyFindsAndUpdatesRecord1(){      
        searchCust.setCustIdTxt("CS014");
        searchCust.getFindButton().doClick();
        searchCust.getUpdateButton().doClick();
        //8 because the NIC is not changed, and is in use by the record we got
        verify(service, times(8)).execUpdateCalled();
        String msg = "Not all fields were updated.";        
        when(service.showRegistrationUpdatedMsg(searchCust.updateMsg))
                .thenReturn(searchCust.updateMsg);
        doNothing().when(service).execUpdateCalled();
        assertEquals(msg, searchCust.showRegistrationUpdatedMsg(searchCust.updateMsg));     
    }
    
    /**
     * Test Case ID: ITest-updateCustomer-002
     * Requirement: REQ-28 The booking agent shall update the customer’s account
     * information that has been displayed as a result of the agent’s search
     * query, which is outlined in this statement’s dependencies.
     * Purpose: To test that the SW correctly finds and outputs the correct 
     * information, modifies the output, re-inputs it, and updates the DataBase,
     * while changing NIC field. 
     * Test setup: create a new mock that represents a dialogue informing the
     * customer was not found.
     * Test Strategy: Mock testing
     * Input: user enters "CS006" in search box. call method
     * findButtonActionPerformed() and updateButtonActionPerformed()
     * Expected state: The update module writes to all 9 fields in a record from
     * the database once. The system also notifies the user that 'Registration 
     *      updated.'
     */
    @Test
    public void verifyFindsAndUpdatesRecord2(){
        searchCust.setCustIdTxt("CS014");
        searchCust.getFindButton().doClick();
        //update NIC
        searchCust.setNIC("8888888888");
        searchCust.getUpdateButton().doClick();
        verify(service, times(9)).execUpdateCalled();
        String msg = "Registration updated.";        
        when(service.showRegistrationUpdatedMsg(searchCust.updateMsg))
                .thenReturn(searchCust.updateMsg);
        doNothing().when(service).execUpdateCalled();
        assertEquals(msg, searchCust.showRegistrationUpdatedMsg(searchCust.updateMsg)); 
    }
    
}
