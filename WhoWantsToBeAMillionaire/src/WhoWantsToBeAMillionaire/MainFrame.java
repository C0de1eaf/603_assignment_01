package WhoWantsToBeAMillionaire;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private final GameGUI gameGUI;
    private final MenuGUI menuGUI;
    private final LeaderboardGUI leaderboardGUI;
    private final Game game;

    public MainFrame() {

        // Create panel for title label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add title label to the title panel
        JLabel titleLabel = new JLabel("<html><body><center>Who Wants To Be<br>A Millionaire</center></body></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);

        // Use GridBagLayout to center the titlePanel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        add(titlePanel, gbc);

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);

        game = new Game();
        gameGUI = new GameGUI(cardLayout, cards);

        setTitle("Game and Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);

        leaderboardGUI = new LeaderboardGUI(cardLayout, cards);

        cards.add(gameGUI, "gameGUI");
        cards.add(leaderboardGUI, "leaderboardGUI");

        menuGUI = new MenuGUI(cardLayout, cards);
        cards.add(menuGUI, "menuGUI");

        gbc.gridy = 1;
        add(cards, gbc);
        cardLayout.show(cards, "menuGUI");
    }

}
