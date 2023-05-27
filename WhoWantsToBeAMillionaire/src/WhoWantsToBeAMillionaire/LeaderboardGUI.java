package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class LeaderboardGUI extends JPanel {

    private JButton returnButton;

    public LeaderboardGUI(CardLayout cardLayout, JPanel cards) {
        setLayout(new BorderLayout());

        // Create a return button
        returnButton = new JButton("Return");

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

        // Create a panel with a fixed size
        JPanel fixedSizePanel = new JPanel();
        fixedSizePanel.setPreferredSize(new Dimension(50, 50));
        fixedSizePanel.setLayout(new GridBagLayout());
        fixedSizePanel.add(returnButton);

        // Add the fixed size panel to the LeaderboardGUI panel
        add(fixedSizePanel, BorderLayout.NORTH);
    }
}