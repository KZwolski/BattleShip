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

    public UserPlayer(){

    }
    public boolean isAlive(){
        return true;
    }

    public void getShotCord(){

        input.getCoordinates();
    }






}
