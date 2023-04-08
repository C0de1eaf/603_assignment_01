package WhoWantsToBeAMillionaire;

public class Questions {

    /*
    Structure of the questions class:
    Level of difficulty
    String Question
    String Array of Multichoice questions
    Correct index of the question
     */
    private int difficulty;
    private String question;
    private String[] answers;
    private int correctAnswerIndex;

    public Questions(int difficulty, String question, String[] answers, int correctAnswerIndex) {
        this.difficulty = difficulty;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public void printQuestion() {
        System.out.println(question);
        int counter = 0;
        for (String answer : answers) {
            System.out.println((counter += 1) + ") " + answer);
        }
    }
}
