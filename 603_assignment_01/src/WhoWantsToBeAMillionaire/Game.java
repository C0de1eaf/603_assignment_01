package WhoWantsToBeAMillionaire;

import java.util.*;

public class Game {

    public ArrayList<ArrayList> questions;

    private int currentLevel;
    private final Random rand;
    private int prizeMoney;

    public Game() {
        // create question list
        questions = questionsCreation();
        currentLevel = 1;

        rand = new Random();
    }

    public void runGame() {
        int totalQuestions = 10;
        int currentQuestion = 1;
        prizeMoney = 0;
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < totalQuestions; i++) {
            Questions current = getRandomQuestion();
            current.printQuestion();
            int levelAnswer = current.getCorrectAnswerIndex();

            int userAnswer = 0;
            boolean validity = false;

            while (!validity) {
                System.out.print("Please enter a number between 1 and 4: ");
                String input = scan.next();

                try {
                    userAnswer = Integer.parseInt(input);
                    if (userAnswer < 1 || userAnswer > 4) {
                        System.out.println("Invalid input. Please try again.");
                        continue;
                    }
                    validity = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }

            if (userAnswer == levelAnswer + 1) {
                System.out.println("Correct!");
            } else {
                System.out.println("Sorry but you are wrong and it is game over!\n"
                        + "You lose your cash prize of [" + prizeMoney + "]");
                break;
            }
            currentQuestion++;
        }
    }

    public Questions getRandomQuestion() {
        int questionNumber = rand.nextInt(questions.get(currentLevel).size());
        Questions selectedQuestion = (Questions) questions.get(currentLevel).get(questionNumber);
        questions.remove(selectedQuestion);
        return selectedQuestion;
    }

    public ArrayList questionsCreation() {
        ArrayList<Questions> easy = new ArrayList();
        ArrayList<Questions> hard = new ArrayList();

        // Create easy questions
        Questions easyQ1 = new Questions(1, "What is the largest organ in the human body?", new String[]{" Brain", " Skin", " Liver", " Heart"}, 1);
        Questions easyQ2 = new Questions(1, "What is the tallest mountain in the world?", new String[]{" Mount Everest", " Mount Kilimanjaro", " Mount McKinley", " Mount Rainier"}, 0);
        Questions easyQ3 = new Questions(1, "What is the smallest planet in our solar system?", new String[]{" Venus", " Mars", " Mercury", " Jupiter"}, 2);
        Questions easyQ4 = new Questions(1, "What is the chemical symbol for gold?", new String[]{" Ag", " Cu", " Au", " Fe"}, 2);
        Questions easyQ5 = new Questions(1, "What is the capital city of Australia?", new String[]{" Sydney", " Melbourne", " Canberra", " Perth"}, 2);
        Questions easyQ6 = new Questions(1, "What is the smallest country in the world?", new String[]{" Monaco", " Vatican City", " San Marino", " Liechtenstein"}, 1);
        Questions easyQ7 = new Questions(1, "What is the most common gas found in the Earth's atmosphere?", new String[]{" Oxygen", " Nitrogen", " Carbon Dioxide", " Helium"}, 1);
        Questions easyQ8 = new Questions(1, "What is the atomic number of the element Oxygen?", new String[]{" 6", " 8", " 10", " 12"}, 1);
        Questions easyQ9 = new Questions(1, "What is the highest waterfall in the world?", new String[]{" Angel Falls", " Victoria Falls", " Niagara Falls", " Iguazu Falls"}, 0);
        Questions easyQ10 = new Questions(1, "What is the name of the longest river in Africa?", new String[]{" Congo River", " Nile River", " Zambezi River", " Niger River"}, 1);
        Questions easyQ11 = new Questions(1, "What is the name of the largest ocean in the world?", new String[]{" Atlantic Ocean", " Indian Ocean", " Arctic Ocean", " Pacific Ocean"}, 3);
        Questions easyQ12 = new Questions(1, "What is the study of plants called?", new String[]{" Zoology", " Botany", " Geology", " Anthropology"}, 1);
        Questions easyQ13 = new Questions(1, "Which element is represented by the symbol 'He'?", new String[]{" Helium", " Hydrogen", " Carbon", " Oxygen"}, 0);
        Questions easyQ14 = new Questions(1, "Which country is home to the tallest mountain in the world?", new String[]{" United States", " Nepal", " China", " Russia"}, 1);
        Questions easyQ15 = new Questions(1, "Which U.S. state is known as the \"Aloha State\"?", new String[]{"California", "Texas", "Hawaii", "Florida"}, 2);

        // Create hard questions
        Questions hardQ1 = new Questions(2, "What is the chemical formula for benzene?", new String[]{" C5H5", " C6H5OH", " C6H6", " C6H12O6"}, 2);
        Questions hardQ2 = new Questions(2, "What is the only continent that has no active volcanoes?", new String[]{" North America", " Africa", " Europe", " Australia"}, 3);
        Questions hardQ3 = new Questions(2, "What is the name of the deepest point in the ocean?", new String[]{" Challenger Deep", " Mariana Trench", " Puerto Rico Trench", " Tonga Trench"}, 0);
        Questions hardQ4 = new Questions(2, "What is the minimum number of queens that must be placed on a chessboard, such that no two queens attack each other?", new String[]{" 4", " 6", " 8", " 10"}, 2);
        Questions hardQ5 = new Questions(2, "What is the rarest blood type in humans?", new String[]{" AB-", " AB+", " O-", " A+"}, 0);
        Questions hardQ6 = new Questions(2, "What is the capital city of Turkmenistan?", new String[]{" Tashkent", " Dushanbe", " Ashgabat", " Bishkek"}, 2);
        Questions hardQ7 = new Questions(2, "What is the name of the highest mountain peak in Africa?", new String[]{" Mount Kilimanjaro", " Mount Everest", " Mount Aconcagua", " Mount McKinley"}, 0);
        Questions hardQ8 = new Questions(2, "Which country has the most time zones?", new String[]{" Russia", " China", " Canada", " United States"}, 0);
        Questions hardQ9 = new Questions(2, "What is the name of the largest moon in our solar system?", new String[]{" Callisto", " Titan", " Ganymede", " Io"}, 2);
        Questions hardQ10 = new Questions(2, "What is the significance of the number 1729 in mathematics?", new String[]{" It is a Mersenne prime", " It is a Fermat prime", " It is a Carmichael number", " It is the Hardy-Ramanujan number"}, 3);

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

    public int getPrizeMoney() {
        return this.prizeMoney;
    }
}
