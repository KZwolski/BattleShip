package player;

import board.Board;
import board.Square;
import board.SquareStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class representing Medium Level of the game, extending Player abstract class
 */
public class MediumComputer extends Player {
    /**
     * List that stores all the excluded fields for the computer player
     */
    List<int[]> excludedFields = new ArrayList<>();

    /**
     * Array of ints that stores coordinates of the last hit cords
     */
    private int[] lastShotCords;
    /**
     * String representing direction in which computer is shooting
     */
    private String Direction;
    /**
     * Value representing shift of the shot
     */

    private int shiftValue;
    /**
     * Fields representing number of hits in a row
     */
    private int hitInARow;

    /**
     * Method used for getting shiftValue
     *
     * @return value of shiftValue field
     */
    public int getShiftValue() {
        return shiftValue;
    }

    /**
     * Method for setting shift of the shot
     *
     * @param shiftValue value of shift
     */
    public void setShiftValue(int shiftValue) {
        this.shiftValue = shiftValue;
    }

    /**
     * Method used for getting direction of the shot
     *
     * @return value of Direction field
     */
    public String getDirection() {
        return Direction;
    }

    /**
     * Method used for setting the direction of shot
     *
     * @param direction direction
     */
    public void setDirection(String direction) {
        Direction = direction;
    }

    /**
     * Constructor for MediumComputer, init name as "Computer", isALive is set as true, score =0 and shiftValue on the start equals 1
     * @param board Object of type board
     */
    public MediumComputer(Board board) {
        this.playerName = "Computer";
        this.board = board;
        this.isAlive = true;
        this.score = 0;
        this.shiftValue = 1;
    }

    @Override
    public int[] getValidShotCords() {
        int[] cords = drawCoordinates();
        while (!input.validateCords(cords[0], cords[1])) {
            cords = drawCoordinates();
        }
        return cords;
    }

    /**
     * Method responsible for getting random valid shot cords
     *
     * @return array of ints storing coordinates of shot
     */
    public int[] drawCoordinates() {
        int min = 0;
        int max = 9;
        return new int[]{ThreadLocalRandom.current().nextInt(min, max + 1), ThreadLocalRandom.current().nextInt(min, max + 1)};

    }

    /**
     * Method responsible for drawing random direction
     *
     * @return Vertical direction as "V" or Horizontal direction as "H"
     */
    public String drawDirection() {
        String[] values = {"V", "H"};
        return values[ThreadLocalRandom.current().nextInt(0, values.length)];

    }

    /**
     * Method used for checking if shot coordinates are stored in excludedFields field
     * @param x row of the shot
     * @param y column of the shot
     * @return true if shot cords are not excluded ones
     */

