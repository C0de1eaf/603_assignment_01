package MillionaireGUI;

import WhoWantsToBeAMillionaire.Question;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author yutas
 */
public interface SystemInit {
    void createGUI(CardLayout cardLayout, JPanel cards);
    List<JButton> createButtons(String[] buttonTexts, Dimension buttonSize);
    JPanel createInputPanel();
    DocumentFilter createDocumentFilter();
    Question getRandomQuestion();
    void updateLeaderboardData() throws IOException;
    void storeUserScoreAndUpdateLeaderboard(int lowestValue) throws IOException;
}
