package WhoWantsToBeAMillionaire;

import java.io.IOException;
import java.sql.SQLException;

public class WhoWantsToBeAMillionaire {

    public static void main(String[] args) throws IOException, SQLException {
        Game game = new Game();
        game.runGame();
    }
}
