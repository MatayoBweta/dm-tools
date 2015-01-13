/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.controller;

import java.sql.Connection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;

/**
 *
 * @author UNHCRUser
 */
public class DataMistakeControllerTest {

    public DataMistakeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        
    }

    /**
     * Test of getContents method, of class DataMistakeController.
     */
    @Test
    public void testGetContents() throws Exception {
        System.out.println("getContents");
        Connection conn = EntityManagerSingleton.getDefault().getConnectionForproGres();
        int mistakeType = DataMistakeController.T_MISTAKE_BY_ID;
        DataMistakeController instance = new DataMistakeController();
        instance.getContents(conn, mistakeType);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }



}