package player;

import board.Board;
import board.Square;
import board.SquareStatus;
import ship.Ship;
import ship.ShipType;
import utilities.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract class storing methods shared by classes that extends it
 */

public abstract class Player {
    /**
     * Field representing name of player
     */
    protected String playerName;
    /**
     * Represents player's board
     */
    protected Board board;
    /**
     * Value used to determine winner
     */

    protected boolean isAlive;
    /**
     * Stores player's current game score
     */
    protected int score;
    /**
     * Instance of input class, used to handle user inputs
     *
     * @see Input
     */

    protected Input input = new Input();

    /**
     * Method used to get access to user's score
     *
     * @return user's score as Integer
     */
    public int getScore() {
        return score;
    }

    /**
     * Class field storing player's ships
     */
    protected List<Ship> playerShips = new ArrayList<>();

    /**
     * Method used for getting access to player's ships list
     *
     * @return list of ships
     */
    public List<Ship> getPlayerShips() {
        return playerShips;
    }

    /**
     * Method used to add new ship to player's ships list
     *
     * @param ship Object of type ship
     */
    public void addShips(Ship ship) {
        this.playerShips.add(ship);
    }

    /**
     * Method checking if user is still alive
     *
     * @return true if user didn't lose all the ships
     */
    public boolean isStillAlive() {
        for (int i = 0; i < playerShips.size(); i++) {
            for (int j = 0; j < playerShips.get(i).getOccupiedCells().size(); j++) {
                if (!playerShips.get(i).getOccupiedCells().get(j).getSquareStatus().equals(SquareStatus.SINK)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method used to get access to player's name
     *
     * @return String stored in playerName field
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Method used for getting valid coordinates for shooting
     *
     * @return array of ints storing row and column of shot
     */
    public abstract int[] getValidShotCords();

    /**
     * Method responsible for handling shot of player
     *
     * @param enemyBoard enemy board with ships
     * @param yourGuesses board with users guesses
     * @param enemyPlayer object of type Player
     */
    public void handleShot(Board enemyBoard, Board yourGuesses, Player enemyPlayer) {
        int[] cords = getValidShotCords();
        if (checkSquareStatus(cords[0], cords[1], yourGuesses.getOcean())) {
            handleShot(enemyBoard, yourGuesses, enemyPlayer);
        }
        markShot(cords[0], cords[1], enemyBoard.getOcean(), yourGuesses.getOcean());
        List<Square> squares = shipFields(cords[0], cords[1], enemyPlayer);
        if (isPossibleSink(squares)) {
            sinkShip(squares, yourGuesses);
        }
    }

    /**
     * Method used for changing square status to "HIT" after shot
     *
     * @param x row of the shot
     * @param y column of the shot
     * @param enemyBoard board with enemy's ships
     * @param playersBoard board with player's guesses
     */

    public void markShot(int x, int y, Square[][] enemyBoard, Square[][] playersBoard) {
        if (enemyBoard[x][y].getSquareStatus().equals(SquareStatus.SHIP)) {
            enemyBoard[x][y].setSquareStatus(SquareStatus.HIT);
            playersBoard[x][y].setSquareStatus(SquareStatus.HIT);
            score += 5;
        } else if (enemyBoard[x][y].getSquareStatus().equals(SquareStatus.EMPTY)) {
            enemyBoard[x][y].setSquareStatus(SquareStatus.MISSED);
            playersBoard[x][y].setSquareStatus(SquareStatus.MISSED);
            score -= 1;

        }
    }

    ;

    /**
     * Method used for sinking the ship
     *
     * @param squares list of squares to be sunk
     * @param board player's guesses board
     */
    public void sinkShip(List<Square> squares, Board board) {
        for (Square square : squares) {
            square.setSquareStatus(SquareStatus.SINK);
            board.getOcean()[square.getX()][square.getY()].setSquareStatus(SquareStatus.SINK);
            score += ShipType.values().length;

        }
    }

    ;

    /**
     * Method used for checking if sinking the ship is possible
     *
     * @param squares list of squares to search in
     * @return true if sink is possible
     */
    public boolean isPossibleSink(List<Square> squares) {
        for (Square square : squares) {
            if (!square.getSquareStatus().equals(SquareStatus.HIT)) {
                return false;
            }
        }
        return true;
    }

    ;

    /**
     * Method used to get all fields of the shot ship
     *
     * @param x row of the shot
     * @param y column of the shot
     * @param player Object of type Player
     * @return list of squares that represent single Ship
     */
    public List<Square> shipFields(int x, int y, Player player) {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < player.getPlayerShips().size(); i++) {
            for (int j = 0; j < player.getPlayerShips().get(i).getOccupiedCells().size(); j++) {
                if (player.getPlayerShips().get(i).getOccupiedCells().get(j).getX() == x && player.getPlayerShips().get(i).getOccupiedCells().get(j).getY() == y) {
                    squares = player.getPlayerShips().get(i).getOccupiedCells();

                }
            }
        }
        return squares;
    }

    /**
     * Method checking if square status is HIT or MISSED, used for checking if the field was already shot
     *
     * @param x row of the shot coords
     * @param y column of the shot coords
     * @param ocean player's board
     * @return false if square status is HIT or MISSED
     */
    public boolean checkSquareStatus(int x, int y, Square[][] ocean) {
        return ocean[x][y].getSquareStatus().equals(SquareStatus.HIT) || ocean[x][y].getSquareStatus().equals(SquareStatus.MISSED);
    }

    /**
     * Method used for excluding fields around sunk ship to limit possible shots
     *
     * @param squares list of squares representing ship
     * @param excludedFields list of excluded fields
     */
    public void excludeSquaresAroundShip(List<Square> squares,List<int[]> excludedFields) {
        excludeHorizontalAndVerticalAroundShip(squares,excludedFields);
        excludeDiagonalAroundShip(squares,excludedFields);

    }

    /**
     * Method used for excluding  Horizontal and Vertical fields around sunk ship
     *
     * @param squares list of squares representing ship
     * @param excludedFields list of excluded fields
     */

    public void excludeHorizontalAndVerticalAroundShip(List<Square> squares, List<int[]> excludedFields) {
        for (int i = 0; i < squares.size(); i++) {
            if (input.validateCords(squares.get(i).getX() + 1, squares.get(i).getY())) {
                excludedFields.add(new int[]{squares.get(i).getX() + 1, squares.get(i).getY()});
            }
            if (input.validateCords(squares.get(i).getX() - 1, squares.get(i).getY())) {
                excludedFields.add(new int[]{squares.get(i).getX() - 1, squares.get(i).getY()});
            }
            if (input.validateCords(squares.get(i).getX(), squares.get(i).getY() + 1)) {
                excludedFields.add(new int[]{squares.get(i).getX(), squares.get(i).getY() + 1});
            }
            if (input.validateCords(squares.get(i).getX(), squares.get(i).getY() - 1)) {
                excludedFields.add(new int[]{squares.get(i).getX(), squares.get(i).getY() - 1});
            }
        }
    }
    /**
     * Method used for excluding diagonal fields around sunk ship
     *
     * @param squares list of squares representing ship
     * @param excludedFields list of excluded fields
     */

    public void excludeDiagonalAroundShip(List<Square> squares,List<int[]> excludedFields) {
        for (int i = 0; i < squares.size(); i++) {
            if (input.validateCords(squares.get(i).getX() + 1, squares.get(i).getY() + 1)) {
                excludedFields.add(new int[]{squares.get(i).getX() + 1, squares.get(i).getY() + 1});
            }
            if (input.validateCords(squares.get(i).getX() - 1, squares.get(i).getY() + 1)) {
                excludedFields.add(new int[]{squares.get(i).getX() - 1, squares.get(i).getY() + 1});
            }
            if (input.validateCords(squares.get(i).getX() + 1, squares.get(i).getY() - 1)) {
                excludedFields.add(new int[]{squares.get(i).getX() + 1, squares.get(i).getY() - 1});
            }
            if (input.validateCords(squares.get(i).getX() - 1, squares.get(i).getY() - 1)) {
                excludedFields.add(new int[]{squares.get(i).getX() - 1, squares.get(i).getY() - 1});
            }
        }
    }

    /**
     * Method drawing coordinates from possible moves, used by computer player
     *
     * @param possibleMoves list of possible moves
     * @return next shot coordinates
     */
    public int[] drawFromPossibleMoves(List<int[]> possibleMoves) {
        int randomNUmber = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
        return new int[]{possibleMoves.get(randomNUmber)[0], possibleMoves.get(randomNUmber)[1]};

    }



}
