package board;

public class Square {
    private int x;
    private int y;
    private SquareStatus squareStatus;


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSquareStatus(SquareStatus squareStatus) {
        this.squareStatus = squareStatus;
    }

    public SquareStatus getSquareStatus() {
        return squareStatus;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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
