package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LeaderboardGUI extends JPanel {

    private JButton returnButton;

    public LeaderboardGUI(CardLayout cardLayout, JPanel cards) {
        // Set the layout to FlowLayout with left alignment
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Set the icon for the return button
        try {
            JPanel returnPanel = new JPanel();
            returnPanel.setBounds(50, 50, 250, 250);

            BufferedImage panelImage = ImageIO.read(new File("resources/return.png"));
            ImageIcon returnButtonIcon = new ImageIcon(panelImage);

            JButton returnButton = new JButton(returnButtonIcon);
            // Set the background color of the return button to solid light grey
            returnButton.setBackground(new Color(192, 192, 192)); // RGB: 192, 192, 192 (light grey)

            // Remove border
            returnButton.setBorderPainted(false);

            // Add ActionListener to return button
            returnButton.addActionListener(e -> {
                cardLayout.show(cards, "menuGUI");
            });

            // Remove spacing between the image and button's borders
            returnButton.setMargin(new Insets(0, 0, 0, 0));

            returnPanel.add(returnButton);
            add(returnPanel);

        } catch (Exception ex) {
            System.out.println(ex);
        }

//        // Set the background color of the return button to solid light grey
//        returnButton.setBackground(new Color(192, 192, 192)); // RGB: 192, 192, 192 (light grey)
//
//        // Remove spacing between the image and button's borders
//        returnButton.setMargin(new Insets(0, 0, 0, 0));
//
//        // Remove border
//        returnButton.setBorderPainted(false);
//
//        // Add ActionListener to return button
//        returnButton.addActionListener(e -> {
//            cardLayout.show(cards, "menuGUI");
//        });
//
//        add(returnButton);
    }
}
