import board.Board;
import ship.ShipType;
public class Battleship {
    public static void main(String[] args) {
        Board board = new Board();
        for(int i=0; i<5; i++){
            board.randomPlacement(board,ShipType.values()[i]);
        }
        System.out.println(" ");
        board.printBoard();

//  commit test2
    }
}
