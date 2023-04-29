package WhoWantsToBeAMillionaire;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Leaderboard {

    private final HashMap<String, Integer> leaderboard;

    public Leaderboard() {
        this.leaderboard = new HashMap<>();
    }

    private HashMap<String, Integer> readLeaderboard() {
        try {
            FileReader fr = new FileReader("./resources/leaderboard.txt"); //Read the leaderboard.txt
            Scanner fileScanner = new Scanner(fr);

            //Add the leaderboard.txt contents into the leaderboard HashMap
            while (fileScanner.hasNextLine()) {
                String fileContent = fileScanner.nextLine();

                //Split the fileContent into parts, to effectively add into the HashMap
                String[] parts = fileContent.split(" ");
                String name = parts[0]+" "+parts[1];
                int prizeMoney = Integer.parseInt(parts[2]); //Pase it as an integer to add properly
                this.leaderboard.put(name, prizeMoney);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

        return this.leaderboard;
    }

    public void updateLeaderboard(User user, int prize) {
        HashMap<String, Integer> leaderboard2 = readLeaderboard(); //Add the leaderboard contents into a new leaderboard HashMap to use
        String name = user.getName(); //Get the pram user's name
        leaderboard2.put(name, prize);

        List<Map.Entry<String, Integer>> sortedLeaderboard = new ArrayList<>(leaderboard.entrySet()); //Create a new List to be sorted
        sortedLeaderboard.sort(Map.Entry.<String, Integer>comparingByValue().reversed()); //Reverse the order of the List, effectively descending in order

        while (sortedLeaderboard.size() > 5) {
            sortedLeaderboard.remove(sortedLeaderboard.size() - 1); //Remove the 6th leaderboard entry (the smallest one so it gets removed to retain a top 5)
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./resources/leaderboard.txt"))) { //Create a writer 
            for (Map.Entry<String, Integer> entry : sortedLeaderboard) { //Iterate through the sorted leaderboard
                bw.write(entry.getKey() + " " + entry.getValue()); //Write into the file with the entry's key (name) and the value (prize)
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}
