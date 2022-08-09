package board;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import player.Player;
import player.UserPlayer;
import ship.Ship;
import ship.ShipType;
import utilities.Display;
import utilities.Input;

public abstract class BoardFactory {

    Input input = new Input();
    Display printer = new Display();

    public void randomPlacement(Board board, ShipType shipType, Player userPlayer) {
        String[] directions = {"V", "H"};
        String direction = directions[new Random().nextInt(2)];
        int[] cords = drawCoordinates();
        while (checkAreThereShips(shipType.getLength(), direction, board, cords)) {
            cords = drawCoordinates();
        }
        markSquareAsShip(shipType.getLength(), direction, board, cords, userPlayer);
    }

    public void manualPlacement(Board board, ShipType shipType, Player userPlayer) {
        printer.consolePrint("You are now placing: " + shipType.toString() + " of length: " + shipType.getLength());
        int[] coordinates = input.getCoordinates();
        String direction = input.getDirection();
        while (checkAreThereShips(shipType.getLength(), direction, board, coordinates)) {
            System.out.println("You cannot place ship there :(, try again! ");
            coordinates = input.getCoordinates();
            direction = input.getDirection();
        }
        markSquareAsShip(shipType.getLength(), direction, board, coordinates, userPlayer);

    }

    public void markSquareAsShip(int shipSize, String direction, Board board, int[] coordinates, Player userPlayer) {
        Square[][] ocean = board.getOcean();
        ArrayList<Square> squares = new ArrayList<>();
        for (int i = 0; i < shipSize; i++) {
            if (Objects.equals(direction, "V")) {
                ocean[coordinates[0] + i][coordinates[1]].setSquareStatus(SquareStatus.SHIP);
                squares.add(ocean[coordinates[0] + i][coordinates[1]]);
            } else if (Objects.equals(direction, "H")) {
                ocean[coordinates[0]][coordinates[1] + i].setSquareStatus(SquareStatus.SHIP);
                squares.add(ocean[coordinates[0]][coordinates[1]+i]);
            }
        }
        Ship ship = new Ship(squares);
        userPlayer.addShips(ship);
        for(int i = 0; i<ship.getOccupiedCells().size(); i++){
            System.out.println("dupa\n" + ship.getOccupiedCells().get(i).getX()+" "+ship.getOccupiedCells().get(i).getY());
        }
    }



    public int[] drawCoordinates() {
        int min = 0;
        int max = 9;
        return new int[]{ThreadLocalRandom.current().nextInt(min, max + 1), ThreadLocalRandom.current().nextInt(min, max + 1)};

    }


    public boolean checkAreThereShips(int shipSize, String direction, Board board, int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        for (int i = 0; i < shipSize; i++) {
            if (direction == "V") {
                x += i;
            } else if (direction == "H") {
                y += i;
            }
            if (input.validateCords(x, y)) {
                if (checkEveryDirectionNeighbours(board, x, y)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean checkEveryDirectionNeighbours(Board board, int x, int y) {
        return checkEdgeNeighbours(board, x, y) || checkHorizontalNeighbours(board, x, y) || checkVerticalNeighbours(board, x, y);
    }

    public boolean checkVerticalNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (input.validateCords(x - 1, y) && ocean[x - 1][y].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x + 1, y) && ocean[x + 1][y].getSquareStatus().equals(SquareStatus.SHIP));
    }

    public boolean checkHorizontalNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (input.validateCords(x, y - 1) && ocean[x][y - 1].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x, y + 1) && ocean[x][y + 1].getSquareStatus().equals(SquareStatus.SHIP));
    }

    public boolean checkEdgeNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (input.validateCords(x - 1, y - 1) && ocean[x - 1][y - 1].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x - 1, y + 1) && ocean[x - 1][y + 1].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x + 1, y - 1) && ocean[x + 1][y - 1].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x + 1, y + 1) && ocean[x + 1][y + 1].getSquareStatus().equals(SquareStatus.SHIP));
    }


}

