package player;

import board.Board;
import board.Square;
import board.SquareStatus;
import ship.Ship;
import ship.ShipType;
import utilities.Input;

import java.util.*;


public class UserPlayer implements Player{

    private String playerName;

    private Board board;

    private boolean isAlive;
    private int score;

    private Input input = new Input();

    private List<Ship> playerShips = new ArrayList<>();

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
                if(!playerShips.get(i).getOccupiedCells().get(j).getSquareStatus().toString().equals("SINK")){
                    return true;
                }
            }
        }
        return false;
    }

    public void handleShot(){
        int[] cords = input.getCoordinates();
        while (!input.validateCords(cords[0], cords[1])){
            cords = input.getCoordinates();
        }
        if(checkSquareStatus(cords[0], cords[1])){
            handleShot();
        }
        markShot(cords[0], cords[1]);
        List<Square> squares = shipFields(cords[0], cords[1]);
        if(isPossibleSink(squares)){
            sinkShip(squares);
        }
    }

    public void markShot(int x, int y){
        Square[][] ocean = board.getOcean();
        if(Objects.equals(ocean[x][y].getSquareStatus().toString(), "SHIP")){
        ocean[x][y].setSquareStatus(SquareStatus.valueOf("HIT"));
            score += 5;
        } else {
            ocean[x][y].setSquareStatus(SquareStatus.valueOf("MISSED"));
            score -= 5;
        }
    }

    public boolean isPossibleSink(List<Square> squares){
        for(int i =0; i<squares.size(); i++){
            if(!squares.get(i).getSquareStatus().toString().equals("HIT")){
                return false;
            }
        }
        return true;
    }

    public void sinkShip(List<Square> squares){
        for(int i =0; i<squares.size(); i++){
            squares.get(i).setSquareStatus(SquareStatus.valueOf("SINK"));
            score += ShipType.values().length;
        }
    }

    public List<Square> shipFields (int x, int y){
        List<Square> squares = new ArrayList<>();
        for(int i = 0; i < getPlayerShips().size(); i++){
            for(int j = 0; j < getPlayerShips().get(i).getOccupiedCells().size(); j++){
                if(getPlayerShips().get(i).getOccupiedCells().get(j).getX() == x && getPlayerShips().get(i).getOccupiedCells().get(j).getY() == y ){
                    squares = getPlayerShips().get(i).getOccupiedCells();

                }
            }
        }
        return squares;
    }

    public boolean checkSquareStatus(int x, int y){
        Square[][] ocean = board.getOcean();

        return Objects.equals(ocean[x][y].getSquareStatus().toString(), "HIT") && Objects.equals(ocean[x][y].getSquareStatus().toString(), "MISSED");
    }






}
