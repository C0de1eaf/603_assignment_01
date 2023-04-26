package WhoWantsToBeAMillionaire;

import java.util.ArrayList;

public class FriendList {
    
    private ArrayList<String> listOfFriends;
    
    public FriendList(){
        this.listOfFriends = new ArrayList<>();
    }
    
    public ArrayList<String> createFriendList(){
        this.listOfFriends.add("John");
        this.listOfFriends.add("Lila");
        this.listOfFriends.add("Sam");
        this.listOfFriends.add("Dexter");
        this.listOfFriends.add("Amelia");
        
        return this.listOfFriends; //Return the list of friends
    }
}
