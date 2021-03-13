package com.mycompany.airlinereservationsoftwaremaven;

import org.junit.jupiter.api.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author drose
 */
public class searchCustomerTest {
    
    
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
     * Input: call method findButtonActionPerformed()
     * Expected state: customer NIC no. field in searchCustomer tab is displayed
     */
    @Test
    public void testFindButtonActionPerformed() {
        
    }
}
