package MillionaireGUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuGUI extends JPanel {

    // Buttons
    private JButton playButton;
    private JButton leaderboardButton;
    private JButton exitButton;

    // CardLayout and JPanel
    private CardLayout cardLayout;
    private JPanel cards;

    public MenuGUI(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;

        // Create the buttons
        playButton = createButton("Play");
        leaderboardButton = createButton("Leaderboard");
        exitButton = createButton("Exit");

        Font menuButtonFont = new Font("Arial", Font.BOLD, 26);
        playButton.setFont(menuButtonFont);
        leaderboardButton.setFont(menuButtonFont);
        exitButton.setFont(menuButtonFont);

        // Set the preferred size for the buttons
        Dimension buttonSize = new Dimension(200, 50);
        playButton.setPreferredSize(buttonSize);
        leaderboardButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        // Set the layout manager for MenuGUI
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        add(playButton, gbc);
        gbc.gridy++;
        add(leaderboardButton, gbc);
        gbc.gridy++;
        add(exitButton, gbc);

        addMouseListenerToButton(playButton, Color.decode("#FFD700"), Color.decode("#FFA500"));
        addMouseListenerToButton(leaderboardButton, Color.decode("#FFD700"), Color.decode("#FFA500"));
        addMouseListenerToButton(exitButton, Color.decode("#FFD700"), Color.decode("#FFA500"));

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

        // Set MenuGUI visibility
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false); // Remove button focus border
        return button;
    }

    public void addMouseListenerToButton(JButton button, Color defaultColor, Color hoverColor) {
        button.setBackground(new Color(255, 215, 0));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(defaultColor);
            }
        });
    }
}
