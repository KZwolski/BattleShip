package player;

import board.Board;
import board.Square;
import ship.Ship;

import java.util.List;

public class EasyComputer extends ComputerPlayer{
    public EasyComputer(Board board) {
        this.playerName="Computer";
        this.board = board;
        this.isAlive = true;
        this.score=0;
    }

}
