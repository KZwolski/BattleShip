package board;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import ship.ShipType;

public abstract class BoardFactory {
    public void randomPlacement(Board board, ShipType shipType) {
        String[] directions = {"V", "H"};
        String direction = directions[new Random().nextInt(2)];
        int[] cords = drawCoordinates();
        while (checkAreThereShips(shipType.getLength(), direction, board, cords)) {
            cords = drawCoordinates();
        }
        markSquareAsShip(shipType.getLength(), direction, board, cords);
    }

    public void manualPlacement(Board board, ShipType shipType) {
        System.out.println("You are now placing: " + shipType.toString() + " of length: " + shipType.getLength());
        int[] coordinates = askForCoordinates();
        String direction = askForDirection();
        while (checkAreThereShips(shipType.getLength(), direction, board, coordinates)) {
            System.out.println("You cannot place ship there :(, try again! ");
            coordinates = askForCoordinates();
            direction = askForDirection();
        }
        markSquareAsShip(shipType.getLength(), direction, board, coordinates);

    }

    public void markSquareAsShip(int shipSize, String direction, Board board, int[] coordinates) {
        Square[][] ocean = board.getOcean();
        for (int i = 0; i < shipSize; i++) {
            if (Objects.equals(direction, "V")) {
                ocean[coordinates[0] + i][coordinates[1]].setSquareStatus(SquareStatus.valueOf("SHIP"));
            } else if (Objects.equals(direction, "H")) {
                ocean[coordinates[0]][coordinates[1] + i].setSquareStatus(SquareStatus.valueOf("SHIP"));

            }
        }
    }

    public int[] drawCoordinates() {
        int min = 0;
        int max = 9;
        return new int[]{ThreadLocalRandom.current().nextInt(min, max + 1), ThreadLocalRandom.current().nextInt(min, max + 1)};

    }

    public boolean checkIfValidCords(int x, int y) {
        return x < 10 && x >= 0 && y < 10 && y >= 0;
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
            if (checkIfValidCords(x, y)) {
                if (checkEveryDirection(board, x, y)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean checkEveryDirection(Board board, int x, int y) {
        return checkEdgeNeighbours(board, x, y) || checkHorizontalNeighbours(board, x, y) || checkVerticalNeighbours(board, x, y);
    }

    public boolean checkVerticalNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (checkIfValidCords(x - 1, y) && Objects.equals(ocean[x - 1][y].getSquareStatus().toString(), "SHIP"))
                || (checkIfValidCords(x + 1, y) && Objects.equals(ocean[x + 1][y].getSquareStatus().toString(), "SHIP"));
    }

    public boolean checkHorizontalNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (checkIfValidCords(x, y - 1) && Objects.equals(ocean[x][y - 1].getSquareStatus().toString(), "SHIP"))
                || (checkIfValidCords(x, y + 1) && Objects.equals(ocean[x][y + 1].getSquareStatus().toString(), "SHIP"));
    }

    public boolean checkEdgeNeighbours(Board board, int x, int y) {
        Square[][] ocean = board.getOcean();
        return (checkIfValidCords(x - 1, y - 1) && Objects.equals(ocean[x - 1][y - 1].getSquareStatus().toString(), "SHIP"))
                || (checkIfValidCords(x - 1, y + 1) && Objects.equals(ocean[x - 1][y + 1].getSquareStatus().toString(), "SHIP"))
                || (checkIfValidCords(x + 1, y - 1) && Objects.equals(ocean[x + 1][y - 1].getSquareStatus().toString(), "SHIP"))
                || (checkIfValidCords(x + 1, y + 1) && Objects.equals(ocean[x + 1][y + 1].getSquareStatus().toString(), "SHIP"));
    }


    public int[] askForCoordinates() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter coordinates: ");
        String move = scanner.nextLine().toUpperCase(Locale.ROOT);
        int x = convertInputIntoRow(move);
        int y = convertInputIntoColumn(move);
        if (checkIfValidCords(x, y)) {
            return new int[]{x, y};
        } else {
            System.out.println("Wrong coordinates");
            return askForCoordinates();
        }

    }

    public int convertInputIntoRow(String input) {
        int row = input.charAt(0) - 'A';
        return row;
    }

    public int convertInputIntoColumn(String input) {
        int column = Integer.parseInt(input.substring(1)) - 1;
        return column;
    }

    public String askForDirection() {
        System.out.println("Horizontal(h) or vertical(v)? ");
        Scanner scanner = new Scanner(System.in);
        String letter = scanner.nextLine().toUpperCase(Locale.ROOT);
        switch (letter) {
            case "H", "V" -> {
                return letter;
            }
        }
        return askForDirection();
    }
}

