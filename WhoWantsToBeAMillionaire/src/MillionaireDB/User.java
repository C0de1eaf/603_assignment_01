package MillionaireDB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    private final String name;
    private final GameDB db;

    public User(String name) {
        this.name = name;
        this.db = new GameDB();
    }

    // Check if user exists in the list of participants
    public boolean userExists() {
        boolean userExists = false;

        try {
            try (Statement statement = db.conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM participants")) {
                while (resultSet.next()) {
                    String participantName = resultSet.getString("name");
                    if (participantName.equalsIgnoreCase(this.name)) {
                        userExists = true;
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userExists;
    }

    // Update the list of participants with the user's new prize money
    public void update(int prizeMoney) throws IOException {
        try {
            try (Statement statement = db.conn.createStatement()) {
                boolean userExists = userExists();
                if (userExists) {
                    String updateQuery = "UPDATE participants SET prizemoney = " + prizeMoney + " WHERE name = '" + this.name + "'";
                    statement.executeUpdate(updateQuery);
                } else {
                    String insertQuery = "INSERT INTO participants (name, prizemoney) VALUES ('" + this.name + "', " + prizeMoney + ")";
                    statement.executeUpdate(insertQuery);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the user's name
    public String getName() {
        return this.name;
    }
}
