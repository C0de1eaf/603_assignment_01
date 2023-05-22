package WhoWantsToBeAMillionaire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionList {

    /// declare two ArrayLists to hold easy and hard questions
    private final ArrayList<Question> easy;
    private final ArrayList<Question> hard;

    // Constructor initializes the ArrayLists
    public QuestionList() {
        this.hard = new ArrayList();
        this.easy = new ArrayList();
    }

    // Method to create the list of questions
    public List<ArrayList<Question>> createQuestionList() {

        // create an ArrayList of easy Question objects
        // converts array of Question instances to a List<Question> and assigns this list to easyQuestions
        List<Question> easyQuestions = Arrays.asList(
                new Question("What is the largest organ in the human body?", new String[]{"Brain", "Skin", "Liver", "Heart"}, 1),
                new Question("What is the tallest mountain in the world?", new String[]{"Mount Everest", "Mount Kilimanjaro", "Mount McKinley", "Mount Rainier"}, 0),
                new Question("What is the smallest planet in our solar system?", new String[]{" Venus", " Mars", " Mercury", " Jupiter"}, 2),
                new Question("What is the chemical symbol for gold?", new String[]{" Ag", " Cu", " Au", " Fe"}, 2),
                new Question("What is the capital city of Australia?", new String[]{" Sydney", " Melbourne", " Canberra", " Perth"}, 2),
                new Question("What is the smallest country in the world?", new String[]{" Monaco", " Vatican City", " San Marino", " Liechtenstein"}, 1),
                new Question("What is the most common gas found in the Earth's atmosphere?", new String[]{" Oxygen", " Nitrogen", " Carbon Dioxide", " Helium"}, 1),
                new Question("What is the atomic number of the element Oxygen?", new String[]{" 6", " 8", " 10", " 12"}, 1),
                new Question("What is the highest waterfall in the world?", new String[]{" Angel Falls", " Victoria Falls", " Niagara Falls", " Iguazu Falls"}, 0),
                new Question("What is the name of the longest river in Africa?", new String[]{" Congo River", " Nile River", " Zambezi River", " Niger River"}, 1),
                new Question("What is the name of the largest ocean in the world?", new String[]{" Atlantic Ocean", " Indian Ocean", " Arctic Ocean", " Pacific Ocean"}, 3),
                new Question("What is the study of plants called?", new String[]{" Zoology", " Botany", " Geology", " Anthropology"}, 1),
                new Question("Which element is represented by the symbol 'He'?", new String[]{" Helium", " Hydrogen", " Carbon", " Oxygen"}, 0),
                new Question("Which country is home to the tallest mountain in the world?", new String[]{" United States", " Nepal", " China", " Russia"}, 1),
                new Question("Which U.S. state is known as the \"Aloha State\"?", new String[]{"California", "Texas", "Hawaii", "Florida"}, 2)
        );

        // Adds all the easy questions to easy ArrayList variable using addAll() method
        easy.addAll(easyQuestions);
        
        // create an ArrayList of hard Question objects
        // converts array of Question instances to a List<Question> and assigns this list to hardQuestions
        List<Question> hardQuestions = Arrays.asList(
                new Question("What is the chemical formula for benzene?", new String[]{"C5H5", "C6H5OH", "C6H6", "C6H12O6"}, 2),
                new Question("What is the only continent that has no active volcanoes?", new String[]{"North America", "Africa", "Europe", "Australia"}, 3),
                new Question("What is the name of the deepest point in the ocean?", new String[]{" Challenger Deep", " Mariana Trench", " Puerto Rico Trench", " Tonga Trench"}, 0),
                new Question("What is the minimum number of queens that must be placed on a chessboard, such that no two queens attack each other?", new String[]{" 4", " 6", " 8", " 10"}, 2),
                new Question("What is the rarest blood type in humans?", new String[]{" AB-", " AB+", " O-", " A+"}, 0),
                new Question("What is the capital city of Turkmenistan?", new String[]{" Tashkent", " Dushanbe", " Ashgabat", " Bishkek"}, 2),
                new Question("What is the name of the highest mountain peak in Africa?", new String[]{" Mount Kilimanjaro", " Mount Everest", " Mount Aconcagua", " Mount McKinley"}, 0),
                new Question("Which country has the most time zones?", new String[]{" Russia", " China", " Canada", " United States"}, 0),
                new Question("What is the name of the largest moon in our solar system?", new String[]{" Callisto", " Titan", " Ganymede", " Io"}, 2),
                new Question("What is the significance of the number 1729 in mathematics?", new String[]{" It is a Mersenne prime", " It is a Fermat prime", " It is a Carmichael number", " It is the Hardy-Ramanujan number"}, 3),
                new Question("What is the smallest country in the world by land area?", new String[]{" Monaco", " San Marino", " Vatican City", " Liechtenstein"}, 2),
                new Question("What is the smallest organ in the human body?", new String[]{" Pineal Gland", " Parathyroid Glands", " Pituitary Gland", " Thumus Gland"}, 0),
                new Question("What is the name of the currency used in Thailand?", new String[]{" Baht", " Rupee", " Yen", " Won"}, 0),
                new Question("What is the largest desert in the world?", new String[]{" Gobi Desert", " Sahara Desert", " Arabian Desert", " Kalahari Desert"}, 1),
                new Question("What is the name of the phenomenon where the moon appears larger and brighter than usual?", new String[]{" Supermoon", " Blue Moon", " Blood Moon", " Harvest Moon"}, 0)
        );

        // Adds all the hard questions to hard ArrayList variable using addAll() method
        hard.addAll(hardQuestions);

        // Returns the List of ArraysLists
        return Arrays.asList(easy, hard);
    }
    
    public List<ArrayList<Question>> getEasyQuestions(){
        return Arrays.asList(easy);
    }
    
    public List<ArrayList<Question>> getHardQuestions(){
         return Arrays.asList(hard);
    } 
}
