package WhoWantsToBeAMillionaire;

import java.util.Arrays;
import java.util.Random;

public class AskTheAudience extends LifeLines {

    private Random rand;

    public int askTheAudience(Question question) {
        // Create an array with 4 elements
        int[] arr = new int[4];

        // Choose the index of the element with the highest value
        int highestIndex = 1;

        // Set the values of the other elements to distribute the remaining sum of 100
        int remainingSum = 100 - arr[highestIndex];
        for (int i = 0; i < arr.length; i++) {
            if (i != highestIndex) {
                arr[i] = remainingSum / 3;
            }
        }

        // Set the value of the element at the chosen index to the remaining sum
        arr[highestIndex] = remainingSum - (arr[0] + arr[2] + arr[3]);

        // Output the resulting array
        System.out.println("Original Array: " + Arrays.toString(arr));

        // Choose a new index for the highest value
        int newHighestIndex = 3;

        // Swap the values at the two indices
        int temp = arr[highestIndex];
        arr[highestIndex] = arr[newHighestIndex];
        arr[newHighestIndex] = temp;

        // Calculate the sum of the array elements
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        // Output the resulting array with the new highest value index and percentage bar
        System.out.println("New Array with Highest Value at Index " + newHighestIndex + ": " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            double percentage = (double)arr[i] / sum * 100;
            int barLength = (int) Math.round(percentage / 2);
            for (int j = 0; j < barLength; j++) {
                System.out.print("=");
            }
            System.out.println(" " + String.format("%.2f", percentage) + "%");
        }
        
        super.isUsed();

        return -1;
    }
}
