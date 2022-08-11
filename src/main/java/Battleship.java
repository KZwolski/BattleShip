import logic.Game;

import java.util.ArrayList;
import java.util.List;

/**
 *BattleShip creates new game instance for starting the game
 *
 */
public class Battleship {
    /**
     * Main method of the program responsible for creating Instance of game class and starting the game
     * @param args stores incoming line arguments for the program
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initGame();

    }
}

