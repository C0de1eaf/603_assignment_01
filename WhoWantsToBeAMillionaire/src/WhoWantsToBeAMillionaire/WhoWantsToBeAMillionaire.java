package WhoWantsToBeAMillionaire;

import java.io.IOException;
import javax.swing.JFrame;

public class WhoWantsToBeAMillionaire {

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        JFrame frame = new JFrame("Moving Balls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameGUI GUI = new GameGUI();
        
        frame.getContentPane().add(GUI);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        
        
//        Game game = new Game();
//        game.runGame();
    }
}
