package player;

import board.Board;

import java.util.concurrent.ThreadLocalRandom;
/**
 * Class representing Hard Level of the game, extending Player abstract class
 */
public class EasyComputer extends Player {
    /**
     * Constructor for EasyComputer, init name as "Computer", isALive is set as true, score =0
     * @param board Object of type board
     */
    public EasyComputer(Board board) {
        this.playerName = "Computer";
        this.board = board;
        this.isAlive = true;
        this.score = 0;
    }
    /**
     * Method responsible for getting random valid shot cords
     *
     * @return array of ints storing coordinates of shot
     */
    public int[] drawCoordinates() {
        int min = 0;
        int max = 9;
        return new int[]{ThreadLocalRandom.current().nextInt(min, max + 1), ThreadLocalRandom.current().nextInt(min, max + 1)};

    }

    public int[] getValidShotCords() {
        int[] cords = drawCoordinates();
        while (!input.validateCords(cords[0], cords[1])) {
            cords = drawCoordinates();
        }
        return cords;
    }
}
