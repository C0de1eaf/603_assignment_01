package WhoWantsToBeAMillionaire;

import java.util.ArrayList;

/**
 *
 * @author yutas
 */
public class QuestionList {

    private final ArrayList<Question> easy;
    private final ArrayList<Question> hard;

    public QuestionList() {
        this.hard = new ArrayList();
        this.easy = new ArrayList();
    }

    public ArrayList questionsCreation() {

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
}
