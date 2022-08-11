package board;

/**
 * Class board extending BoardFactory, responsible for handling board of the game
 */
public class Board extends BoardFactory {
    /**
     * Class field representing size of the board
     */
    private final int BOARD_SIZE = 10;
    /**
     * 2-D list of Square objects storing all squares of the game
     */
    private Square[][] ocean = new Square[BOARD_SIZE][BOARD_SIZE];

    /**
     * Constructor of Board class, responsible for initializing the ocean with new Instances of Square class
     */
    public Board() {
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                ocean[i][j] = new Square(i,j);
            }
        }
    }

    /**
     * Method responsible for getting the board
     * @return 2-D list of Square objects
     */
    public Square[][] getOcean() {
        return ocean;
    }
}
