package MillionaireGUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final MenuGUI menuGUI;
    private final GameGUI gameGUI;
    private final LeaderboardGUI leaderboardGUI;

    public MainFrame() {

        // Create panel for title label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add title label to the title panel
        JLabel titleLabel = new JLabel("<html><body><center>Who Wants To Be<br>A Millionaire</center></body></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);
        setResizable(false);

        // Add an empty border to create a gap at the top of the title panel
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Use GroupLayout to layout the components
        GroupLayout layout = new GroupLayout(getContentPane());
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);

        setTitle("Who Wants To Be A Millionaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1700, 1000);

        leaderboardGUI = new LeaderboardGUI(cardLayout, cards);
        menuGUI = new MenuGUI(cardLayout, cards, leaderboardGUI);
        gameGUI = new GameGUI(cardLayout, cards);

        LeaderboardGUI leaderboardGUI = new LeaderboardGUI(cardLayout, cards);

        cards.add(menuGUI, "menuGUI");
        cards.add(gameGUI, "gameGUI");
        cards.add(leaderboardGUI, "leaderboardGUI");

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
