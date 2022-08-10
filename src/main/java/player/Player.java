package player;

import board.Board;
import board.Square;
import board.SquareStatus;
import ship.Ship;
import ship.ShipType;
import utilities.Input;

import java.util.ArrayList;
import java.util.List;

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

    public void setScore(int score) {
        this.score = score;
    }

    public void addShips(Ship ship){
        this.playerShips.add(ship);
    }
    public boolean isStillAlive(){
        for(int i=0; i<playerShips.size(); i++){
            for(int j = 0; j<playerShips.get(i).getOccupiedCells().size(); j++){
                if(!playerShips.get(i).getOccupiedCells().get(j).getSquareStatus().equals(SquareStatus.SINK)){
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

    public  void handleShot(Board enemyBoard, Board yourGuesses, Player enemyPlayer){
        int[] cords = getValidShotCords();
        if(checkSquareStatus(cords[0], cords[1],yourGuesses.getOcean())){
            handleShot(enemyBoard,yourGuesses, enemyPlayer);
        }
        markShot(cords[0], cords[1], enemyBoard.getOcean(), yourGuesses.getOcean());
        List<Square> squares = shipFields(cords[0], cords[1],enemyPlayer);
        if(isPossibleSink(squares)){
            sinkShip(squares, yourGuesses);
        }
    };

    public  void markShot(int x, int y,Square[][] enemyBoard, Square[][] playersBoard){
        if(enemyBoard[x][y].getSquareStatus().equals(SquareStatus.SHIP)){
            enemyBoard[x][y].setSquareStatus(SquareStatus.HIT);
            playersBoard[x][y].setSquareStatus(SquareStatus.HIT);
            score += 5;
        } else {
            enemyBoard[x][y].setSquareStatus(SquareStatus.MISSED);
            playersBoard[x][y].setSquareStatus(SquareStatus.MISSED);
            score -= 5;

        }
    };

    public  void sinkShip(List<Square> squares,Board board){
        for (Square square : squares) {
            square.setSquareStatus(SquareStatus.SINK);
            board.getOcean()[square.getX()][square.getY()].setSquareStatus(SquareStatus.SINK);
            score += ShipType.values().length;

        }
    };

    public boolean isPossibleSink(List<Square> squares){
        for (Square square : squares) {
            if (!square.getSquareStatus().equals(SquareStatus.HIT)) {
                return false;
            }
        }
        return true;
    };

    public  List<Square> shipFields(int x, int y,Player player){
        List<Square> squares = new ArrayList<>();
        for(int i = 0; i < player.getPlayerShips().size(); i++){
            for(int j = 0; j < player.getPlayerShips().get(i).getOccupiedCells().size(); j++){
                if(player.getPlayerShips().get(i).getOccupiedCells().get(j).getX() == x && player.getPlayerShips().get(i).getOccupiedCells().get(j).getY() == y ){
                    squares = player.getPlayerShips().get(i).getOccupiedCells();

                }
            }
        }
        return squares;
    };

    public  boolean checkSquareStatus(int x, int y,Square[][] ocean){
        return ocean[x][y].getSquareStatus().equals(SquareStatus.HIT) && ocean[x][y].getSquareStatus().equals(SquareStatus.MISSED);
    };


}
