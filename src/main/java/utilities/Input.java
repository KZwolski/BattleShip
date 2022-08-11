package utilities;

import java.util.Locale;
import java.util.Scanner;

public class Input {
    Display printer = new Display();

    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toUpperCase(Locale.ROOT);

    }

    public int convertInputIntoRow(String input) {
        return input.charAt(0) - 'A';
    }

    public int convertInputIntoColumn(String input) {
        return Integer.parseInt(input.substring(1)) - 1;
    }

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

    public boolean checkForValidInput(String input) {
        try {
            int column = convertInputIntoColumn(input);
            int row = convertInputIntoRow(input);
            return validateCords(row, column);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

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

    public boolean validateCords(int x, int y) {
        return x < 10 && x >= 0 && y < 10 && y >= 0;
    }

    public String askForName() {
        printer.consolePrint("Please enter your name: ");
        return getUserInput();

    }

}
