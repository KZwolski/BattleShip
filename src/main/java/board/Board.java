package board;

public class Board extends BoardFactory {
    private final int BOARDSIZE = 10;

    private Square[][] ocean = new Square[BOARDSIZE][BOARDSIZE];

    public Board() {
        for (int i = 0; i < BOARDSIZE; i++){
            for (int j = 0; j < BOARDSIZE; j++){
                ocean[i][j] = new Square(i,j);
            }
        }
    }

    public Square[][] getOcean() {
        return ocean;
    }
}
