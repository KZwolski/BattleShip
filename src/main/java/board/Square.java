package board;

public class Square {
    private int x;
    private int y;
    private SquareStatus squareStatus;
//    private boolean isSunk;



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
