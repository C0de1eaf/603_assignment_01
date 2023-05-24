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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GUI_Database {

    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:WWTBAM_EmbDB;create=true";
    private Connection conn;

    public GUI_Database() {
        this.createConnection();
    }
    
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
}