    public boolean checkIfCordsNotExcluded(int x, int y) {
        for (int i = 0; i < excludedFields.size(); i++) {
            if (excludedFields.get(i)[0] == x && excludedFields.get(i)[1] == y) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method responsible for getting possible moves for next shot
     *
     * @param direction represents direction
     * @param shiftValue represents shiftValue
     * @return list storing arrays of ints that stores coordinates of possible moves
     */

    public List<int[]> possibleMoves(String direction, int shiftValue) {
        List<int[]> possibleMoves = new ArrayList<>();
        if (Objects.equals(direction, "V")) {
            if (input.validateCords(lastShotCords[0] + hitInARow * (shiftValue), lastShotCords[1]) && checkIfCordsNotExcluded(lastShotCords[0] + hitInARow * (shiftValue), lastShotCords[1])) {
                possibleMoves.add(new int[]{lastShotCords[0] + hitInARow * (shiftValue), lastShotCords[1]});
            }
        } else if (Objects.equals(direction, "H")) {
            if (input.validateCords(lastShotCords[0], lastShotCords[1] + hitInARow * (shiftValue)) && checkIfCordsNotExcluded(lastShotCords[0], lastShotCords[1] + hitInARow * (shiftValue))) {
                possibleMoves.add(new int[]{lastShotCords[0], lastShotCords[1] + hitInARow * (shiftValue)});
            }
        }
        return possibleMoves;
    }

    /**
     * Method used for swapping direction to opposite
     */
    public void swapDirection() {
        if (Objects.equals(getDirection(), "H")) {
            setDirection("V");
        } else if (Objects.equals(getDirection(), "V")) {
            setDirection("H");
        }
    }

    /**
     * Method used for swapping shift number to opposite
     */
    public void swapShiftNumber() {
        if (Objects.equals(getShiftValue(), 1)) {
            setShiftValue(-1);
        } else if (Objects.equals(getShiftValue(), -1)) {
            setShiftValue(1);
        }
    }

    /**
     * Method used for setting class fields after hitting a ship in a row
     * @param cords coordinates of shoot
     */
    public void setClassFieldsAfterMultiHit(int[] cords) {
        excludedFields.add(cords);
        hitInARow += 1;
    }

    /**
     * Method used for setting class fields when previous shot was hit but current is missed
     * @param cords coordinates of shoot
     */
    public void setClassFieldsAfterHitMiss(int[] cords) {
        excludedFields.add(cords);
        hitInARow = 1;
    }

    /**
     * Method used for setting class fields after single hit
     * @param cords coordinates of shoot
     */
    public void setClassFieldsAfterSingleHit(int[] cords) {
        excludedFields.add(cords);
        lastShotCords = cords;
        hitInARow += 1;
    }

    /**
     * Method used for setting class fields after missing
     * @param cords coordinates of shoot
     */
    public void setClassFieldsAfterSingleMiss(int[] cords) {
        excludedFields.add(cords);
    }

    /**
     * Method used for setting class fields after sinking ship
     * @param squares list of squares occupied by the ship
     */

    public void setClassFieldsAfterSinkShip(List<Square> squares) {
        excludeSquaresAroundShip(squares, excludedFields);
        hitInARow = 0;
    }

    /**
     * Method checking is it possible to shoot the specific square
     * @param x row of the shoot
     * @param y column of the shoot
     * @param ocean player's board
     * @return true if it's possible to shoot
     */

    public boolean checkIfCanShotThere(int x, int y, Square[][] ocean) {
        for (int i = 0; i < excludedFields.size(); i++) {
            if (excludedFields.get(i)[0] == x && excludedFields.get(i)[1] == y) {
                return true;
            }
        }
        return ocean[x][y].getSquareStatus().equals(SquareStatus.HIT) || ocean[x][y].getSquareStatus().equals(SquareStatus.MISSED);
    }

    @Override
    public void handleShot(Board enemyBoard, Board yourGuesses, Player enemyPlayer) {
        int[] cords = new int[2];
        if (hitInARow > 0) {
            if (getDirection() == null) {
                setDirection(drawDirection());
            }
            List<int[]> possibleMoves = possibleMoves(getDirection(), getShiftValue());
            if (possibleMoves.isEmpty()) {
                swapShiftNumber();
                possibleMoves = possibleMoves(getDirection(), getShiftValue());
                if (possibleMoves.isEmpty()) {
                    swapDirection();
                    possibleMoves = possibleMoves(getDirection(), getShiftValue());
                }

            }
            try {
                cords = drawFromPossibleMoves(possibleMoves);
            } catch (Exception e) {
                handleShot(enemyBoard, yourGuesses, enemyPlayer);
            }
            if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.SHIP)) {
                setClassFieldsAfterMultiHit(cords);
            } else if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.EMPTY)) {
                setClassFieldsAfterHitMiss(cords);
            }
        } else {
            cords = getValidShotCords();
            if (checkIfCanShotThere(cords[0], cords[1], yourGuesses.getOcean())) {
                handleShot(enemyBoard, yourGuesses, enemyPlayer);
            }
            if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.SHIP)) {
                setClassFieldsAfterSingleHit(cords);
            } else if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.EMPTY)) {
                setClassFieldsAfterSingleMiss(cords);
            }

        }
        markShot(cords[0], cords[1], enemyBoard.getOcean(), yourGuesses.getOcean());
        List<Square> squares = shipFields(cords[0], cords[1], enemyPlayer);
        if (!squares.isEmpty() && isPossibleSink(squares)) {
            sinkShip(squares, yourGuesses);
            setClassFieldsAfterSinkShip(squares);

        }
    }


}
