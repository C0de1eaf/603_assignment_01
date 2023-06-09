package LifeLine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public final class AskTheAudienceGUI extends JPanel implements LifeLineInterface {

    private final String[] barString;
    private final int[] percentages;
    private final String questionName;
    private boolean isUsed = false;

    public AskTheAudienceGUI(final String[] barString, final int correctAnswerIndex, final String questionName) {
        this.questionName = questionName;
        this.barString = barString;
        this.percentages = generateRandomPercentages(correctAnswerIndex);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        drawTitle(g);
        drawBars(g);
    }

    private void drawTitle(final Graphics g) {
        String question = "The audience have voted for: " + questionName;

        Font newFont = new Font("Arial", Font.BOLD, 28);
        g.setFont(newFont);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int titleWidth = g.getFontMetrics().stringWidth(question);
        int titleX = (panelWidth - titleWidth) / 2;
        int titleY = panelHeight / 5;

        g.setColor(Color.BLACK);
        g.drawString(question, titleX, titleY);
    }

    private static final Color[] BAR_COLORS = {
        new Color(152, 204, 152),
        new Color(102, 204, 102),
        new Color(51, 153, 51),
        new Color(0, 102, 0)
    };

    private void drawBars(final Graphics g) {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2;
        setOpaque(false);

        for (int i = 0; i < barString.length; i++) {
            g.setColor(BAR_COLORS[i]); // Change this line
            final int barWidth = 200;
            final int gap = 70;
            final int x = centerX - (barWidth * barString.length + gap * (barString.length - 1)) / 2 + (barWidth + gap) * i;
            final int barHeight = 4 * percentages[i];

            Font currentFont = g.getFont();
            Font newFont = currentFont.deriveFont(20f);
            g.setFont(newFont);

            drawCenteredString(g, barString[i] + ": " + percentages[i] + "%", x, panelHeight - barHeight - 25, barWidth, newFont);

            g.fillRect(x, panelHeight - barHeight, barWidth, barHeight);
        }
    }

    public void drawCenteredString(Graphics g, String text, int x, int y, int width, Font font) {

        FontMetrics metrics = g.getFontMetrics(font);
        int xPos = x + (width - metrics.stringWidth(text)) / 2;
        g.setFont(font);
        g.drawString(text, xPos, y);
    }

    public int[] generateRandomPercentages(int correctAnswerIndex) {
        Random rand = new Random();

        int rand1 = rand.nextInt(26) + 15;
        int rand2 = rand.nextInt(Math.min(26, 100 - rand1 - 10)) + 15;
        int rand3 = rand.nextInt(Math.min(26, 100 - rand1 - rand2 - 10)) + 10;

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
