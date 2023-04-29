package WhoWantsToBeAMillionaire;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {

    private final String name;
    private final ArrayList<String> listOfParticipants;

    public User(String name) {
        this.name = name;
        listOfParticipants = new ArrayList<>();
    }

    // Check if user exists in the list of participants
    public boolean userExists() {
        boolean userExists = false;

        try {
            FileReader fr = new FileReader("./resources/participants.txt");
            Scanner fileScanner = new Scanner(fr);

            // Read through the file and add each participant to the list
            while (fileScanner.hasNextLine()) {
                String fileContent = fileScanner.nextLine();
                this.listOfParticipants.add(fileContent);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

        // Check if the user's name exists in the list of participants
        for (String e : this.listOfParticipants) {
            if (e.toLowerCase().contains(this.name.toLowerCase())) {
                userExists = true;
                break;
            }
        }

        return userExists;
    }

    // Update the list of participants with the user's new prize money
    public void update(int prizeMoney) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./resources/participants.txt"))) {
            if (userExists()) { // If the user exists, update their prize money in the list
                for (String e : this.listOfParticipants) {
                    if (e.toLowerCase().contains(this.name.toLowerCase())) {
                        this.listOfParticipants.set(this.listOfParticipants.indexOf(e), this.name + " " + prizeMoney);
                    }
                }
            } else { // If the user doesn't exist, add them to the list
                this.listOfParticipants.add(this.name + " " + prizeMoney);
            }
            // Write the updated list of participants to the file
            for (String data : this.listOfParticipants) {
                bw.write(data);
                bw.newLine();
            }
        }
    }

    // Get the user's name
    public String getName() {
        return this.name;
    }
}
