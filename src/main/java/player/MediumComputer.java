package player;

import board.Board;
import board.Square;
import board.SquareStatus;
import ship.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class MediumComputer extends Player {
    List<int[]> excludedFields = new ArrayList<>();

    private int[] lastShotCords;

    public int[] getLastShotCords() {
        return lastShotCords;
    }

    public MediumComputer(Board board) {
        this.playerName = "Computer";
        this.board = board;
        this.isAlive = true;
        this.score = 0;
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

    public int drawShiftValue() {
        int[] values = {1, -1};
        return values[ThreadLocalRandom.current().nextInt(0, values.length + 1)];

    }

    public String drawDirection() {
        String[] values = {"V", "H"};
        return values[ThreadLocalRandom.current().nextInt(0, values.length)];

    }

    public int[] shotAroundShip() {
        int[] coordinates = new int[2];
        int shiftValue = drawShiftValue();
        String direction = drawDirection();
        if (Objects.equals(direction, "V")) {
            coordinates[0] = lastShotCords[0] + shiftValue;
            coordinates[1] = lastShotCords[1];
        } else if (Objects.equals(direction, "H")) {
            coordinates[0] = lastShotCords[0];
            coordinates[1] = lastShotCords[1] + shiftValue;

        }
        return coordinates;
    }
    public boolean checkIfCordsExcluded(int [] coords){
        for(int i=0; i<excludedFields.size(); i++){
            if(excludedFields.get(i)[0] == coords[0] && excludedFields.get(i)[1] == coords[1]){
                return true;
            }
        }return false;
    }

    @Override
    public void handleShot(Board enemyBoard, Board yourGuesses, Player enemyPlayer) {
        int[] cords;
        if (getLastShotCords() == null) {
            cords = getValidShotCords();
        }else {
            cords = shotAroundShip();
        }
        if (checkSquareStatus(cords[0], cords[1], yourGuesses.getOcean()) && checkIfCordsExcluded(cords)) {
            handleShot(enemyBoard, yourGuesses, enemyPlayer);
        }
        markShot(cords[0], cords[1], enemyBoard.getOcean(), yourGuesses.getOcean());
        if (enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.HIT)) {
            lastShotCords = cords;
        }else if(enemyBoard.getOcean()[cords[0]][cords[1]].getSquareStatus().equals(SquareStatus.MISSED)){
            excludedFields.add(cords);
        }
        List<Square> squares = shipFields(cords[0], cords[1], enemyPlayer);
        if (isPossibleSink(squares)) {
            sinkShip(squares, yourGuesses);
        }
    }
}
