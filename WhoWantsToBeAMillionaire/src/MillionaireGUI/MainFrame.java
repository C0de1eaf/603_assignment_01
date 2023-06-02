package MillionaireGUI;

import MillionaireGUI.GameGUI;
import MillionaireGUI.MenuGUI;
import MillionaireGUI.LeaderboardGUI;
import WhoWantsToBeAMillionaire.Game;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private GameGUI gameGUI;
    private MenuGUI menuGUI;
    private LeaderboardGUI leaderboardGUI;
    private Game game;

    public MainFrame() {

        game = new Game();
        gameGUI = new GameGUI();
        game.setUserNameFromGameGUI(gameGUI);
        gameGUI.setGame(game);

        setTitle("Game and Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);

        leaderboardGUI = new LeaderboardGUI(cardLayout, cards);

        cards.add(gameGUI, "gameGUI");
        cards.add(leaderboardGUI, "leaderboardGUI");

        menuGUI = new MenuGUI(cardLayout, cards);
        cards.add(menuGUI, "menuGUI");

        add(cards);
        cardLayout.show(cards, "menuGUI");
        
        System.out.println();
    }
}
