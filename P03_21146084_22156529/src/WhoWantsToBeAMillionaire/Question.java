package WhoWantsToBeAMillionaire;

public class Question {

    /*
     * Structure of the questions class:
     * Level of difficulty - not included in the implementation
     * String Question - the actual question being asked
     * String Array of Multichoice questions - array containing the possible answers to the question
     * Correct index of the question - the index of the correct answer in the array of possible answers
     */
// private fields for the question, array of answers, and correct answer index
    private String question;
    private String[] answers;
    private int correctAnswerIndex;

// constructor method for creating a Question object with a question, array of answers, and correct answer index
    public Question(String question, String[] answers, int correctAnswerIndex) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

// getter method for getting the question string
    public String getQuestion() {
        return question;
    }

// setter method for setting the question string
    public void setQuestion(String question) {
        this.question = question;
    }

// getter method for getting the array of possible answers
    public String[] getAnswers() {
        return answers;
    }

// setter method for setting the array of possible answers
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

// getter method for getting the index of the correct answer in the array of possible answers
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

// setter method for setting the index of the correct answer in the array of possible answers
    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

// method for printing the question and array of possible answers
    public void printQuestion() {
        System.out.println(question); // print the question
        int counter = 0; // counter variable for printing the answer index
        for (String answer : answers) { // loop through the array of answers
            System.out.println((counter += 1) + ") " + answer); // print the answer with its index
        }
    }
}
