package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;

import javax.imageio.ImageIO;

public class LeaderboardGUI extends JPanel {

    private JButton returnButton;

    public LeaderboardGUI(CardLayout cardLayout, JPanel cards) {
        // Set the layout to FlowLayout with left alignment
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create a return button
        returnButton = new JButton();
        returnButton.setPreferredSize(new Dimension(100, 100));

        // Set the icon for the return button
        try {
            Image img = ImageIO.read(getClass().getResource("resources/return.png"));
            returnButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        // Remove spacing between the image and button's borders
        returnButton.setMargin(new Insets(0, 0, 0, 0));

        // Remove border
        returnButton.setBorderPainted(false);

        // Add ActionListener to return button
        returnButton.addActionListener(e -> {
            cardLayout.show(cards, "menuGUI");
        });

        // Add the return button to the LeaderboardGUI panel
        add(returnButton);
    }
}