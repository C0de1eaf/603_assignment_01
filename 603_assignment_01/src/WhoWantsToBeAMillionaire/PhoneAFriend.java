package WhoWantsToBeAMillionaire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PhoneAFriend extends LifeLines {

    private final ArrayList<String> friends;
    private final FriendList friendList;
    private final Random rand;

    public PhoneAFriend() {
        this.friendList = new FriendList();
        this.friends = this.friendList.createFriendList();
        this.rand = new Random();
    }

    public void phoneAFriend(Question question) {
        int answerIndex = question.getCorrectAnswerIndex(); //Get the correct answer
        String friendToCall = friends.get(rand.nextInt(friends.size())); //Get a random friend from the list of friends

        System.out.println("Hmm, who should I call.");
        System.out.println("I'll call " + friendToCall);
        System.out.println("Ringing....");
        System.out.println(friendToCall + ": Hey, what's up?");
        System.out.println("I need some help with this question, what do you think?");

        int[] weights = new int[4]; //Weights to distribute across the random number geenration
        for (int i = 0; i < 4; i++) {
            if (i == answerIndex) { //Make sure the correct answer is weighted higher
                weights[i] = 10;
            } else {
                weights[i] = 1;
            }
        }

        int totalWeight = Arrays.stream(weights).sum(); //Sum all the weights of the friends weights using stream and .sum()
        int randomWeight = rand.nextInt(totalWeight);  //Select a random number 

        //Iterate and choose a random number
        int cumulativeWeight = 0;
        for (int i = 0; i < 4; i++) {
            cumulativeWeight += weights[i]; //Run the loop until the cumulative weight > the random number
            if (randomWeight < cumulativeWeight) {
                System.out.println(friendToCall + ": I think it's option " + (i + 1) + "\n"); //Friends answer
                break;
            }
        }

        super.lifeLineUsed();
    }
}
