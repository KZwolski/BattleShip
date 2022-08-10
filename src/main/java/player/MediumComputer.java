package player;

import board.Square;
import ship.Ship;

import java.util.List;

public class MediumComputer extends Player{

    @Override
    public int[] getValidShotCords() {
        return new int[0];
    }
}
