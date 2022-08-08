package ship;

import board.Square;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final List<Square> occupiedCells = new ArrayList<Square>();

    public void occupySquare(Square square) {
        occupiedCells.add(square);
    }

    public List<Square> getOccupiedCells() {
        return occupiedCells;
    }
}
