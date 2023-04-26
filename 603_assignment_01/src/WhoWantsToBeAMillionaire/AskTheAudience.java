package WhoWantsToBeAMillionaire;

import java.util.Arrays;
import java.util.Random;

public class AskTheAudience extends LifeLines {

    public void askTheAudience(Question question) {
        Random rand = new Random();

        // Create an array with 4 elements
        int[] arr = new int[4];

        // Choose the index of the element with the highest value
        int highestValueIndex = question.getCorrectAnswerIndex();

        // Generate 3 random integers between 1 and 25
        int rand1 = rand.nextInt(26) + 10;
        int rand2 = rand.nextInt(26) + 10;
        int rand3 = rand.nextInt(21) + 15;

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

        printPercentages(arr);

        super.lifeLineUsed();
    }

    public void printPercentages(int[] array) {
        // Calculate the total sum of the array elements
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }

        // Calculate the percentage of each element and print a percentage bar
        System.out.println("Audience poll:");
        for (int i = 0; i < array.length; i++) {
            int percent = (int) Math.round((double) array[i] / sum * 100);
            System.out.print((i + 1) + ") ");
            for (int j = 0; j < percent; j++) {
                System.out.print("=");
            }

            System.out.println(" " + percent + "%");
        }
    }
}
