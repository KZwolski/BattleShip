package utilities;

import java.util.Locale;
import java.util.Scanner;

/**
 * Class responsible for handling user input in the game
 */
public class Input {
    /**
     * Create instance of Display class to handle displaying messages
     *
     * @see Display
     */
    Display printer = new Display();

    /**
     * Method responsible for getting user's input
     *
     * @return command line arguments provided by user
     */
    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toUpperCase(Locale.ROOT);

    }

    /**
     * Method used for converting user's input into row
     * @param input command line arguments
     * @return row converted from input
     */
    public int convertInputIntoRow(String input) {
        return input.charAt(0) - 'A';
    }
    /**
     * Method used for converting user's input into column
     * @param input command line arguments
     * @return column converted from input
     */
    public int convertInputIntoColumn(String input) {
        return Integer.parseInt(input.substring(1)) - 1;
    }

    /**
     * Method responsible for getting shot coordinates from user
     *
     * @return array of ints representing coordinates
     */
    public int[] getCoordinates() {
        while (true) {
            printer.consolePrint("Please enter coordinates: ");
            String move = getUserInput();
            if (checkForValidInput(move)) {
                int x = convertInputIntoRow(move);
                int y = convertInputIntoColumn(move);
                return new int[]{x, y};
                }
            printer.consolePrint("Wrong coordinates!");
            }

    }

    /**
     * Method used for checking if provided coords are valid
     *
     * @param input command line arguments
     * @return true if cords are valid
     */
    public boolean checkForValidInput(String input) {
        try {
            int column = convertInputIntoColumn(input);
            int row = convertInputIntoRow(input);
            return validateCords(row, column);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    /**
     * Method responsible for getting direction from user
     *
     * @return provided direction
     */
    public String getDirection() {
        printer.consolePrint("Horizontal(h) or vertical(v)? ");
        String letter = getUserInput();
        switch (letter) {
            case "H", "V" -> {
                return letter;
            }
            default -> getDirection();
        }
        return getDirection();
    }

    /**
     * Method checking if provided cords are in the size of board
     *
     * @param x row to check
     * @param y column to check
     * @return true if valid
     */
    public boolean validateCords(int x, int y) {
        return x < 10 && x >= 0 && y < 10 && y >= 0;
    }

    /**
     * Method used getting name from the user
     *
     * @return name of the user
     */
    public String askForName() {
        printer.consolePrint("Please enter your name: ");
        return getUserInput();

    }

}
