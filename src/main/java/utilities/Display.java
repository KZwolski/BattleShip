package utilities;

import board.Square;
import board.SquareStatus;

public class Display {

    public final String LIGHT_BLUE = "\033[38;2;120;172;255m"; //LIGHT BLUE
    public final String RESET = "\033[0m";  // Text Reset
    public final String BLACK = "\033[0;30m";   // BLACK
    public final String DARK_RED = "\033[38;2;145;40;16m"; //DARK RED
    public final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    private final String SHIP = "\uD83D\uDEF3";
    private final String WATER = "\uD83C\uDF0A";
    private final String SHIP_SUNK = "\uD83D\uDCA3";
    private final String HIT_MISS = "\uD83C\uDFAF";
    private final String HIT_SHIP = "\uD83D\uDE80";

    private final String CLEAR_SCREEN = "\033[H\033[2J";




    public void welcomeMenu() {
        System.out.println("Main Menu");
        System.out.println("1-Start game");
        System.out.println("2-Highest score");
        System.out.println("3-Quit");
    }
    public void gameTypeMenu() {
        System.out.println("GameType Menu");
        System.out.println("1-Player vs Player");
        System.out.println("2-Player vs Computer");
        System.out.println("3-Quit");
    }
    public void shipPlacementMenu() {
        System.out.println("ShipPlacement menu");
        System.out.println("1-Manual Placement");
        System.out.println("2-Random Placement");
        System.out.println("3-Quit");
    }
    public void computerDifficultyMenu() {
        System.out.println("computerDifficultyMenu");
        System.out.println("1-Easy Level");
        System.out.println("2-Medium Level");
        System.out.println("3-Hard Level");
        System.out.println("4-Quit");
    }
    public static void displayInvalidValue() {
        System.out.println("Invalid input try again");
    }


    public void printBoard(Square[][] ocean) {
        System.out.println(BLACK_BACKGROUND_BRIGHT+".|  1   2   3  4   5  6   7   8   9  10 " +RESET +"|");
        for (int i = 0; i < ocean.length; i++) {
            System.out.print(BLACK_BACKGROUND_BRIGHT+(char) (i + 'A') + "|"+RESET+"  ");
            for (int j = 0; j < ocean.length; j++) {
                if (ocean[i][j].getSquareStatus().equals(SquareStatus.EMPTY)){
                    System.out.print(LIGHT_BLUE + WATER + "  ");
                } else if (ocean[i][j].getSquareStatus().equals(SquareStatus.SHIP)){
                    System.out.print(RESET + SHIP + "  ");
                } else if (ocean[i][j].getSquareStatus().equals(SquareStatus.MISSED)){
                    System.out.print(RESET+HIT_MISS + "  ");
                } else if (ocean[i][j].getSquareStatus().equals(SquareStatus.HIT)){
                    System.out.print(DARK_RED+HIT_SHIP + "  ");
                } else if (ocean[i][j].getSquareStatus().equals(SquareStatus.SINK)){
                    System.out.print(BLACK + SHIP_SUNK + "  ");
                }
            }
            System.out.println(RESET+"|");

        }
        System.out.println("----------------------------------------/");
        System.out.print(SHIP + RESET +" Ship ");
        System.out.print(LIGHT_BLUE+WATER + RESET +" Water ");
        System.out.println(BLACK+SHIP_SUNK + RESET +" Ship sunk ");
        System.out.print(HIT_MISS + " Hit(Miss) ");
        System.out.print(DARK_RED+HIT_SHIP + RESET +" Hit(Ship)");

    }

    public void consolePrint(String message){
        System.out.println(message);
    }


}
