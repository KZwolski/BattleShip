package player;


import board.Board;
import board.Square;
import board.SquareStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class HardComputer extends Player {

    List<int[]> excludedFields = new ArrayList<>();
    List<int[]> diagonalFields;

    private int[] lastShotCords;

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


    public HardComputer(Board board) {
        this.playerName = "Computer";
        this.board = board;
        this.isAlive = true;
        this.score = 0;
        this.shiftValue = 1;
        this.diagonalFields = initDiagonalFields();
    }

    public List<int[]> initDiagonalFields() {
        List<int[]> diagonalFields = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            {
                diagonalFields.add(new int[]{i, i});
                diagonalFields.add(new int[]{9 - i, i});
            }
        }
        return diagonalFields;
    }

    public int[] drawFromDiagonalFields() {
        int randomNUmber = ThreadLocalRandom.current().nextInt(0, diagonalFields.size());
        return diagonalFields.get(randomNUmber);
    }

    public void removeCordsFromList(int[] cords) {
        for (int i = 0; i < diagonalFields.size(); i++) {
            if (diagonalFields.get(i)[0] == cords[0] && diagonalFields.get(0)[1] == cords[1]) {
                diagonalFields.remove(i);
            }
        }
    }

    public void deleteExcludedFieldsFromDiagonalList() {
        for (int i = 0; i < diagonalFields.size(); i++) {
            for (int[] excludedField : excludedFields) {
                try {
                    if (diagonalFields.get(i)[0] == excludedField[0] && diagonalFields.get(i)[1] == excludedField[1]) {
                        diagonalFields.remove(i);
                    }
                } catch (Exception ignored) {

                }
            }
        }
    }

    @Override
    public int[] getValidShotCords() {
        int[] cords = drawCoordinates();
        while (!input.validateCords(cords[0], cords[1])) {
            cords = drawCoordinates();
        }
        if (checkIfCordsExcluded(cords[0], cords[1])) {
            return cords;
        } else {
            return getValidShotCords();
        }
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


    public List<int[]> possibleMoves(String direction, int shiftValue) {
        List<int[]> possibleMoves = new ArrayList<>();
        if (Objects.equals(direction, "V")) {
            if (input.validateCords(lastShotCords[0] + hitInARow * (shiftValue), lastShotCords[1]) && checkIfCordsExcluded(lastShotCords[0] + hitInARow * (shiftValue), lastShotCords[1])) {
                possibleMoves.add(new int[]{lastShotCords[0] + hitInARow * (shiftValue), lastShotCords[1]});
            }
        } else if (Objects.equals(direction, "H")) {
            if (input.validateCords(lastShotCords[0], lastShotCords[1] + hitInARow * (shiftValue)) && checkIfCordsExcluded(lastShotCords[0], lastShotCords[1] + hitInARow * (shiftValue))) {
                possibleMoves.add(new int[]{lastShotCords[0], lastShotCords[1] + hitInARow * (shiftValue)});
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


    public void setClassFieldsAfterMultiHit(int[] cords) {
        excludedFields.add(cords);
        hitInARow += 1;
    }

    public void setClassFieldsAfterHitMiss(int[] cords) {
        excludedFields.add(cords);
        hitInARow = 1;
    }

    public void setClassFieldsAfterSingleHit(int[] cords) {
        excludedFields.add(cords);
        lastShotCords = cords;
        hitInARow += 1;
    }

    public void setClassFieldsAfterSingleMiss(int[] cords) {
        excludedFields.add(cords);
    }

    public void setClassFieldsAfterSinkShip(List<Square> squares) {
        excludeSquaresAroundShip(squares, excludedFields);
        hitInARow = 0;
    }

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
            if (diagonalFields.isEmpty()) {
                cords = getValidShotCords();
            } else {
                deleteExcludedFieldsFromDiagonalList();
                cords = drawFromDiagonalFields();
                removeCordsFromList(cords);
            }
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
