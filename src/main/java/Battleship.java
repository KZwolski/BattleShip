import utilities.Display;
import utilities.Input;

public class Battleship {
    public static void main(String[] args) {
        Display printer = new Display();
        String input = Input.getUserInput();
        printer.welcomeMenu();
        switch (input) {
            case "1" -> startNewGame();
            case "2" -> displayHighScores();
            case "3" -> exitGame();
            default -> Display.displayInvalidValue();
        }
    }
    public static void startNewGame() {
    }

    public static void displayHighScores() {
    }

    public static void exitGame() {
        System.out.println("Ok, Bye!");
    }
}

