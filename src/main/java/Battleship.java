import board.Board;
import board.SquareStatus;
import logic.Game;
import player.ComputerPlayer;
import player.EasyComputer;
import player.Player;
import player.UserPlayer;
import ship.ShipType;
import utilities.Display;
import utilities.Input;

public class Battleship {
    public static void main(String[] args) {
        Game game = new Game();
        game.initGame();
    }
}

