/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LifeLine;

import WhoWantsToBeAMillionaire.Question;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yutas
 */
public class AskTheAudienceTest {

    //Verifies the use of the Ask the audience method.
    @Test
    public void testAskTheAudience() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        //Create a question to use for the test, with a known answer
        String[] answerOptions = {"London", "Paris", "Madrid", "Berlin"};
        int correctAnswerIndex = 1; // Paris is the correct answer
        Question question = new Question("What is the capital of France?", answerOptions, correctAnswerIndex);

        //Create a new ask the audience object and run the question through the object's ATA method
        AskTheAudience AtA = new AskTheAudience();
        AtA.askTheAudience(question);

        //Get the ask the audience class's random values,
        Field rand1Field = AtA.getClass().getDeclaredField("rand1");
        Field rand2Field = AtA.getClass().getDeclaredField("rand2");
        Field rand3Field = AtA.getClass().getDeclaredField("rand3");

        //Make these values returned accesible to use for testing
        rand1Field.setAccessible(true);
        rand2Field.setAccessible(true);
        rand3Field.setAccessible(true);

        //Get the integer values of each random field
        int rand1 = rand1Field.getInt(AtA);
        int rand2 = rand2Field.getInt(AtA);
        int rand3 = rand3Field.getInt(AtA);

        //Ensure each random value is within the desired value set in the class
        assertTrue(rand1 >= 10 && rand1 <= 35);
        assertTrue(rand2 >= 10 && rand2 <= 35);
        assertTrue(rand3 >= 10 && rand3 <= 35);

        //Ensure the random values do not exceed 65
        assertTrue(rand1 + rand2 + rand3 <= 65);

        //Ensure in any case the random values added exceed 65, that rand3 is in the range of 10 and 29.
        if (rand1 + rand2 + rand3 > 65) {
            assertTrue(rand3 >= 10 && rand3 <= 29);
        }
    }

    @Test
    public void testAskTheAudienceLifelineUsage() {
        // Create a question
        String[] answerOptions = {"London", "Paris", "Madrid", "Berlin"};
        int correctAnswerIndex = 1; // Paris is the correct answer
        Question question = new Question("What is the capital of France?", answerOptions, correctAnswerIndex);

        AskTheAudience AtA = new AskTheAudience();
        AtA.askTheAudience(question);

        // Verify that the lifeline is used proper;y
        assertEquals(true, AtA.isUsed());
    }
}
