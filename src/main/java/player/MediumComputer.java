package player;

import board.Board;
import board.Square;
import board.SquareStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class MediumComputer extends Player {
    List<int[]> excludedFields = new ArrayList<>();

    private int[] lastShotCords;

    private SquareStatus lastShotStatus;

    private String Direction;

    private int shiftValue;

    private int hitInARow;

    public int getShiftValue() {
        return shiftValue;
    }

    public void setShiftValue(int shiftValue) {
        this.shiftValue = shiftValue;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }


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

    public int[] drawCoordinates() {
        int min = 0;
        int max = 9;
        return new int[]{ThreadLocalRandom.current().nextInt(min, max + 1), ThreadLocalRandom.current().nextInt(min, max + 1)};

    }


    public String drawDirection() {
        String[] values = {"V", "H"};
        return values[ThreadLocalRandom.current().nextInt(0, values.length)];

    }

    public boolean checkIfCordsExcluded(int x, int y) {
        for (int i = 0; i < excludedFields.size(); i++) {
            if (excludedFields.get(i)[0] == x && excludedFields.get(i)[1] == y) {
                return false;
            }
        }
        return true;
    }


    public List<int[]> possibleMoves(Board enemyBoard, String direction, int shiftValue) {
        List<int[]> possibleMoves = new ArrayList<>();
        if (Objects.equals(direction, "V")) {
            if (shiftValue == 1) {
                if (input.validateCords(lastShotCords[0] + hitInARow, lastShotCords[1]) && checkIfCordsExcluded(lastShotCords[0] + hitInARow, lastShotCords[1])) {
                    possibleMoves.add(new int[]{lastShotCords[0] + hitInARow, lastShotCords[1]});
                }
            } else if (shiftValue == -1) {
                if (input.validateCords(lastShotCords[0] - hitInARow, lastShotCords[1]) && checkIfCordsExcluded(lastShotCords[0] - hitInARow, lastShotCords[1])) {
                    possibleMoves.add(new int[]{lastShotCords[0] - hitInARow, lastShotCords[1]});
                }
            }
        } else if (Objects.equals(direction, "H")) {
            if (shiftValue == 1) {
                if (input.validateCords(lastShotCords[0], lastShotCords[1] + hitInARow) && checkIfCordsExcluded(lastShotCords[0], lastShotCords[1] + hitInARow)) {
                    possibleMoves.add(new int[]{lastShotCords[0], lastShotCords[1] + hitInARow});
                }
            } else if (shiftValue == -1) {
                if (input.validateCords(lastShotCords[0], lastShotCords[1] - hitInARow) && checkIfCordsExcluded(lastShotCords[0], lastShotCords[1] - hitInARow)) {
                    possibleMoves.add(new int[]{lastShotCords[0], lastShotCords[1] - hitInARow});
                }
            }
        }

        return possibleMoves;
    }

    public void swapDirection() {
        if (Objects.equals(getDirection(), "H")) {
            setDirection("V");
        } else if (Objects.equals(getDirection(), "V")) {
            setDirection("H");
        }
    }

    public void swapShiftNumber() {
        if (Objects.equals(getShiftValue(), 1)) {
            setShiftValue(-1);
        } else if (Objects.equals(getShiftValue(), -1)) {
            setShiftValue(1);
        }
    }

    public int[] drawFromPossibleMoves(List<int[]> possibleMoves) {
        int randomNUmber = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
        return new int[]{possibleMoves.get(randomNUmber)[0], possibleMoves.get(randomNUmber)[1]};

    }

    @Override
    public void handleShot(Board enemyBoard, Board yourGuesses, Player enemyPlayer) {
        int[] cords;
        if (hitInARow > 0) {
            if (getDirection() == null) {
                setDirection(drawDirection());
            }
            List<int[]> possibleMoves = possibleMoves(enemyBoard, getDirection(), getShiftValue());
            if (possibleMoves.isEmpty()) {
                swapShiftNumber();
                possibleMoves = possibleMoves(enemyBoard, getDirection(), getShiftValue());
                if (possibleMoves.isEmpty()) {
                    swapDirection();
                    possibleMoves = possibleMoves(enemyBoard, getDirection(), getShiftValue());
                }

            }
            cords = drawFromPossibleMoves(possibleMoves);
            if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.SHIP)) {
                excludedFields.add(cords);
                hitInARow += 1;
                lastShotStatus = SquareStatus.HIT;
            } else if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.EMPTY)) {
                excludedFields.add(cords);
                hitInARow = 1;
                lastShotStatus = SquareStatus.MISSED;
            }
        } else {
            cords = getValidShotCords();
            if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.SHIP)) {
                excludedFields.add(cords);
                lastShotStatus = SquareStatus.HIT;
                lastShotCords = cords;
                hitInARow += 1;
            } else if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.EMPTY)) {
                excludedFields.add(cords);
                lastShotStatus = SquareStatus.MISSED;
            }

        }
        if (checkSquareStatus(cords[0], cords[1], yourGuesses.getOcean())) {
            handleShot(enemyBoard, yourGuesses, enemyPlayer);
        }
        markShot(cords[0], cords[1], enemyBoard.getOcean(), yourGuesses.getOcean());
        List<Square> squares = shipFields(cords[0], cords[1], enemyPlayer);
        if (!squares.isEmpty() && isPossibleSink(squares)) {
            sinkShip(squares, yourGuesses);
            hitInARow = 0;
            lastShotStatus = SquareStatus.MISSED;

        }
    }
}
