/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MillionaireDB;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yutas
 */
public class GameDBTest {

    private static Connection connection;
    private static final GameDB db = new GameDB();

    @Before
    public void setUp() {
        if (db.conn != null) {
            connection = db.conn;
        } else {
            System.out.println("Database did not have a valid connection!");
        }
    }

    @After
    public void tearDown() throws Exception {
        // Close the database connection
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    //Test for the connection and ensure Database works
    @Test
    public void testDBConnection() {
        try {
            assertNotNull(connection);
            assertTrue(connection.isValid(5));
        } catch (SQLException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
