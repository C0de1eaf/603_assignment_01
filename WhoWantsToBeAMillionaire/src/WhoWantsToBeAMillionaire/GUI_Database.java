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
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GUI_Database {

    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:WWTBAM_EmbDB;create=true";
    protected Connection conn;

    public GUI_Database() {
        this.createConnection();
    }

//    public static void main(String[] args) throws SQLException {
//        GUI_Database gui = new GUI_Database();
//        gui.fetchData();
//    }

    public ArrayList<Question> getEasyQuestions() {
        ArrayList<Question> easyQuestions = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EasyQuestions");

            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String[] options = resultSet.getString("options").split(",");
                int correctAnswer = resultSet.getInt("correctAnswer");
                Question q = new Question(question, options, correctAnswer);
                easyQuestions.add(q);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
        return easyQuestions;
    }

    public ArrayList<Question> getHardQuestions() {
        ArrayList<Question> hardQuestions = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM HardQuestions");

            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String[] options = resultSet.getString("options").split(",");
                int correctAnswer = resultSet.getInt("correctAnswer");
                Question q = new Question(question, options, correctAnswer);
                hardQuestions.add(q);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
        return hardQuestions;
    }

    //Create a connection
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

    //Get all tables from the database
    public void getTables() throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        String[] tableTypes = {"TABLE"};
        ResultSet rs = metaData.getTables(null, null, "%", tableTypes);

        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            System.out.println("Table: " + tableName);
        }
        rs.close();
    }

    public void fetchData() {
        String tableName = "participants";
        try (Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    System.out.println(columnName + ": " + value);
                }
                System.out.println("---------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Drop a table
    public void dropTable() {
        try (Statement statement = conn.createStatement()) {
            // Execute the SQL command to delete the "Questions" table
            String query = "DROP TABLE HardQuestions";
            statement.executeUpdate(query);
            System.out.println("Deleted table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
