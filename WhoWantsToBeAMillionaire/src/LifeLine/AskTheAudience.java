package LifeLine;

import WhoWantsToBeAMillionaire.Question;
import java.util.Random;

public class AskTheAudience extends LifeLines {

    // Method to ask the audience for their opinion
    public void askTheAudience(Question question) {

        // Create a new Random object
        Random rand = new Random();

        int rand1, rand2, rand3;

        // Get the index of the correct answer for the question
        int highestValueIndex = question.getCorrectAnswerIndex();

        // Generate 3 random integers between 10 and 35 (inclusive)
        rand1 = rand.nextInt(26) + 10;
        rand2 = rand.nextInt(26) + 10;

        // Ensure that the sum of rand1 and rand2 is not greater than 65
        if ((rand1 + rand2) > 65) {
            // If it is, generate a random integer between 10 and 29 (inclusive) for rand3
            rand3 = rand.nextInt(20) + 10;
        } else {
            // Otherwise, generate a random integer between 10 and 35 (inclusive) for rand3
            rand3 = rand.nextInt(26) + 10;
        }

        // Calculate the value of the fourth element to ensure the sum is 100
        int sum = rand1 + rand2 + rand3;
        int rand4 = 100 - sum;

        // Create an array with 4 elements to store audience votes
        // Set the values of the array elements
        int[] arr = {rand1, rand2, rand3, rand4};

        // Find the index of the maximum value in the array
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }

        // Swap the values in the array to ensure the highest value is at the highestValueIndex
        if (maxIndex != highestValueIndex) {
            int temp = arr[highestValueIndex];
            arr[highestValueIndex] = arr[maxIndex];
            arr[maxIndex] = temp;
        }

        // Call the printPercentages method to display the audience's vote percentages
        printPercentages(arr);

        // Call the lifeLineUsed method from the parent class to mark the lifeline as used
        super.lifeLineUsed();
    }

    // Method to print the audience's vote percentages
    public void printPercentages(int[] array) {
        // Calculate the total sum of the array elements
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }

        // Print the percentage bar for each array element
        System.out.println("Audience poll:");
        for (int i = 0; i < array.length; i++) {
            // Calculate the percentage of the array element
            int percent = (int) Math.round((double) array[i] / sum * 100);

            // Print the percentage bar with the percentage value
            System.out.print((i + 1) + ") ");
            for (int j = 0; j < percent; j++) {
                System.out.print("=");
            }
            System.out.println(" " + percent + "%");
        }
    }
}
