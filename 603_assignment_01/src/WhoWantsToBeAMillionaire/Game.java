package WhoWantsToBeAMillionaire;

import java.io.IOException;
import java.util.*;

public class Game {

    public ArrayList<ArrayList> questions;
    int[] cashPrize = {100, 200, 300, 500, 1000, 5000, 10000, 50000, 100000, 250000};
    private Scanner scan = new Scanner(System.in);
    private int currentLevel;
    private final Random rand;
    public FiftyFifty useFiftyFifty = new FiftyFifty();

    public Game() {
        // create question list
        questions = questionsCreation();

        rand = new Random();
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
                            } else {
                                // fiftyFifty
                                current = useFiftyFifty.fiftyFifty(current);
                                current.printQuestion();
                            }
                        } else if (userAnswer == 8) {
                            // ask the audience

                        } else if (userAnswer == 9) {
                            // phone a friend

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

    // Create an ArrayList both with Easy and hard Questions
    public ArrayList questionsCreation() {
        ArrayList<Question> easy = new ArrayList();
        ArrayList<Question> hard = new ArrayList();

        // Create easy questions
        Question easyQ1 = new Question("What is the largest organ in the human body?", new String[]{" Brain", " Skin", " Liver", " Heart"}, 1);
        Question easyQ2 = new Question("What is the tallest mountain in the world?", new String[]{" Mount Everest", " Mount Kilimanjaro", " Mount McKinley", " Mount Rainier"}, 0);
        Question easyQ3 = new Question("What is the smallest planet in our solar system?", new String[]{" Venus", " Mars", " Mercury", " Jupiter"}, 2);
        Question easyQ4 = new Question("What is the chemical symbol for gold?", new String[]{" Ag", " Cu", " Au", " Fe"}, 2);
        Question easyQ5 = new Question("What is the capital city of Australia?", new String[]{" Sydney", " Melbourne", " Canberra", " Perth"}, 2);
        Question easyQ6 = new Question("What is the smallest country in the world?", new String[]{" Monaco", " Vatican City", " San Marino", " Liechtenstein"}, 1);
        Question easyQ7 = new Question("What is the most common gas found in the Earth's atmosphere?", new String[]{" Oxygen", " Nitrogen", " Carbon Dioxide", " Helium"}, 1);
        Question easyQ8 = new Question("What is the atomic number of the element Oxygen?", new String[]{" 6", " 8", " 10", " 12"}, 1);
        Question easyQ9 = new Question("What is the highest waterfall in the world?", new String[]{" Angel Falls", " Victoria Falls", " Niagara Falls", " Iguazu Falls"}, 0);
        Question easyQ10 = new Question("What is the name of the longest river in Africa?", new String[]{" Congo River", " Nile River", " Zambezi River", " Niger River"}, 1);
        Question easyQ11 = new Question("What is the name of the largest ocean in the world?", new String[]{" Atlantic Ocean", " Indian Ocean", " Arctic Ocean", " Pacific Ocean"}, 3);
        Question easyQ12 = new Question("What is the study of plants called?", new String[]{" Zoology", " Botany", " Geology", " Anthropology"}, 1);
        Question easyQ13 = new Question("Which element is represented by the symbol 'He'?", new String[]{" Helium", " Hydrogen", " Carbon", " Oxygen"}, 0);
        Question easyQ14 = new Question("Which country is home to the tallest mountain in the world?", new String[]{" United States", " Nepal", " China", " Russia"}, 1);
        Question easyQ15 = new Question("Which U.S. state is known as the \"Aloha State\"?", new String[]{"California", "Texas", "Hawaii", "Florida"}, 2);

        // Create hard questions
        Question hardQ1 = new Question("What is the chemical formula for benzene?", new String[]{" C5H5", " C6H5OH", " C6H6", " C6H12O6"}, 2);
        Question hardQ2 = new Question("What is the only continent that has no active volcanoes?", new String[]{" North America", " Africa", " Europe", " Australia"}, 3);
        Question hardQ3 = new Question("What is the name of the deepest point in the ocean?", new String[]{" Challenger Deep", " Mariana Trench", " Puerto Rico Trench", " Tonga Trench"}, 0);
        Question hardQ4 = new Question("What is the minimum number of queens that must be placed on a chessboard, such that no two queens attack each other?", new String[]{" 4", " 6", " 8", " 10"}, 2);
        Question hardQ5 = new Question("What is the rarest blood type in humans?", new String[]{" AB-", " AB+", " O-", " A+"}, 0);
        Question hardQ6 = new Question("What is the capital city of Turkmenistan?", new String[]{" Tashkent", " Dushanbe", " Ashgabat", " Bishkek"}, 2);
        Question hardQ7 = new Question("What is the name of the highest mountain peak in Africa?", new String[]{" Mount Kilimanjaro", " Mount Everest", " Mount Aconcagua", " Mount McKinley"}, 0);
        Question hardQ8 = new Question("Which country has the most time zones?", new String[]{" Russia", " China", " Canada", " United States"}, 0);
        Question hardQ9 = new Question("What is the name of the largest moon in our solar system?", new String[]{" Callisto", " Titan", " Ganymede", " Io"}, 2);
        Question hardQ10 = new Question("What is the significance of the number 1729 in mathematics?", new String[]{" It is a Mersenne prime", " It is a Fermat prime", " It is a Carmichael number", " It is the Hardy-Ramanujan number"}, 3);
        Question hardQ11 = new Question("What is the smallest country in the world by land area?", new String[]{" Monaco", " San Marino", " Vatican City", " Liechtenstein"}, 2);
        Question hardQ12 = new Question("What is the smallest organ in the human body?", new String[]{" Pineal Gland", " Parathyroid Glands", " Pituitary Gland", " Thumus Gland"}, 0);
        Question hardQ13 = new Question("What is the name of the currency used in Thailand?", new String[]{" Baht", " Rupee", " Yen", " Won"}, 0);
        Question hardQ14 = new Question("What is the largest desert in the world?", new String[]{" Gobi Desert", " Sahara Desert", " Arabian Desert", " Kalahari Desert"}, 1);
        Question hardQ15 = new Question("What is the name of the phenomenon where the moon appears larger and brighter than usual?", new String[]{" Supermoon", " Blue Moon", " Blood Moon", " Harvest Moon"}, 0);

        // Add easy questions to Easy list
        easy.add(easyQ1);
        easy.add(easyQ2);
        easy.add(easyQ3);
        easy.add(easyQ4);
        easy.add(easyQ5);
        easy.add(easyQ6);
        easy.add(easyQ7);
        easy.add(easyQ8);
        easy.add(easyQ9);
        easy.add(easyQ10);
        easy.add(easyQ11);
        easy.add(easyQ12);
        easy.add(easyQ13);
        easy.add(easyQ14);
        easy.add(easyQ15);

        // Add hard questions to Hard list
        hard.add(hardQ1);
        hard.add(hardQ2);
        hard.add(hardQ3);
        hard.add(hardQ4);
        hard.add(hardQ5);
        hard.add(hardQ6);
        hard.add(hardQ7);
        hard.add(hardQ8);
        hard.add(hardQ9);
        hard.add(hardQ10);
        hard.add(hardQ11);
        hard.add(hardQ12);
        hard.add(hardQ13);
        hard.add(hardQ14);
        hard.add(hardQ15);

        // add the easy and hard question lists to one list that is returned
        ArrayList questionList = new ArrayList<>();
        questionList.add(easy);
        questionList.add(hard);

        return questionList;
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
