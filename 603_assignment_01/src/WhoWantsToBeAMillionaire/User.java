package WhoWantsToBeAMillionaire;

import java.util.ArrayList;

public class User {
    private String name;
    private int prizeMoney;
    private ArrayList<String> listOfParticipants;
    
    public User(String name, int prizeMoney){
        this.name = name;
        this.prizeMoney = prizeMoney;
    }
}
