package board;

public class Board extends BoardFactory {
    private final int BOARD_SIZE = 10;

    public int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    private Square[][] ocean = new Square[BOARD_SIZE][BOARD_SIZE];

    public Board() {
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                ocean[i][j] = new Square(i,j);
            }
        }
    }
    public void printBoard(){
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                System.out.print(ocean[i][j]+ "  ");
            }
            System.out.println("");
        }
    }
    public Square[][] getOcean() {
        return ocean;
    }
}
