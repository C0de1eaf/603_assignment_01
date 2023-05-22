package WhoWantsToBeAMillionaire;

import java.io.IOException;
import java.util.*;

public class Game {

    // create variables that are used throughout this class
    private final List<ArrayList<Question>> questions;
    int[] cashPrize = {100, 200, 300, 500, 1000, 5000, 10000, 50000, 100000, 250000};
    private final Scanner scan;
    private int currentLevel;
    private final Random rand;
    public FiftyFifty fiftyFifty;
    public AskTheAudience askTheAudience;
    public PhoneAFriend phoneAFriend;
    public Leaderboard leaderboard;
    private GUI_Database db;

    // sets the initial values of variables
    public Game() {
        this.fiftyFifty = new FiftyFifty();
        this.askTheAudience = new AskTheAudience();
        this.phoneAFriend = new PhoneAFriend();
        this.rand = new Random();
        this.scan = new Scanner(System.in);
        this.leaderboard = new Leaderboard();
        this.db = new GUI_Database();
        
        ArrayList<Question> easy = db.getEasyQuestions();
        ArrayList<Question> hard = db.getHardQuestions();
        this.questions = Arrays.asList(easy, hard);
    }

    /* 
    This is the method which is ran in the Main method in WWTBAM class
    It is responsible for delivering the game experience and connecting all the methods together
    More comments inside...
     */
    public void runGame() throws IOException {
        // create initial variables and give them default variables.
        int totalQuestions = 10;
        currentLevel = 1;
        String name = "";
        boolean nameValidity = false;
        boolean continuePlaying = true;
        int currentCash = 0;

        /* 
        Loops the entire program until it is stopped
        It is stopped by setting the continuePlaying to false
         */
        do {
            // Welcomes the player to the game
            System.out.println("\nWelcome to the Who Wants To Be A Millionaire game.");
            System.out.println("You will be asked a total of 10 questions with varying difficulty");
            System.out.println("as the questions go on. Please answer them by typing 1 - 4 in the");
            System.out.println("console, for the game to process your answer.\n");
            System.out.println("Please enter your full name:");

            /*
            Validates the name of the user
            If the users input is invalid, ask the user again until valid
            Provides an example of a name for the string
             */
            while (!nameValidity) {
                name = scan.nextLine();
                if (name.matches("[a-zA-Z ]+ [a-zA-Z ]+")) {
                    nameValidity = true;
                } else {
                    System.out.println("Please only enter a valid name!\nExample: John Doe");
                }
            }

            /*
            Creates a new user object and checks if the user already exists
            If the user exists then the program will tell that your Score will be updated at the end of the game
            Otherwise creates the user and welcomes them
             */
            User newUser = new User(name);
            if (newUser.userExists()) {
                System.out.println("You have previously participated!");
                System.out.println("We will update your prize money after this game.\n");
            } else {
                System.out.println("Welcome " + name + "!\n");
            }

            // Tells the user about a special feature (LifeLines)
            // They are used to assist the user when answering a question
            // Tells the user how to use a Lifeline
            System.out.println("Anytime during this game, you will be able to use each of the 3 LifeLines once.");
            System.out.println("To use each LifeLine, type: ");
            System.out.println("7 - To use 50:50");
            System.out.println("8 - To use Ask An Audience");
            System.out.println("9 - To use Phone A Friend");

            /*
            Creates a loop which will last 10 Rounds becuase totalQuestions = 10
            continues the loop until it is met certain conditions such as Losing, or opting out of game
             */
            for (int i = 0; i < totalQuestions && continuePlaying; i++) {
                // creates the CURRENT question for the user randomly
                Question current = getRandomQuestion();
                current.printQuestion();

                // Gets the answer of the question of the CURRENT question
                int levelAnswer = current.getCorrectAnswerIndex();

                int userAnswer = 0;
                boolean validity = false;

                /* 
                Creates a loop which takes in the users input and checks if it is the answer
                depending on the users input, the program responds accordingly
                Doesn't allow incorrect input of any sort.
                Once the input is valid, set validity to TRUE which continues the game
                 */
                while (!validity) {
                    System.out.print("> ");
                    String input = scan.next();

                    // Checks the input
                    try {
                        // Program only allows integers as input
                        userAnswer = Integer.parseInt(input);
                        // If input is a number which isn't 1, 2, 3, 4 or 7, 8, 9 then it tells user incorrect input
                        if ((userAnswer < 1 || userAnswer > 4) && (userAnswer < 7 || userAnswer > 9)) {
                            System.out.println("Invalid input. Please try again.");
                        } else if (userAnswer == 7) { // Else if the answer is 7 then it is equal to the user using a 50:50 Lifeline
                            // If the 50:50 lifeline has been used in the past, tell the user it was used.
                            if (fiftyFifty.isUsed()) {
                                System.out.println("Sorry, 50:50 is already used!");
                                current.printQuestion();
                            } else { // Otherwise use the 50:50 lifeline
                                // fiftyFifty
                                current = fiftyFifty.fiftyFifty(current);
                                current.printQuestion();
                            }
                        } else if (userAnswer == 8) { // Else if the answer is 8 then it is equal to the user using a Ask the Audience Lifeline
                            // If the Ask the Audience lifeline has been used in the past, tell the user it was used.
                            if (askTheAudience.isUsed()) {
                                System.out.println("You have already asked the audience");
                                current.printQuestion();
                            } else { // Otherwise use the Ask the Audience lifeline
                                askTheAudience.askTheAudience(current);
                            }
                        } else if (userAnswer == 9) { // Else if the answer is 9 then it is equal to the user using a Phone A Friend Lifeline
                            // If the Phone a Friend lifeline has been used in the past, tell the user it was used.
                            if (this.phoneAFriend.isUsed()) {
                                System.out.println("You have already phoned a friend.");
                                current.printQuestion();
                            } else { // Otherwise use the Phone a Friend lifeline
                                phoneAFriend.phoneAFriend(current);
                                current.printQuestion();
                            }
                        } else { // Sets validity to true if the input was valid
                            validity = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please try again.");
                        scan.nextLine();
                    }
                }

                /*
                Checks if the user answer is equal to the question answer
                Since the question numbers are stored as an index of an Array it goes 0, 1, 2, 3
                Because the user was prompted to answer 1,2,3 or 4 it is logical to increase the levelAnswer by one so the index + 1 matches the users question selection
                */
                if (userAnswer == levelAnswer + 1) {
                    // If the user is correct, prints correct
                    System.out.println("Correct!");
                    // Updates the users Cash prize
                    currentCash = getPrize();
                    // Update the users cash score
                    newUser.update(currentCash);
                    // Updates the leaderboard
                    leaderboard.updateLeaderboard(newUser, currentCash);
                } else {
                    // User answered incorrectly, they lose
                    System.out.println("Sorry but you are wrong and it is game over!\n"
                            + "You lose your cash prize of [$" + currentCash + "]");
                    // Sets the cash score to 0 due to their loss
                    currentCash = 0;
                    // updates cash score again after it was set to 0
                    newUser.update(currentCash);
                    // update leaderboard
                    leaderboard.updateLeaderboard(newUser, currentCash);
                    // break out of loop
                    break;
                }

                // If the user answers 10 questions correctly then they are prompted with a Victory message
                if (currentLevel == 10) {
                    System.out.println("Congradulations! You have won the Who Wants To Be A Millionaire Game.");
                    System.out.println("You have won " + currentCash + "!");
                    System.out.println("Thank you for participating!");
                    leaderboard.updateLeaderboard(newUser, currentCash);
                    break;
                }
                
                // Asks the user if they want to continue playing and then updates the currentLevel
                continuePlaying = continuePlaying();
                currentLevel++;
            }

            // If the user chooses to not continue playing, then the program resigns them and lets them keep their cash prize and stops the game
            if (!continuePlaying) {
                System.out.println("You decided to resign, you won a total of $" + currentCash + " dollars!");
                break; // delete if needed
            } else { // if the user loses the game ask them if they want to play the game again
                System.out.println("Do you want to play again? (Y/N)");
                String playAgain = scan.next().toUpperCase();
                continuePlaying = playAgain.equals("Y");
                scan.nextLine();
                nameValidity = false;
                currentLevel = 1;
                currentCash = 0;

                //Reset lifelines for next game
                fiftyFifty.resetLifeLine();
                askTheAudience.resetLifeLine();
                phoneAFriend.resetLifeLine();
            }
        } while (continuePlaying);
    }

    // Updates the prize money method
    public int getPrize() {
        int prize;
        prize = cashPrize[currentLevel - 1];
        return prize;
    }

    /*
    Generates a random question for the user that is stored in the QuestionList class
    It gets the size of the List and generates a random number between 0 and the size of the list then picks the random question
    Once picked, remove that question from the list and updates the list
    The random Question difficulty depends on the currentLevel
    Generates harder questions for when the user is on level 7, 8, 9 and 10 otherwise the questions generated are easy
    Returns the selected question
    */
    public Question getRandomQuestion() {
        System.out.println("\n--Current Level: " + currentLevel + "--\n"); // prints out current level
        int questionIndex = rand.nextInt(questions.get(currentLevel < 7 ? 0 : 1).size()); // get random question randomly based on the size of the array
        ArrayList<Question> questionList = questions.get(currentLevel < 7 ? 0 : 1); // create a new ArrayList variable based on the level difficulty
        Question selectedQuestion = questionList.get(questionIndex);

        questionList.remove(questionIndex); // remove the chosen question from the ArrayList

        return selectedQuestion;
    }

    /*
    Asks the user if they want to continue playing or not
    Prints the users current cash prize and asks if they want to leave.
    Checks for valid input (accepts only Y and N)
    */
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
