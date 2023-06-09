package LifeLine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public abstract class AskTheAudienceGUI extends JPanel implements LifeLineInterface {

    private static final Color BACKGROUND_COLOR = Color.white;
    private static final Color[] BAR_COLORS = {Color.red, Color.blue, Color.green, Color.orange};
    private final String[] barTitles;
    private final int correctAnswerIndex;

    private boolean isUsed = false;

    public AskTheAudienceGUI(final String[] barTitles, int correctAnswerIndex) {
        this.barTitles = barTitles;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        drawBars(g);
    }

    private void drawBars(final Graphics g) {
        int OUTER_MARGIN = 20,
                WIDTH = 400 + 2 * OUTER_MARGIN,
                HEIGHT = 300 + 2 * OUTER_MARGIN;

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        final int barHeight = 20;
        for (int i = 0; i < barTitles.length; i++) {
            g.setColor(BAR_COLORS[i]);
            final int y = OUTER_MARGIN + 50 * i;
            final int barWidth = 5 * barTitles[i].indexOf(i);
            final int x = OUTER_MARGIN;
            g.fillRect(x, y, barWidth, barHeight);
            g.drawString((i + 1) + ") " + barTitles[i] + "%", x + barWidth + 5, y + barHeight - 5);
        }
    }

    public int[] generateRandomPercentages(int correctAnswerIndex) {
        Random rand = new Random();

        int rand1 = rand.nextInt(26) + 10;
        int rand2 = rand.nextInt(26) + 10;

        int rand3 = (rand1 + rand2) > 65 ? rand.nextInt(20) + 10 : rand.nextInt(26) + 10;

        int sum = rand1 + rand2 + rand3;
        int rand4 = 100 - sum;

        int[] arr = {rand1, rand2, rand3, rand4};

        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }

        if (maxIndex != correctAnswerIndex) {
            int temp = arr[correctAnswerIndex];
            arr[correctAnswerIndex] = arr[maxIndex];
            arr[maxIndex] = temp;
        }

        return arr;
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
