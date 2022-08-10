import board.Board;
import dataManager.DataManager;
import player.Player;
import player.UserPlayer;
import ship.ShipType;
import utilities.Display;
import utilities.Input;

public class Battleship {
//    public static void main(String[] args) {
//        Display printer = new Display();
//        printer.welcomeMenu();
//        String input = Input.getUserInput();
//        switch (input) {
//            case "1" -> startNewGame();
//            case "2" -> displayHighScores();
//            case "3" -> exitGame();
//            default -> Display.displayInvalidValue();
//        }
//    }
//    public static void startNewGame() {
//    }
//
 //   public static void displayHighScores() {
 //   DataManager.bestScoreRead();
 //   }
//
//    public static void exitGame() {
//        System.out.println("Ok, Bye!");
//    }
public static void main(String[] args) {
    Board board = new Board();
    UserPlayer player = new UserPlayer("adam", board);
    Display display = new Display();

    for(int i = 0; i<5; i++){
        board.randomPlacement(board, ShipType.values()[i], player);
    }
    display.printBoard(board.getOcean());
    player.handleShot();
    display.printBoard(board.getOcean());
    player.handleShot();
    display.printBoard(board.getOcean());
    player.handleShot();
    display.printBoard(board.getOcean());

}
}

