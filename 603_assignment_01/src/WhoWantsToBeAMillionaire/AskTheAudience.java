package WhoWantsToBeAMillionaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AskTheAudience extends LifeLines {

    private Random rand;

    public Question AskTheAudience(Question question) {
        rand = new Random();
        int randomNum = rand.nextInt(21) + 40; // between 40 and 60
        
        String[] replaceValues = new String[4];
        
        return question;
    }
}
