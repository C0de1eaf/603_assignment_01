package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;

public class LeaderboardGUI extends JPanel {

    private JButton returnButton;

    public LeaderboardGUI(CardLayout cardLayout, JPanel cards) {
        setLayout(new BorderLayout());

        // Create a return button
        returnButton = new JButton("Return");

        // Add ActionListener to return button
        returnButton.addActionListener(e -> {
            cardLayout.show(cards, "menuGUI");
        });

        // Add return button to the LeaderboardGUI panel
        add(returnButton, BorderLayout.NORTH);
    }
}
