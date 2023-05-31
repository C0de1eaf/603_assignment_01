package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class LeaderboardGUI extends JPanel {

    private JButton returnButton;

    public LeaderboardGUI(CardLayout cardLayout, JPanel cards) {
        setLayout(new BorderLayout());
        // Set the layout to FlowLayout with left alignment
        setLayout(new FlowLayout(FlowLayout.LEFT));

        try {
            BufferedImage panelImage = ImageIO.read(new File("resources/cropped_return.png"));
            ImageIcon returnButtonIcon = new ImageIcon(panelImage);

            returnButton = new JButton(returnButtonIcon);

            // Remove border
            returnButton.setBorderPainted(false);

            // Set the background color of the return button to solid light grey
            returnButton.setBackground(new Color(255, 255, 255)); // RGB: 192, 192, 192 (light grey)

            // Remove spacing between the image and button's borders
            returnButton.setMargin(new Insets(0, 0, 0, 0));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        // Add ActionListener to return button
        returnButton.addActionListener(e -> {
            cardLayout.show(cards, "menuGUI");
        });

        // Add the return button to the LeaderboardGUI panel
        add(returnButton, BorderLayout.PAGE_END);
    }
}
