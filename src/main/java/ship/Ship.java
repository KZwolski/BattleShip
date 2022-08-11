package ship;

import board.Square;

import java.util.List;

/**
 * Class representing ship on the board
 */
public class Ship {
    /**
     * List storing all the squares on which ship is placed
     */
    private List<Square> occupiedCells;

    /**
     * Method used for getting list of squares on which ship is placed
     * @return list of squares where ship is placed
     */
    public List<Square> getOccupiedCells() {
        return occupiedCells;
    }

    /**
     * Constructor of class SHip, creates instance of the class and also passing list passed as argument to occupiedCells field
     *
     * @param squares list squares on which ship is placed
     */
    public Ship(List<Square> squares) {
        this.occupiedCells = squares;
    }


}
