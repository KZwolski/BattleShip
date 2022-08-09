import board.*;
import ship.ShipType;
import utilities.Display;
import utilities.Input;

public class Battleship {
    public static void main(String[] args) {
        Board board = new Board();
        Display printer = new Display();
        printer.welcomeMenu();
        Square[][] ocean = board.getOcean();
        printer.printBoard(ocean);
        for(int i=0; i<5; i++){
            board.randomPlacement(board, ShipType.values()[i]);
            printer.printBoard(ocean);
        }

    }
}
