package GameLifeLines;

import WhoWantsToBeAMillionaire.Question;
import java.util.Random;

// This class provides the implementation for the Fifty-Fifty lifeline.
// The lifeline removes two incorrect answer options, leaving the correct answer and one incorrect answer.
public class FiftyFifty extends LifeLines {

    private Random rand; // A random number generator object for selecting incorrect answer options.

    // This method uses the Fifty-Fifty lifeline on a given question and returns a new question object with modified answer options.
    // The method takes a Question object as a parameter and returns a modified Question object.
    public Question fiftyFifty(Question question) {
        rand = new Random(); // Initialize the random number generator object.

        String[] currentQuestions = question.getAnswers(); // Get the current answer options for the question.
        int answerIndex = question.getCorrectAnswerIndex(); // Get the index of the correct answer.

        String[] fiftyFifty = new String[4]; // Create a new string array to hold the modified answer options.
        fiftyFifty[answerIndex] = currentQuestions[answerIndex]; // Keep the correct answer.

        int count = 0; // A counter for the number of incorrect answer options removed.
        while (count < 2) {
            int randomIndex = rand.nextInt(4); // Select a random index for an incorrect answer option.
            if (randomIndex != answerIndex && fiftyFifty[randomIndex] == null) { // Check that the index is not the index of the correct answer, and that the answer option has not already been removed.
                fiftyFifty[randomIndex] = " "; // Replace the incorrect answer option with a blank.
                count++; // Increment the counter.
            }
        }

        for (int i = 0; i < currentQuestions.length; i++) {
            if (fiftyFifty[i] == null) {
                fiftyFifty[i] = currentQuestions[i]; // Keep the remaining answer options.
            }
        }

        Question newQuestion = new Question(question.getQuestion(), fiftyFifty, answerIndex); // Create a new Question object with the modified answer options.
        super.lifeLineUsed(); // Mark the lifeline as used.

        return newQuestion; // Return the modified Question object.
    }
}
