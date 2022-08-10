package player;

import board.Board;
import board.Square;
import ship.Ship;

import java.util.List;

public interface Player {

    void addShips(Ship ship);

    void handleShot(Square[][] enemyBoard, Square[][] playersBoard,Player player, Board board);

    void markShot(int x, int y,Square[][] enemyBoard, Square[][] playersBoard);

    void sinkShip(List<Square> squares,Board board);

    boolean isPossibleSink(List<Square> squares);

    List<Square> shipFields(int x, int y,Player player);

    List<Ship> getPlayerShips();

    boolean checkSquareStatus(int x, int y,Square[][] ocean);


}
