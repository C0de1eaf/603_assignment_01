package WhoWantsToBeAMillionaire;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private int prizeWon;
    private ArrayList<String> listOfParticipants;
    
    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.prizeWon = 0;
    }
}
