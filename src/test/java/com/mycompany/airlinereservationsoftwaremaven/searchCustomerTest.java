package com.mycompany.airlinereservationsoftwaremaven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
/**
 *
 * @author drose
 */
public class searchCustomerTest {
    
    private static Main desktop;
    private static searchCustomer searchCust;
    private static Connection con;
    private static String testNIC;
    
    @BeforeAll
    public static void setUpClass(){
        desktop = new Main();
        searchCust = new searchCustomer();
        testNIC = "9876543210";
        String query = "INSERT INTO Customer (ID,nic,firstname,lastname,passport,address,dob,gender,contact,photo)"
                    + " VALUES('CS005','9876543210','Johnny','Last','1<3<2','123 there','','Male',987,00000000);";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/airline","root","");
            addCustomer ac = new addCustomer();
            ac.setNIC("9876543210");
            if(ac.isUniqueNIC()){
                Statement st = con.createStatement();
                st.executeUpdate(query);
            }            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }        
    }
    
    @AfterAll
    public static void tearDownClass(){
        searchCust = null;
        try {
            String query = "delete from Customer where ID = 'CS005'";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @BeforeEach
    public void setUp(){
        desktop.getDesktop().add(searchCust);
        searchCust.show();
    }
    
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
    public void testFindButtonActionPerformed() {
        searchCust.setCustIdTxt("CS005");
        searchCust.getFindButton().doClick();
        assertEquals(testNIC, searchCust.getTxtNic());
    }
    
    /**
     * Test Case ID: UTest-validCustomerIDSearchInput-001
     * Requirement: REQ-49 The booking agent shall update the customer’s account
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
     * Expected state: The desktop shall be in the 'Registrated user updated.'
     * state.
     */
    @ParameterizedTest
    @ValueSource(strings = {"0", "1"})
    public void testValidCustomerIDSearch(String choice) {
        byte [] blob = {00000000};
        testNIC = "098745632"+choice;
        searchCust.fillSearchCustomer("CS005","dave","smith",testNIC,"11<<59","over there", choice, "1234567892", blob);
        searchCust.getUpdateButton().doClick();
        assertEquals("Registation Updated.", searchCust.updateMsg);
    }
    
    /**
     * Test Case ID: UTest-validCustomerIDSearchInput-002
     * Requirement: REQ-49 The booking agent shall update the customer’s account
     * information that has been displayed as a result of the agent’s search
     * query, which is outlined in this statement’s dependencies.
     * Purpose: To test that the SW is able to find an existing customer and 
     * displays all details pertaining to that customer.
     * Test setup: Open a search customer tab virtually in memory. 
     * Test Strategy: Pairwise testing with best fit orthogonal array 
     * Input: enter the following data in each test case (record)
     * specifically:
     * gender   NIC     FN      LN      PPID    Addr    dob    phone#   img     expected output
        none	unique	vld	vld	vld	vld	vld	vld	vld     fail
        none	invld	vld	vld	vld	invld	invld	invld	invld   fail
        none	taken	invld	invld	invld	vld	vld	vld	invld   fail
        none	unique	invld	invld	invld	invld	invld	invld	vld     fail
        male	unique	vld	invld	invld	vld	invld	invld	vld     fail
        male	invld	vld	invld	invld	invld	vld	vld	invld   fail
        male	taken	invld	vld	vld	vld	invld	invld	invld   fail
        male	taken	invld	vld	vld	invld	vld	vld	vld     fail
        female	unique	invld	vld	invld	invld	vld	invld	invld   fail
        female	invld	invld	vld	invld	vld	invld	vld	vld     fail
        female	taken	vld	invld	vld	invld	vld	invld	vld     fail
        female	invld	vld	invld	vld	vld	invld	vld	invld   fail
        both	unique	invld	invld	vld	invld	invld	vld	invld   fail
        both	invld	invld	invld	vld	vld	vld	invld	vld     fail
        both	taken	vld	vld	invld	invld	invld	vld	vld     fail
        both	invld	vld	vld	invld	vld	vld	invld	invld   fail
     * 'fail' means not all update fields were updated because
     * one or more fields were invalid, incorrect or empty.
     * Expected state: The desktop shall be in the 'Not all fields were updated'
     * state.
     * 100% statement + branch
     */
    //@ParameterizedTest
    //@MethodSource("tc2Provider")
    public void testValidCustomerIDSearchNeg(Object[] args) {
        searchCust.fillSearchCustomer("CS005", (String)args[2],(String)args[2],
                (String)args[1],(String)args[4],(String)args[5],
                (String)args[0], (String)args[7], (byte[])args[0]);
        searchCust.getUpdateButton().doClick();
        assertEquals("Not all fields were updated", searchCust.updateMsg);
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
        String idob = "";
        String pn = "1234567890";
        String ipn = "1-800-granola";
        byte[] blob = {00000000};
        byte[] iblob = null;
        return Stream.of(
            Arguments.of( new Object[]{"-1", nic, fn, fn, ppid, addr, dob, pn, (Object)blob}),
            Arguments.of( new Object[]{"-1", inic, fn, fn, ppid, iaddr, idob, ipn, (Object)iblob}),
            Arguments.of( new Object[]{"-1", badnic, ifn, ifn, ippid, addr, dob, pn, (Object)iblob}),
            Arguments.of( new Object[]{"-1", nic, ifn, ifn, ippid, iaddr, idob, ipn, (Object)blob}),
            Arguments.of( new Object[]{"0", nic, fn, ifn, ippid, addr, idob, ipn, (Object)blob}),
            Arguments.of( new Object[]{"0", inic, fn, ifn, ippid, iaddr, dob, pn, (Object)iblob}),
            Arguments.of( new Object[]{"0", badnic, ifn, fn, ppid, addr, idob, ipn, (Object)iblob}),
            Arguments.of( new Object[]{"0", badnic, ifn, fn, ppid, iaddr, dob, pn, (Object)blob}),
            Arguments.of( new Object[]{"1", nic, ifn, fn, ippid, iaddr, dob, ipn, (Object)iblob}),
            Arguments.of( new Object[]{"1", inic, ifn, fn, ippid, addr, idob, pn, (Object)blob}),
            Arguments.of( new Object[]{"1", badnic, fn, ifn, ppid, iaddr, dob, ipn, (Object)blob}),
            Arguments.of( new Object[]{"1", inic, fn, ifn, ppid, addr, idob, pn, (Object)iblob}),
            Arguments.of( new Object[]{"2", nic, ifn, ifn, ppid, iaddr, idob, pn, (Object)iblob}),
            Arguments.of( new Object[]{"2", inic, ifn, ifn, ppid, addr, dob, ipn, (Object)blob}),
            Arguments.of( new Object[]{"2", badnic, fn, fn, ippid, iaddr, idob, pn, (Object)blob}),
            Arguments.of( new Object[]{"2", inic, fn, fn, ippid, addr, dob, ipn, (Object)iblob})
        );
    }
}


