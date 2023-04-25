package WhoWantsToBeAMillionaire;

import java.util.Random;

/**
 *
 * @author yutas
 */
public class FiftyFifty extends LifeLines {

    private Random rand;

    //Uses FiftyFifty
    public Question fiftyfifty(Question question) {
        rand = new Random();

        String[] currentQuestions = question.getAnswers(); //Create a new String array with the input parameter question's answer
        int answerIndex = question.getCorrectAnswerIndex(); //Get the index of the answer int he parameter question
        int randomIndex = 0;

        String[] fiftyfifty = new String[2];
        fiftyfifty[0] = currentQuestions[answerIndex];

        do {
            randomIndex = rand.nextInt(4);//Randomise a random index that is not the answer index
        } while (randomIndex == answerIndex);
        fiftyfifty[1] = currentQuestions[randomIndex];

        Question newQuestion = new Question(question.getQuestion(), fiftyfifty, 0); //Create a new question object to return, this will represen the question after fiftyfifty was used.
        super.lifeLineUsed();

        return newQuestion;
    }
}
