package player;

import board.Board;
import ship.Ship;
import utilities.Input;

import java.util.*;


public class UserPlayer implements Player{

    private String playerName;

    private Board board;

    private Input input = new Input();

    private List<Ship> playerShips = new ArrayList<>();


    public void addShips(Ship ship){
        this.playerShips.add(ship);
    }
    public UserPlayer(String playerName, Board board){
        this.playerName = playerName;
        this.board = board;
    }
    public boolean isAlive(){
        return true;
    }

    public void getShotCord(){

        input.getCoordinates();
    }






}
