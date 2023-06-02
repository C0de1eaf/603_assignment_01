package WhoWantsToBeAMillionaire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MillionaireDB {

    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:WWTBAM_EmbDB;create=true";
    protected Connection conn;

    public MillionaireDB() {
        this.createConnection();
    }

//    public static void main(String[] args) {
//        MillionaireDB db = new MillionaireDB();
//        System.out.println(db.getLeaderboard().toString());
//    }
    //Obtains the easyQuestions from the database
    public ArrayList<Question> getEasyQuestions() {
        ArrayList<Question> easyQuestions = new ArrayList<>(); //Create an ArrayList
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EasyQuestions"); //Create a query to get all Questions from EasyQuestions

            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String[] options = resultSet.getString("options").split(",");
                int correctAnswer = resultSet.getInt("correctAnswer");
                Question q = new Question(question, options, correctAnswer); //Create a new Question object 
                easyQuestions.add(q); //Add it to the easyQuestions arraylist
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
        return easyQuestions;
    }

    //Obtains the hardQuestions from the database
    public ArrayList<Question> getHardQuestions() {
        ArrayList<Question> hardQuestions = new ArrayList<>(); //Create an ArrayList
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM HardQuestions"); //Create a query to get all Questions from HardQuestions

            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String[] options = resultSet.getString("options").split(",");
                int correctAnswer = resultSet.getInt("correctAnswer");
                Question q = new Question(question, options, correctAnswer); //Create a new Question object  
                hardQuestions.add(q); //Add it to the hardQuestions arraylist
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

    //Returns an arraylist version of the top 5 leaderboard
    public ArrayList<String> getLeaderboard() {
        ArrayList<String> leaderboard = new ArrayList<>(); //Create an arraylist
        String tableName = "leaderboard";
        try (Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int prizeMoney = resultSet.getInt("prizemoney");
                leaderboard.add(name + " " + prizeMoney); //Add the name and prizeMoney to the arraylist
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard; //Return leaderboard 
    }
}
