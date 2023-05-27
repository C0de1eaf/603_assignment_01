package WhoWantsToBeAMillionaire;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;

public class WhoWantsToBeAMillionaire {

    public static void main(String[] args) throws IOException, SQLException {
        Game game = new Game();
        game.runGame();
    }
}
