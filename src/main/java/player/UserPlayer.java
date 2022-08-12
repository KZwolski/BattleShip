package player;

import board.Board;

/**
 * Class representing user, extending Player abstract class
 */
public class UserPlayer extends Player {

    /**
     * Constructor for UserPlayer,creates instance of UserPlayer class, init name, isALive is set as true, score =0
     *
     * @param playerName name of the player
     * @param board Object of type board
     */
    public UserPlayer(String playerName, Board board) {
        this.playerName = playerName;
        this.board = board;
        this.isAlive = true;
        this.score = 0;
    }

    public int[] getValidShotCords() {
        int[] cords = input.getCoordinates();
        while (!input.validateCords(cords[0], cords[1])) {
            cords = input.getCoordinates();
        }
        return cords;
    }


}
