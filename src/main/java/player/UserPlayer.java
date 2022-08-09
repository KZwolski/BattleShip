package player;

import board.Board;
import board.Square;
import board.SquareStatus;
import ship.Ship;
import utilities.Input;

import java.util.*;


public class UserPlayer implements Player{

    private String playerName;

    private Board board;

    private boolean isAlive;
    private int score;

    private Input input = new Input();

    private List<Ship> playerShips = new ArrayList<>();
    @Override
    public List<Ship> getPlayerShips() {
        return playerShips;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addShips(Ship ship){
        this.playerShips.add(ship);
    }
    public UserPlayer(String playerName, Board board){
        this.playerName = playerName;
        this.board = board;
        this.isAlive = true;
        this.score = 0;
    }
    public boolean isAlive(){
        for(int i=0; i<playerShips.size(); i++){
            for(int j = 0; j<playerShips.get(i).getOccupiedCells().size(); j++){
                if(!playerShips.get(i).getOccupiedCells().get(j).getSquareStatus().equals(SquareStatus.SINK)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void handleShot(Square[][] enemyBoard, Square[][] playersBoard, Player enemyPlayer,Board board){
        int[] cords = input.getCoordinates();
        while (!input.validateCords(cords[0], cords[1])){
            cords = input.getCoordinates();
        }
        if(checkSquareStatus(cords[0], cords[1],playersBoard)){
            handleShot(enemyBoard,playersBoard, enemyPlayer,board);
        }
        markShot(cords[0], cords[1], enemyBoard, playersBoard);
        List<Square> squares = shipFields(cords[0], cords[1],enemyPlayer);
        if(isPossibleSink(squares)){
            sinkShip(squares, board);
        }
    }

    @Override
    public void markShot(int x, int y, Square[][] enemyBoard, Square[][] playersBoard){
        if(enemyBoard[x][y].getSquareStatus().equals(SquareStatus.SHIP)){
        enemyBoard[x][y].setSquareStatus(SquareStatus.HIT);
        playersBoard[x][y].setSquareStatus(SquareStatus.HIT);
        } else {
            enemyBoard[x][y].setSquareStatus(SquareStatus.MISSED);
            playersBoard[x][y].setSquareStatus(SquareStatus.MISSED);
        }
    }

    @Override
    public boolean isPossibleSink(List<Square> squares){
        for (Square square : squares) {
            if (!square.getSquareStatus().equals(SquareStatus.HIT)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void sinkShip(List<Square> squares, Board board){
        for (Square square : squares) {
            square.setSquareStatus(SquareStatus.SINK);
            board.getOcean()[square.getX()][square.getY()].setSquareStatus(SquareStatus.SINK);
        }
    }
    @Override
    public List<Square> shipFields (int x, int y,Player player){
        List<Square> squares = new ArrayList<>();
        for(int i = 0; i < player.getPlayerShips().size(); i++){
            for(int j = 0; j < player.getPlayerShips().get(i).getOccupiedCells().size(); j++){
                if(player.getPlayerShips().get(i).getOccupiedCells().get(j).getX() == x && player.getPlayerShips().get(i).getOccupiedCells().get(j).getY() == y ){
                    squares = player.getPlayerShips().get(i).getOccupiedCells();

                }
            }
        }
        return squares;
    }
    @Override
    public boolean checkSquareStatus(int x, int y,Square[][] ocean){

        return ocean[x][y].getSquareStatus().equals(SquareStatus.HIT) || ocean[x][y].getSquareStatus().equals(SquareStatus.MISSED);
    }






}
