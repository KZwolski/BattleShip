package Board;

public class Square {
    private int x;
    private int y;
    private boolean isEmpty;
    private boolean isShip;
    private boolean isHit;
    private boolean isMissed;
//    private boolean isSunk;
//    private SquareStatus squareStatus;


    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.isEmpty = true;
        this.isShip = false;
        this.isHit = false;
        this.isMissed = false;
    }

    @Override
    public String toString() {
        if (isShip){
            return SquareStatus.SHIP.getCharacter();
        } else if (isHit){
            return SquareStatus.HIT.getCharacter();
        } else if (isMissed){
            return SquareStatus.MISSED.getCharacter();
        } else {
            return SquareStatus.EMPTY.getCharacter();
        }
    }
}
