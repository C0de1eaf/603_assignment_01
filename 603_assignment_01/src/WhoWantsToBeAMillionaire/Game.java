package WhoWantsToBeAMillionaire;

import java.io.IOException;
import java.util.*;

public class Game {

    public ArrayList<ArrayList> questions;
    int[] cashPrize = {100, 200, 300, 500, 1000, 5000, 10000, 50000, 100000, 250000};
    private final Scanner scan;
    private int currentLevel;
    private final Random rand;
    public FiftyFifty useFiftyFifty;
    public AskTheAudience askTheAudience;
    public PhoneAFriend phoneAFriend;
    public QuestionList questionList;

    public Game() {
        this.useFiftyFifty = new FiftyFifty();
        this.askTheAudience = new AskTheAudience();
        this.phoneAFriend = new PhoneAFriend();
        this.rand = new Random();
        this.scan = new Scanner(System.in);
        this.questionList = new QuestionList();
        this.questions = this.questionList.questionsCreation();
    }

    public void runGame() throws IOException {
        int totalQuestions = 10;
        currentLevel = 1;
        String name = "";
        boolean nameValidity = false;
        boolean continuePlaying = true;
        int currentCash = 0;

        do {
            System.out.println("\nWelcome to the Who Wants To Be A Millionaire game.");
            System.out.println("Please enter your name:");

            while (!nameValidity) {
                name = scan.nextLine();
                if (name.matches("[a-zA-Z ]+")) {
                    nameValidity = true;
                } else {
                    System.out.println("Please only enter a name!");
                }
            }

            User newUser = new User(name);
            if (newUser.userExists()) {
                System.out.println("You have previously participated!");
                System.out.println("We will update your prize money after this game.");
            } else {
                System.out.println("Welcome " + name + "!");
            }

            for (int i = 0; i < totalQuestions && continuePlaying; i++) {
                Question current = getRandomQuestion();
                current.printQuestion();
                int levelAnswer = current.getCorrectAnswerIndex();

                int userAnswer = 0;
                boolean validity = false;

                while (!validity) {
                    System.out.print("> ");
                    String input = scan.next();

                    try {
                        userAnswer = Integer.parseInt(input);
                        if ((userAnswer < 1 || userAnswer > 4) && (userAnswer < 7 || userAnswer > 9)) {
                            System.out.println("Invalid input. Please try again.");
                        } else if (userAnswer == 7) {
                            if (useFiftyFifty.isUsed()) {
                                System.out.println("Sorry, 50:50 is already used!");
                                current.printQuestion();
                            } else {
                                // fiftyFifty
                                current = useFiftyFifty.fiftyFifty(current);
                                current.printQuestion();
                            }
                        } else if (userAnswer == 8) {
                            // ask the audience
                            if (askTheAudience.isUsed()) {
                                System.out.println("You have already asked the audience");
                                current.printQuestion();
                            } else {
                                askTheAudience.askTheAudience(current);
                            }

                        } else if (userAnswer == 9) {
                            // phone a friend
                            if (this.phoneAFriend.isUsed()) {
                                System.out.println("You have already phoned a friend.");
                                current.printQuestion();
                            } else {
                                phoneAFriend.phoneAFriend(current);
                                current.printQuestion();
                            }
                        } else {
                            validity = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please try again.");
                    }
                }

                if (userAnswer == levelAnswer + 1) {
                    System.out.println("Correct!");
                    currentCash = getPrize();
                    newUser.update(currentCash);
                } else {
                    System.out.println("Sorry but you are wrong and it is game over!\n"
                            + "You lose your cash prize of [$" + currentCash + "]");
                    currentCash = 0;
                    newUser.update(currentCash);
                    break;
                }
                continuePlaying = continuePlaying();
                currentLevel++;
            }

            if (!continuePlaying) {
                System.out.println("you quit");
                break; // delete if needed
            } else {
                System.out.println("Do you want to play again? (Y/N)");
                String playAgain = scan.next().toUpperCase();
                continuePlaying = playAgain.equals("Y");
                scan.nextLine();
                nameValidity = false;
                currentLevel = 1;
                currentCash = 0;
            }

        } while (continuePlaying);

        System.out.println("You decided to resign, you won a total of $" + currentCash + " dollars!");
    }

    public int getPrize() {
        int prize;
        prize = cashPrize[currentLevel - 1];
        return prize;
    }

    public Question getRandomQuestion() {
        System.out.println("\n--Current Level: " + currentLevel + "--\n"); // prints out current level
        int questionIndex = rand.nextInt(questions.get(currentLevel < 7 ? 0 : 1).size()); // get random question randomly based on the size of the array
        ArrayList<Question> questionList = questions.get(currentLevel < 7 ? 0 : 1); // create a new ArrayList variable based on the level difficulty
        Question selectedQuestion = questionList.get(questionIndex);

        questionList.remove(questionIndex); // remove the chosen question from the ArrayList

        // Testing purposes [size of selected list + selected question within the list
//        System.out.println("Size of updated array " + questionList.size() + "\nchosen question inside the array " + (questionIndex + 1));
        return selectedQuestion;
    }

    // ask user if they want to opt out of the game
    public boolean continuePlaying() {
        scan.nextLine();
        int currentCash = getPrize();
        System.out.print("You earned a total of [" + currentCash + "]\n");
        System.out.print("Do you want to continue playing? (Y/N)\n> ");
        String input = scan.nextLine();
        while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
            System.out.print("Error: Invalid input. Please enter [Y] or [N].\n> ");
            input = scan.nextLine();
        }
        return input.equalsIgnoreCase("Y");
    }

    /**
     * @return the currentLevel
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @param currentLevel the currentLevel to set
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
