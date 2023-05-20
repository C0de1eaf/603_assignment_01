/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WhoWantsToBeAMillionaire;

/**
 *
 * @author yutas
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GUI_Database {

    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:WWTBAM_EmbDB;create=true";
    private Connection conn;

    public static void main(String[] args) throws SQLException {
        //Test
        GUI_Database db = new GUI_Database();
        db.createConnection();
        //db.createTables();
        //db.checkTables();
    }

    public GUI_Database() {
        this.createConnection();
    }

    public void createConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                System.out.println(URL + " connection successful.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void createTables() throws SQLException {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql = "CREATE TABLE Participants (id INT PRIMARY KEY, NAME VARCHAR(50), PRIZEMONEY INT)";
            statement.executeUpdate(sql);
            System.out.println("Table created.");
            
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
        statement.close();
    }

    public void checkTables() throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.createStatement();
            String sql = "SELECT * FROM Participants";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("NAME");
                int prizeMoney = resultSet.getInt("PRIZEMONEY");

                System.out.println("ID: " + id + ", Name: " + name + ", Prize Money: " + prizeMoney);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
        statement.close();
        resultSet.close();
    }
}
