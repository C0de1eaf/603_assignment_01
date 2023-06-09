package MillionaireDB;

import WhoWantsToBeAMillionaire.Question;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GameDB {

    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:WWTBAM_EmbDB;create=true";
    protected Connection conn;

    public GameDB() {
        this.createConnection();
    }

    public static void main(String[] args) {
        GameDB db = new GameDB();
        db.getCols();
    }

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

    public void createTable() {
        try (Statement statement = conn.createStatement()) {
            // Execute the SQL command to create the "EasyQuestions" table
            String query = "CREATE TABLE leaderboard ("
                    + "name VARCHAR(255),"
                    + "prizemoney INT)";
            statement.executeUpdate(query);

            System.out.println("Table has been created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Get columns 
    public void getCols() {
        String tableName = "leaderboard";
        try (Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                String columnType = metaData.getColumnTypeName(i);
                System.out.println("Column name: " + columnName);
                System.out.println("Column type: " + columnType);
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
            String query = "DROP TABLE leaderboard";
            statement.executeUpdate(query);
            System.out.println("Deleted table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
