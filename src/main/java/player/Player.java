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

public abstract class Player {
    protected String playerName;

    protected Board board;

    protected boolean isAlive;
    protected int score;

    protected Input input = new Input();

    public int getScore() {
        return score;
    }

    protected List<Ship> playerShips = new ArrayList<>();

    public List<Ship> getPlayerShips() {
        return playerShips;
    }

    public void addShips(Ship ship) {
        this.playerShips.add(ship);
    }

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

    public String getPlayerName() {
        return playerName;
    }

    public abstract int[] getValidShotCords();

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

    ;

    public void markShot(int x, int y, Square[][] enemyBoard, Square[][] playersBoard) {
        if (enemyBoard[x][y].getSquareStatus().equals(SquareStatus.SHIP)) {
            enemyBoard[x][y].setSquareStatus(SquareStatus.HIT);
            playersBoard[x][y].setSquareStatus(SquareStatus.HIT);
            score += 5;
        } else if (enemyBoard[x][y].getSquareStatus().equals(SquareStatus.EMPTY)) {
            enemyBoard[x][y].setSquareStatus(SquareStatus.MISSED);
            playersBoard[x][y].setSquareStatus(SquareStatus.MISSED);
            score -= 5;

        }
    }

    ;

    public void sinkShip(List<Square> squares, Board board) {
        for (Square square : squares) {
            square.setSquareStatus(SquareStatus.SINK);
            board.getOcean()[square.getX()][square.getY()].setSquareStatus(SquareStatus.SINK);
            score += ShipType.values().length;

        }
    }

    ;

    public boolean isPossibleSink(List<Square> squares) {
        for (Square square : squares) {
            if (!square.getSquareStatus().equals(SquareStatus.HIT)) {
                return false;
            }
        }
        return true;
    }

    ;

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


    public boolean checkSquareStatus(int x, int y, Square[][] ocean) {
        return ocean[x][y].getSquareStatus().equals(SquareStatus.HIT) || ocean[x][y].getSquareStatus().equals(SquareStatus.MISSED);
    }
    public void excludeSquaresAroundShip(List<Square> squares,List<int[]> excludedFields) {
        excludeHorizontalAndVerticalAroundShip(squares,excludedFields);
        excludeDiagonalAroundShip(squares,excludedFields);

    }

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

    public int[] drawFromPossibleMoves(List<int[]> possibleMoves) {
        int randomNUmber = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
        return new int[]{possibleMoves.get(randomNUmber)[0], possibleMoves.get(randomNUmber)[1]};

    }



}
