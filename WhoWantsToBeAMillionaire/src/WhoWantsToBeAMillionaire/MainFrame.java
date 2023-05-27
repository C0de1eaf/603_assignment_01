package WhoWantsToBeAMillionaire;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private GameGUI gameGUI;
    private MenuGUI menuGUI;

    public MainFrame() {
        setTitle("Game and Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);

        gameGUI = new GameGUI();

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);

        cards.add(gameGUI, "gameGUI");

        menuGUI = new MenuGUI(cardLayout, cards); // Only create the instance once
        cards.add(menuGUI, "menuGUI");

        add(cards);
        cardLayout.show(cards, "menuGUI");
    }
}
