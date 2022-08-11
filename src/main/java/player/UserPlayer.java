package player;

import board.Board;


public class UserPlayer extends Player {


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
