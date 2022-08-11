package board;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import player.Player;
import ship.Ship;
import ship.ShipType;
import utilities.Display;
import utilities.Input;

/**
 * Abstract class handling manual and random ship placement on the user's board
 */
public abstract class BoardFactory {
    /**
     * Create instance of Input class to handle user's input
     *
     * @see Input
     */
    Input input = new Input();
    /**
     * Create instance of Display class to handle displaying messages
     *
     * @see Display
     */
    Display printer = new Display();

    /**
     * Method handling random ship placement
     * @param board board on which ships will be placed
     * @param shipType type of the ship that is going to be placed
     * @param userPlayer player that is currently placing ships
     */
    public void randomPlacement(Board board, ShipType shipType, Player userPlayer) {
        String[] directions = {"V", "H"};
        String direction = directions[new Random().nextInt(2)];
        int[] cords = drawCoordinates();
        while (checkAreThereShips(shipType.getLength(), direction, board, cords)) {
            cords = drawCoordinates();
        }
        markSquareAsShip(shipType.getLength(), direction, board, cords, userPlayer);
    }
    /**
     * Method handling manual ship placement
     * @param board board on which ships will be placed
     * @param shipType type of the ship that is going to be placed
     * @param userPlayer player that is currently placing ships
     */
    public void manualPlacement(Board board, ShipType shipType, Player userPlayer) {
        printer.consolePrint("You are now placing: " + shipType.toString() + " of length: " + shipType.getLength());
        int[] coordinates = input.getCoordinates();
        String direction = input.getDirection();
        while (checkAreThereShips(shipType.getLength(), direction, board, coordinates)) {
            printer.consolePrint("You cannot place ship there :(, try again! ");
            coordinates = input.getCoordinates();
            direction = input.getDirection();
        }
        markSquareAsShip(shipType.getLength(), direction, board, coordinates, userPlayer);

    }

    /**
     * Method responsible for changing given square status to SHIP
     * @param shipSize size of the ship to be placed
     * @param direction direction in which the ship will be placed
     * @param board board on which ship will be placed
     * @param coordinates coordinates to place the ship at
     * @param userPlayer player that is currently placing ship
     */
    public void markSquareAsShip(int shipSize, String direction, Board board, int[] coordinates, Player userPlayer) {
        Square[][] ocean = board.getOcean();
        ArrayList<Square> squares = new ArrayList<>();
        for (int i = 0; i < shipSize; i++) {
            if (Objects.equals(direction, "V")) {
                ocean[coordinates[0] + i][coordinates[1]].setSquareStatus(SquareStatus.SHIP);
                squares.add(ocean[coordinates[0] + i][coordinates[1]]);
            } else if (Objects.equals(direction, "H")) {
                ocean[coordinates[0]][coordinates[1] + i].setSquareStatus(SquareStatus.SHIP);
                squares.add(ocean[coordinates[0]][coordinates[1] + i]);
            }
        }
        Ship ship = new Ship(squares);
        userPlayer.addShips(ship);
    }

    /**
     * Method for drawing random coordinates in the board range
     *
     * @return array of ints representing coordinates
     */
    public int[] drawCoordinates() {
        int min = 0;
        int max = 9;
        return new int[]{ThreadLocalRandom.current().nextInt(min, max + 1), ThreadLocalRandom.current().nextInt(min, max + 1)};

    }

    /**
     * Method used for checking if are ships around ship placement
     * @param shipSize size of the ship that will be placed
     * @param direction direction in which ship will be placed
     * @param board  board on which ship will be placed
     * @param coordinates starting coordinates of ship that will be placed
     *
     * @return true if placement is not possible
     */
    public boolean checkAreThereShips(int shipSize, String direction, Board board, int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        for (int i = 0; i < shipSize; i++) {
            if (Objects.equals(direction, "V")) {
                x += i;
            } else if (Objects.equals(direction, "H")) {
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

    /**
     * Method checking for other ships on every direction around given cords
     * @param board board on which ship will be placed
     * @param x row of placement
     * @param y column of placement
     *
     * @return true if other ship was found
     */
    public boolean checkEveryDirectionNeighbours(Board board, int x, int y) {
        return checkBoxItSelf(board, x, y) || checkEdgeNeighbours(board, x, y) || checkHorizontalNeighbours(board, x, y)
                || checkVerticalNeighbours(board, x, y);
    }
    /**
     * Method checking for other ships on given cords
     * @param board board on which ship will be placed
     * @param x row of placement
     * @param y column of placement
     *
     * @return true if other ship was found
     */
    public boolean checkBoxItSelf(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (input.validateCords(x, y) && ocean[x][y].getSquareStatus().equals(SquareStatus.SHIP));
    }
    /**
     * Method checking for other ships on vertical direction around given cords
     * @param board board on which ship will be placed
     * @param x row of placement
     * @param y column of placement
     *
     * @return true if other ship was found
     */
    public boolean checkVerticalNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (input.validateCords(x - 1, y) && ocean[x - 1][y].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x + 1, y) && ocean[x + 1][y].getSquareStatus().equals(SquareStatus.SHIP));
    }
    /**
     * Method checking for other ships on horizontal direction around given cords
     * @param board board on which ship will be placed
     * @param x row of placement
     * @param y column of placement
     *
     * @return true if other ship was found
     */
    public boolean checkHorizontalNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (input.validateCords(x, y - 1) && ocean[x][y - 1].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x, y + 1) && ocean[x][y + 1].getSquareStatus().equals(SquareStatus.SHIP));
    }
    /**
     * Method checking for other ships on diagonal direction around given cords
     * @param board board on which ship will be placed
     * @param x row of placement
     * @param y column of placement
     *
     * @return true if other ship was found
     */
    public boolean checkEdgeNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (input.validateCords(x - 1, y - 1) && ocean[x - 1][y - 1].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x - 1, y + 1) && ocean[x - 1][y + 1].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x + 1, y - 1) && ocean[x + 1][y - 1].getSquareStatus().equals(SquareStatus.SHIP))
                || (input.validateCords(x + 1, y + 1) && ocean[x + 1][y + 1].getSquareStatus().equals(SquareStatus.SHIP));
    }


}

