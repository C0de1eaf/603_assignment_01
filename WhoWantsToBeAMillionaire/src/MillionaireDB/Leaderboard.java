package MillionaireDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {

    private final HashMap<String, Integer> leaderboard;
    private final GameDB db;

    public Leaderboard() {
        this.leaderboard = new HashMap<>();
        this.db = new GameDB();
    }

    private void readLeaderboard() {
        try {
            try (Statement statement = db.conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM leaderboard")) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int prizeMoney = resultSet.getInt("prizemoney");
                    this.leaderboard.put(name, prizeMoney);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLeaderboard(User user, int prize) {
        readLeaderboard(); // Add the leaderboard contents into a new leaderboard HashMap to use
        String name = user.getName(); // Get the param user's name
        leaderboard.put(name, prize);

        List<Map.Entry<String, Integer>> sortedLeaderboard = new ArrayList<>(leaderboard.entrySet());
        sortedLeaderboard.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        while (sortedLeaderboard.size() > 10) {
            sortedLeaderboard.remove(sortedLeaderboard.size() - 1);
        }

        try (Statement statement = db.conn.createStatement()) {
            // Clear existing leaderboard data
            statement.executeUpdate("DELETE FROM leaderboard");

            // Insert updated leaderboard data
            for (Map.Entry<String, Integer> entry : sortedLeaderboard) {
                String entryName = entry.getKey();
                int entryPrizeMoney = entry.getValue();
                String query = "INSERT INTO leaderboard (name, prizemoney) VALUES ('" + entryName + "', " + entryPrizeMoney + ")";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
