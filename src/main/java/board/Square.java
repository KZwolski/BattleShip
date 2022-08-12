package board;

/**
 * Square class, representing one field on the board
 */
public class Square {
    /**
     * Field representing row of the square
     */
    private int x;
    /**
     * Field representing column of the square
     */
    private int y;
    /**
     * Field storing SquareStatus enum
     */
    private SquareStatus squareStatus;

    /**
     * Method responsible for setting SquareStatus field
     *
     * @param squareStatus one of the values  from SquareStatus
     */
    public void setSquareStatus(SquareStatus squareStatus) {
        this.squareStatus = squareStatus;
    }

    /**
     * Method used for getting SquareStatus
     *
     * @return current status of the square
     */
    public SquareStatus getSquareStatus() {
        return squareStatus;
    }

    /**
     * Method used for getting row value from square object
     *
     * @return x field value
     */
    public int getX() {
        return x;
    }
    /**
     * Method used for getting column value from square object
     *
     * @return y field value
     */
    public int getY() {
        return y;
    }

    /**
     * Constructor of class Square, sets SquareStatus as EMPTY
     * @param x row to be init
     * @param y column to be inti
     */
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.squareStatus = SquareStatus.EMPTY;

    }

    @Override
    public String toString() {
        return squareStatus.getCharacter();
    }
}
