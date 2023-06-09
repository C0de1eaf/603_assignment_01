package LifeLine;

import WhoWantsToBeAMillionaire.Question;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JPanel;

public final class PhoneAFriendGUI extends JPanel implements LifeLineInterface {

    private final ArrayList<String> friends;
    private final FriendList friendList;
    private final Random rand;
    private final StringBuilder message;
    private boolean isUsed;

    public PhoneAFriendGUI(String[] answers, int correctAnswerIndex, String question) {
        this.friendList = new FriendList();
        this.friends = this.friendList.createFriendList();
        this.rand = new Random();
        this.message = new StringBuilder();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Increase the font size
        Font font = new Font("Arial", Font.BOLD, 28);
        g2d.setFont(font);

        String[] lines = message.toString().split("\n");
        int lineHeight = g2d.getFontMetrics().getHeight();
        int y = lineHeight;

        for (String line : lines) {
            // Calculate the x-coordinate for center alignment
            int lineWidth = g2d.getFontMetrics().stringWidth(line);
            int x = (getWidth() - lineWidth) / 2;

            g2d.drawString(line, x, y);
            y += lineHeight;
        }
    }

    public void phoneAFriend(Question question) {
        int answerIndex = question.getCorrectAnswerIndex(); //Get the correct answer
        String friendToCall = friends.get(rand.nextInt(friends.size())); //Get a random friend from the list of friends

        appendMessage("Hmm, who should I call... I'll call " + friendToCall);
        appendMessage("Ringing....");
        appendMessage(friendToCall + ": Hey, what's up?");
        appendMessage("I need some help with this question, what do you think?");
        appendMessage(question.getQuestion());

        int[] weights = new int[4]; //Weights to distribute across the random number generation
        for (int i = 0; i < 4; i++) {
            if (i == answerIndex) { //Make sure the correct answer is weighted higher
                weights[i] = 10;
            } else {
                weights[i] = 1;
            }
        }

        int totalWeight = Arrays.stream(weights).sum(); //Sum all the weights of the friends weights using stream and .sum()
        int randomWeight = rand.nextInt(totalWeight);  //Select a random number 

        //Iterate and choose a random number
        int cumulativeWeight = 0;
        for (int i = 0; i < 4; i++) {
            cumulativeWeight += weights[i]; //Run the loop until the cumulative weight > the random number
            if (randomWeight < cumulativeWeight) {
                appendMessage(friendToCall + ": I think it's" + question.getAnswers()[question.getCorrectAnswerIndex()]); //Friends answer
                break;
            }
        }

    }

    private void appendMessage(String message) {
        this.message.append(message).append("\n");
        repaint();
    }

    @Override
    public boolean isUsed() {
        return this.isUsed;
    }

    @Override
    public void lifeLineUsed() {
        this.isUsed = true;
    }

    @Override
    public void resetLifeLine() {
        this.isUsed = false;
    }

}
