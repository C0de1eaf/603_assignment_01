package WhoWantsToBeAMillionaire;

import java.util.Random;

public class FiftyFifty extends LifeLines {

    private Random rand;

    //Uses FiftyFifty
    public Question fiftyFifty(Question question) {
        rand = new Random();

        String[] currentQuestions = question.getAnswers(); // Get the current answers
        int answerIndex = question.getCorrectAnswerIndex(); // Get the index of the correct answer

        String[] fiftyFifty = new String[4];
        fiftyFifty[answerIndex] = currentQuestions[answerIndex]; // Keep the correct answer

        int count = 0;
        while (count < 2) {
            int randomIndex = rand.nextInt(4);
            if (randomIndex != answerIndex && fiftyFifty[randomIndex] == null) {
                fiftyFifty[randomIndex] = " "; // Replace an incorrect answer with a blank
                count++;
            }
        }

        for (int i = 0; i < currentQuestions.length; i++) {
            if (fiftyFifty[i] == null) {
                fiftyFifty[i] = currentQuestions[i]; // Keep the remaining answer options
            }
        }

        Question newQuestion = new Question(question.getQuestion(), fiftyFifty, answerIndex); // Create a new Question object with the modified answer options
        super.lifeLineUsed(); // Mark the lifeline as used

        return newQuestion;
    }

}
