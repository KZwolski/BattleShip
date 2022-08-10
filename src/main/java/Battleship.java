import board.Board;
import dataManager.DataManager;

import board.SquareStatus;
import player.ComputerPlayer;
import player.EasyComputer;
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
    Display display = new Display();
    Board playersBoard = new Board();
    Board playersGuesses = new Board();
    Board computersBoard = new Board();
    Board computersGuesses = new Board();
    Player player = new UserPlayer("Karol",playersBoard);
    Player computer = new EasyComputer(computersBoard);
    for(int i = 0; i<5; i++){
        playersBoard.randomPlacement(playersBoard, ShipType.values()[i], player);
    }
    for(int i = 0; i<5; i++){
        computersBoard.randomPlacement(computersBoard, ShipType.values()[i], computer);
    }
    while (true){
        System.out.println("PLAYER SHOOTING NOW");
        System.out.println("YOUR SHIPS: ");
        display.printBoard(playersBoard.getOcean());
        System.out.println("");
        System.out.println("YOUR GUESSES: ");
        display.printBoard(playersGuesses.getOcean());
        player.handleShot(computersBoard.getOcean(), playersGuesses.getOcean(),computer, playersGuesses);
        System.out.println("YOUR GUESSES: ");
        display.printBoard(playersGuesses.getOcean());
        System.out.println("COMPUTER SHOOTING NOW: ");
        computer.handleShot(playersBoard.getOcean(), computersGuesses.getOcean(),player, computersGuesses);
        display.printBoard(computersBoard.getOcean());
    }
}
}

