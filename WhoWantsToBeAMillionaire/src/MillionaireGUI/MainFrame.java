package MillionaireGUI;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;

public class MainFrame extends JFrame {

    private final GameGUI gameGUI;
    private final MenuGUI menuGUI;
    private final LeaderboardGUI leaderboardGUI;

    public MainFrame() {

        // Create panel for title label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add title label to the title panel
        JLabel titleLabel = new JLabel("<html><body><center>Who Wants To Be<br>A Millionaire</center></body></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);

        // Use GroupLayout to layout the components
        GroupLayout layout = new GroupLayout(getContentPane());
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);

        gameGUI = new GameGUI(cardLayout, cards);

        setTitle("Who Wants To Be A Millionaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);

        leaderboardGUI = new LeaderboardGUI(cardLayout, cards);

        cards.add(gameGUI, "gameGUI");
        cards.add(leaderboardGUI, "leaderboardGUI");

        menuGUI = new MenuGUI(cardLayout, cards);
        cards.add(menuGUI, "menuGUI");

        // Define GroupLayout rules
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(titlePanel)
                .addComponent(cards)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titlePanel)
                .addComponent(cards)
        );

        cardLayout.show(cards, "menuGUI");
    }
}
