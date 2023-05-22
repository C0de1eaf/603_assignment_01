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
    
    public static void main(String[] args) {
        //Test here
    }

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
    
    //Create a table for questions
    public void createTable() {
        try (Statement statement = conn.createStatement()) {
            // Execute the SQL command to create the "EasyQuestions" table
            String query = "CREATE TABLE HardQuestions ("
                    + "question VARCHAR(255),"
                    + "options VARCHAR(255),"
                    + "correctAnswer INT)";
            statement.executeUpdate(query);

            System.out.println("Table has been created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Use this to insert Questions into DataBase
    public void insert() {
        QuestionList questionLists = new QuestionList();
        questionLists.createQuestionList();
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO HardQuestions (question, options, correctAnswer) VALUES (?, ?, ?)")) {
            for (ArrayList<Question> questionList : questionLists.getHardQuestions()) {
                for (Question question : questionList) {
                    statement.setString(1, question.getQuestion());
                    statement.setString(2, String.join(",", question.getAnswers()));
                    statement.setInt(3, question.getCorrectAnswerIndex());
                    statement.executeUpdate();
                }
            }
            System.out.println("Done!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
