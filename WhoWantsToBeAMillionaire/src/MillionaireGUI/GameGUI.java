package MillionaireGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGUI extends JPanel {

    private final JLabel userNameLabel;

    public GameGUI(CardLayout cardLayout, JPanel cards) {
        setLayout(new BorderLayout());

        userNameLabel = new JLabel("Welcome, ");
        add(userNameLabel, BorderLayout.NORTH);
    }

    public void setUserName(String userName) {
        userNameLabel.setText("Welcome, " + userName);
    }
}
