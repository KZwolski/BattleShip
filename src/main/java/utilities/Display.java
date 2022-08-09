package utilities;

import board.Square;

public class Display {

    private final String[] ALPHABET = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public void welcomeMenu(){
        System.out.println("Welcome in BattleShip game!");
        System.out.println("Hit enter to continue!");
    }


    public void printBoard(Square[][] ocean){
        System.out.println(".| 1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < ocean.length; i++){
            System.out.print(ALPHABET[i] + "| ");
            for (int j = 0; j < ocean.length; j++){
                System.out.print(ocean[i][j] + " ");
            }
            System.out.println("");

        }
    }


}
