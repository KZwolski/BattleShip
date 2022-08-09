package player;

import board.Board;
import ship.Ship;
import java.util.*;


public class UserPlayer implements Player{

    private String playerName;

    private Board board;

    private List<Ship> playerShips = new ArrayList<>();
    public boolean isAlive(){
        return true;
    }




}
