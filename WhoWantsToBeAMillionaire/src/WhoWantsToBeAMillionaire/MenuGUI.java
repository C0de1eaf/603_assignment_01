package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;

public class MenuGUI extends JPanel {

    private JButton playButton;
    private JButton leaderboardButton;
    private JButton exitButton;
    private CardLayout cardLayout;
    private JPanel cards;
    private int buttonWidth = 150;
    private int buttonHeight = 50;

    public MenuGUI(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;
        // Create a panel to hold the buttons with vertical layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Create the buttons
        playButton = createButton("Play");
        leaderboardButton = createButton("Leaderboard");
        exitButton = createButton("Exit");

        exitButton.addActionListener(e -> System.exit(0));

        // Set the layout manager for MenuGUI
        setLayout(new BorderLayout());

        // Set the size and alignment for the buttons
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        playButton.setMaximumSize(buttonSize);
        leaderboardButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create a panel to hold the buttons with vertical layout
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Add vertical spacing between buttons
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Adjust the spacing as needed
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Adjust the spacing as needed
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());
        playButton.addActionListener(e -> {
            cardLayout.show(cards, "gameGUI");
        });
        add(buttonPanel, BorderLayout.CENTER);

        // Show the GUI
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false); // Remove button focus border
        return button;
    }
}
