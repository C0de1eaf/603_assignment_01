package WhoWantsToBeAMillionaire;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {

    private String name;
    private int prizeMoney;
    private ArrayList<String> listOfParticipants;

    public User(String name) {
        this.name = name;
        this.prizeMoney = 0;
        listOfParticipants = new ArrayList<>();
    }

    public boolean userExists() {
        boolean userExists = false;

        try {
            FileReader fr = new FileReader("./resources/userlist.txt");
            Scanner fileScanner = new Scanner(fr);

            while (fileScanner.hasNextLine()) {
                String fileContent = fileScanner.nextLine();
                this.listOfParticipants.add(fileContent);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

        for (String e : this.listOfParticipants) {
            if (e.toLowerCase().contains(this.name.toLowerCase())) {
                userExists = true;
                break;
            }
        }

        return userExists;
    }

    public void update(int prizeMoney) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./resources/userlist.txt"))) {
            if (userExists()) {
                for (String e : this.listOfParticipants) {
                    if (e.toLowerCase().contains(this.name.toLowerCase())) {
                        this.listOfParticipants.set(this.listOfParticipants.indexOf(e), this.name + " " + this.prizeMoney);
                    }
                }
            } else {
                this.listOfParticipants.add(this.name + " " + this.prizeMoney);
            }
            for (String data : this.listOfParticipants) {
                bw.write(data);
                bw.newLine();
            }
        }
    }
}
