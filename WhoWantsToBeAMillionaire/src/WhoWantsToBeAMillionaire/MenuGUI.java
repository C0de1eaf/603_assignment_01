package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;

public class MenuGUI extends JPanel {

    // Buttons
    private JButton playButton;
    private JButton leaderboardButton;
    private JButton exitButton;

    // CardLayout and Jpanel
    private CardLayout cardLayout;
    private JPanel cards;

    // Button Dimensions
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

        // Add buttons to the button panel with spacing
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());
        
        // Add ActionListener to play button
        playButton.addActionListener(e -> {
            cardLayout.show(cards, "gameGUI");
        });

        leaderboardButton.addActionListener(e -> {
            cardLayout.show(cards, "leaderboardGUI");
        });
        
        // Add ActionListener to exit button
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        
        // Add the button panel to MenuGUI
        add(buttonPanel, BorderLayout.CENTER);

        // Set MenuGUI visibility
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false); // Remove button focus border
        return button;
    }
}
