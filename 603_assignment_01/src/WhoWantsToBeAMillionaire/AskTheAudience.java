package WhoWantsToBeAMillionaire;

import java.util.Arrays;
import java.util.Random;

public class AskTheAudience extends LifeLines {

    private Random rand;

    public void askTheAudience(Question question) {
        // Create an array with 4 elements
        int[] arr = new int[4];

        // Choose the index of the element with the highest value
        int highestValueIndex = question.getCorrectAnswerIndex();

        // Generate 3 random integers between 1 and 25
        int rand1 = (int) (Math.random() * 25) + 1;
        int rand2 = (int) (Math.random() * 25) + 1;
        int rand3 = (int) (Math.random() * 25) + 1;

        // Calculate the value of the fourth element to ensure the sum is 100
        int sum = rand1 + rand2 + rand3;
        int rand4 = 100 - sum;

        // Set the values of the array elements
        arr[0] = rand1;
        arr[1] = rand2;
        arr[2] = rand3;
        arr[3] = rand4;

        // Set the highest value to be at the highestValueIndex
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        if (maxIndex != highestValueIndex) {
            int temp = arr[highestValueIndex];
            arr[highestValueIndex] = arr[maxIndex];
            arr[maxIndex] = temp;
        }

        super.isUsed();
    }
}
