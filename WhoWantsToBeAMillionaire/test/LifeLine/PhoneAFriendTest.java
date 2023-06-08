/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LifeLine;

import WhoWantsToBeAMillionaire.Question;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yutas
 */
public class PhoneAFriendTest {

    @Test
    public void testPhoneAFriend() {
        // Create a question
        String[] answerOptions = {"London", "Paris", "Madrid", "Berlin"};
        int correctAnswerIndex = 1; // Paris is the correct answer
        Question question = new Question("What is the capital of France?", answerOptions, correctAnswerIndex);

        PhoneAFriend PaF = new PhoneAFriend();
        PaF.phoneAFriend(question);

        assertEquals(true, PaF.isUsed());
    }
}
