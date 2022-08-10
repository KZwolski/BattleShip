package player;

import board.Board;
import board.Square;
import board.SquareStatus;
import ship.Ship;
import utilities.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class ComputerPlayer implements Player {
    Input input = new Input();
    protected String playerName;
    protected Board board;
    protected boolean isAlive;
    protected int score;
    protected List<Ship> computerShips = new ArrayList<>();
    @Override
    public List<Ship> getPlayerShips() {
        return computerShips;
    }


    @Override
    public void addShips(Ship ship) {
        this.computerShips.add(ship);

    }
    public int[] drawCoordinates() {
        int min = 0;
        int max = 9;
        return new int[]{ThreadLocalRandom.current().nextInt(min, max + 1), ThreadLocalRandom.current().nextInt(min, max + 1)};

    }

    @Override
    public void handleShot(Board enemyBoard, Board yourGuesses, Player enemyPlayer) {
        int[] cords = drawCoordinates();
        while (!input.validateCords(cords[0], cords[1])) {
            cords = drawCoordinates();
        }
        if (checkSquareStatus(cords[0], cords[1],yourGuesses.getOcean())) {
            handleShot(enemyBoard,yourGuesses,enemyPlayer);
        }
        markShot(cords[0], cords[1],enemyBoard.getOcean(),board.getOcean());
        List<Square> squares = shipFields(cords[0], cords[1],enemyPlayer);
        if (isPossibleSink(squares)) {
            sinkShip(squares, board);
        }
    }

    @Override
    public void markShot(int x, int y,Square[][] enemyBoard, Square[][] playersBoard) {
        if (enemyBoard[x][y].getSquareStatus().equals(SquareStatus.SHIP)) {
            enemyBoard[x][y].setSquareStatus(SquareStatus.HIT);
            playersBoard[x][y].setSquareStatus(SquareStatus.HIT);
        } else {
            enemyBoard[x][y].setSquareStatus(SquareStatus.MISSED);
            playersBoard[x][y].setSquareStatus(SquareStatus.MISSED);
        }

    }

    @Override
    public void sinkShip(List<Square> squares,Board board) {
        for (Square square : squares) {
            square.setSquareStatus(SquareStatus.SINK);
            board.getOcean()[square.getX()][square.getY()].setSquareStatus(SquareStatus.SINK);
        }

    }

    @Override
    public boolean isPossibleSink(List<Square> squares) {
        for (Square square : squares) {
            if (!square.getSquareStatus().equals(SquareStatus.HIT)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Square> shipFields(int x, int y,Player player) {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < getPlayerShips().size(); i++) {
            for (int j = 0; j < getPlayerShips().get(i).getOccupiedCells().size(); j++) {
                if (getPlayerShips().get(i).getOccupiedCells().get(j).getX() == x && getPlayerShips().get(i).getOccupiedCells().get(j).getY() == y) {
                    squares = getPlayerShips().get(i).getOccupiedCells();

                }
            }
        }
        return squares;
    }

    @Override
    public boolean checkSquareStatus(int x, int y,Square[][] ocean) {

        return ocean[x][y].getSquareStatus().equals(SquareStatus.HIT) && ocean[x][y].getSquareStatus().equals(SquareStatus.MISSED);
    }


    public String getPlayerName() {
        return playerName;
    }

    public boolean isStillAlive(){
        for(int i=0; i<computerShips.size(); i++){
            for(int j = 0; j<computerShips.get(i).getOccupiedCells().size(); j++){
                if(!computerShips.get(i).getOccupiedCells().get(j).getSquareStatus().equals(SquareStatus.SINK)){
                    return true;
                }
            }
        }
        return false;
    }


}
