/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LifeLine;

import WhoWantsToBeAMillionaire.Question;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yutas
 */
public class FiftyFiftyTest {
    
    @Test
    public void testFiftyFifty() {
        //Create a question
        String[] answerOptions = {"London", "Paris", "Madrid", "Berlin"};
        int correctAnswerIndex = 1; // Paris is the correct answer
        Question question = new Question("What is the capital of France?", answerOptions, correctAnswerIndex);

        FiftyFifty fiftyFifty = new FiftyFifty();
        Question modifiedQuestion = fiftyFifty.fiftyFifty(question);

        //Test that the answer still exists
        assertEquals("Paris", modifiedQuestion.getAnswers()[correctAnswerIndex]);

        //Test that the 2 questions which still exist also existed in the old question
        assertTrue(Arrays.asList(answerOptions).contains(modifiedQuestion.getAnswers()[0]));
        assertTrue(Arrays.asList(answerOptions).contains(modifiedQuestion.getAnswers()[1]));
    }

    @Test
    public void testFiftyFiftyLifelineUsage() {
        // Create a question
        String[] answerOptions = {"London", "Paris", "Madrid", "Berlin"};
        int correctAnswerIndex = 1; // Paris is the correct answer
        Question question = new Question("What is the capital of France?", answerOptions, correctAnswerIndex);

        FiftyFifty fiftyFifty = new FiftyFifty();
        fiftyFifty.fiftyFifty(question);

        // Verify that the lifeline is used proper;y
        assertEquals(true, fiftyFifty.isUsed());
    }
}
