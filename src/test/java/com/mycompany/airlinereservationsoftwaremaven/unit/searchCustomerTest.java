package com.mycompany.airlinereservationsoftwaremaven.unit;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.SearchCustomerService;
import com.mycompany.airlinereservationsoftwaremaven.addCustomer;
import com.mycompany.airlinereservationsoftwaremaven.searchCustomer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
/**
 * searchCustomer unit testing
 * @author drose
 */
public class searchCustomerTest {
    
    private static Main desktop;
    private static searchCustomer searchCust;
    private static Connection con;

    //Set up for all tests in file
    @BeforeAll
    public static void setUpClass(){
        desktop = new Main();
        SearchCustomerService searchFake = new SearchCustomerService() {
            @Override
            public void showCustNotFoundMsg() {}

            @Override
            public void execUpdateCalled() {}

            @Override
            public String showRegistrationUpdatedMsg(String msg) {return "";}
        };
        searchCust = new searchCustomer(searchFake);
        desktop.add(searchCust).setVisible(true);
        String query1 = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
                    + " VALUES('CS005','9876543210','Johnny','Last','1<3<2','123 there','2000-05-01','Male',987,00000000);";
        String query2 = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
                    + " VALUES('CS006','8876543210','Johnny','Last','1<3<2','123 there','2000-05-01','Male',987,00000000);";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
            addCustomer ac = new addCustomer();
            //let's assume 9876543210 and 8876543210 are unique.
            Statement st = con.createStatement();
            st.executeUpdate(query1);
            st.executeUpdate(query2);
            /*ac.setNIC("9876543210");
            if(ac.isUniqueNIC()){                
            }   */         
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }        
    }

    //Delete from customer when finished for future testing
    @AfterAll
    public static void tearDownClass(){
        searchCust = null;
        try {
            String query = "delete from Customer where ID = 'CS005' OR ID = 'CS006'";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Setup before each test
    @BeforeEach
    public void setUp(){
        desktop.getDesktop().add(searchCust);
        searchCust.show();
    }

    //Teardown after each
    @AfterEach
    public void tearDown(){
        desktop.getDesktop().remove(searchCust);
    }
    
    /**
     * Test Case ID: UTest-searchCustomer-001
     * Requirement: REQ-19 The booking agent shall search for a customer account
     * by using the customer account’s ID as search input.
     * Purpose: To test that the SW can find the customer in the DB given the 
     * customer account's ID.
     * Test setup: create a new insert query and remove the test record after
     * testing is done. 
     * Test Strategy: Output equivalence class testing.
     *  Partition output space as follows:
     *  - customer is found
     *  - customer is not found
     * Input: user enters "CS005" in search box. call method
     * findButtonActionPerformed()
     * Expected state: customer NIC no. field in searchCustomer tab is displayed
     * Specifically: 9876543210
     */
    @Test
    public void testFindCustomer() {
        searchCust.setCustIdTxt("CS005");
        searchCust.getFindButton().doClick();
        assertEquals("9876543210", searchCust.getTxtNic());
    }
    
    /**
     * Test Case ID: UTest-validCustomerIDUpdateInput-001
     * Requirement: REQ-28 The booking agent shall update the customer’s account
     * information that has been displayed as a result of the agent’s search
     * query, which is outlined in this statement’s dependencies.
     * Purpose: To test that the SW is able to find an existing customer and 
     * displays all details pertaining to that customer.
     * Test setup: Open a search customer tab virtually in memory. 
     * Test Strategy: Pairwise testing with best fit orthogonal array 
     * Input: enter the following data in each test case (record)
     * specifically:
     * gender   NIC   FN      LN     PPID   Addr   dob   phone#  img
     |  male  |unique|vald  |vald  |vald  |vald  |vald  |vald  |vald|  
     |female  |unique|vald  |vald  |vald  |vald  |vald  |vald  |vald|
     * Expected state: The desktop shall be in the 'Registration updated.'
     * state.
     */
    @ParameterizedTest
    @ValueSource(strings = {"0", "1"})
    public void testValidCustomerIDUpdate(String choice) {
        byte [] blob = {00000000};
        String testNIC = "098745632"+choice;        
        searchCust.fillSearchCustomer("CS006","dave","smith",testNIC,"11<<59","over there", "2000-01-01", choice, "1234567892", blob);
        try{
        searchCust.getUpdateButton().doClick();
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("Registration updated.", searchCust.getUnitUpdateMsg());
    }
    
    /**
     * Test Case ID: UTest-validCustomerIDUpdateInput-002
     * Requirement: REQ-28 The booking agent shall update the customer’s account
     * information that has been displayed as a result of the agent’s search
     * query, which is outlined in this statement’s dependencies.
     * Purpose: To test that the SW is able to find an existing customer and 
     * displays all details pertaining to that customer.
     * Test setup: Open a search customer tab virtually in memory. 
     * Test Strategy: Pairwise testing with best fit orthogonal array 
     * Input: enter the following data in each test case (record)
     * specifically:
     * gender   NIC     FN      LN      PPID    Addr    dob    phone#   img     
        none	unique	vld	vld	vld	vld	vld	vld	vld     
        none	invld	vld	vld	vld	invld	invld	invld	invld   
        none	taken	invld	invld	invld	vld	vld	vld	invld   
        none	unique	invld	invld	invld	invld	invld	invld	vld     
        male	unique	vld	invld	invld	vld	invld	invld	vld     
        male	invld	vld	invld	invld	invld	vld	vld	invld   
        male	taken	invld	vld	vld	vld	invld	invld	invld   
        male	taken	invld	vld	vld	invld	vld	vld	vld     
        female	unique	invld	vld	invld	invld	vld	invld	invld   
        female	invld	invld	vld	invld	vld	invld	vld	vld     
        female	taken	vld	invld	vld	invld	vld	invld	vld     
        female	invld	vld	invld	vld	vld	invld	vld	invld   
        both	unique	invld	invld	vld	invld	invld	vld	invld   
        both	invld	invld	invld	vld	vld	vld	invld	vld     
        both	taken	vld	vld	invld	invld	invld	vld	vld     
        both	invld	vld	vld	invld	vld	vld	invld	invld
     * Expected state: The desktop shall be in the 'Not all fields were updated.'
     * state.
     */
    @ParameterizedTest
    @MethodSource("tc2Provider")
    public void testValidCustomerIDUpdateNeg(String[] args) {
        byte[] blob = {00000000};
        if(args[8].equals("2")){
            blob = new byte[0];
        }
        searchCust.fillSearchCustomer("CS006", args[2],args[3],args[1],args[4],
                args[5], args[6], args[0], args[7], blob);
        searchCust.getUpdateButton().doClick();
        assertEquals("Not all fields were updated.", searchCust.getUnitUpdateMsg());
    }
    
    static Stream<Arguments> tc2Provider() {
        String nic = "6278465824";
        String badnic = "9876543210";
        String inic = "3f4";
        String fn = "john-k";
        String ifn = "jack$";
        String ppid = "1<<2";
        String ippid = "1>g4";
        String addr = "there";
        String iaddr = "";
        DateFormat da = new SimpleDateFormat("yyyy-MM-dd");
        String dob = da.format(new java.util.Date());
        String idob = "5xf16h8248";
        String pn = "1234567890";
        String ipn = "1-800-granola";
        String blob = "1";
        String iblob = "2";
        return Stream.of(
            Arguments.of((Object) new String[]{"-1", nic, fn, fn, ppid, addr, dob, pn, blob}),
            Arguments.of((Object) new String[]{"-1", inic, fn, fn, ppid, iaddr, idob, ipn, iblob}),
            Arguments.of((Object) new String[]{"-1", badnic, ifn, ifn, ippid, addr, dob, pn, iblob}),
            Arguments.of((Object) new String[]{"-1", nic, ifn, ifn, ippid, iaddr, idob, ipn, blob}),
            Arguments.of((Object) new String[]{"0", nic, fn, ifn, ippid, addr, idob, ipn, blob}),
            Arguments.of((Object) new String[]{"0", inic, fn, ifn, ippid, iaddr, dob, pn, iblob}),
            Arguments.of((Object) new String[]{"0", badnic, ifn, fn, ppid, addr, idob, ipn, iblob}),
            Arguments.of((Object) new String[]{"0", badnic, ifn, fn, ppid, iaddr, dob, pn, blob}),
            Arguments.of((Object) new String[]{"1", nic, ifn, fn, ippid, iaddr, dob, ipn, iblob}),
            Arguments.of((Object) new String[]{"1", inic, ifn, fn, ippid, addr, idob, pn, blob}),
            Arguments.of((Object) new String[]{"1", badnic, fn, ifn, ppid, iaddr, dob, ipn, blob}),
            Arguments.of((Object) new String[]{"1", inic, fn, ifn, ppid, addr, idob, pn, iblob}),
            Arguments.of((Object) new String[]{"2", nic, ifn, ifn, ppid, iaddr, idob, pn, iblob}),
            Arguments.of((Object) new String[]{"2", inic, ifn, ifn, ppid, addr, dob, ipn, blob}),
            Arguments.of((Object) new String[]{"2", badnic, fn, fn, ippid, iaddr, idob, pn, blob}),
            Arguments.of((Object) new String[]{"2", inic, fn, fn, ippid, addr, dob, ipn, iblob})
        );
    }

    /**
     * Test Case ID: testSearchCustNeg
     * Purpose: To negative test searching for a customer by ID
     * Test setup: Input hardcoded test values into SQL, then call ticketreport instance.
     * Test Strategy: Input Validation, Bottom-up Integration
     * Input: CS010
     * Expected state: Passes, Posts Customer not Found
     */
    @Test
    public void testSearchCustNeg() {
        searchCust.setCustIdTxt("CS010");
        searchCust.getFindButton().doClick();
    }
}


