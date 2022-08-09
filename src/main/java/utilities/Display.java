package utilities;

import board.Square;

public class Display {

    public void welcomeMenu() {
        System.out.println("Main Menu");
        System.out.println("1-Start game");
        System.out.println("2-Highest score");
        System.out.println("3-Quit");
    }


    public void printBoard(Square[][] ocean) {
        System.out.println(".| 1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < ocean.length; i++) {
            System.out.print((char) (i + 'A') + "| ");
            for (int j = 0; j < ocean.length; j++) {
                System.out.print(ocean[i][j] + " ");
            }
            System.out.println("");

        }
    }


}
