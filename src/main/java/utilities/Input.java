package utilities;

import java.util.Locale;
import java.util.Scanner;

public class Input {
    Scanner sc = new Scanner(System.in);

    public String getUserInput() {
        return sc.nextLine().toUpperCase(Locale.ROOT);
    }

    public int convertInputIntoRow(String input) {
        return input.charAt(0) - 'A';
    }

    public int convertInputIntoColumn(String input) {
        return Integer.parseInt(input.substring(1)) - 1;
    }

    public int[] getCoordinates() {
        System.out.println("Please enter coordinates: ");
        String move = getUserInput();
        int x = convertInputIntoRow(move);
        int y = convertInputIntoColumn(move);
        if (validateCords(x, y)) {
            return new int[]{x, y};
        } else {
            System.out.println("Wrong coordinates");
            return getCoordinates();
        }

    }

    public String getDirection() {
        System.out.println("Horizontal(h) or vertical(v)? ");
        String letter = getUserInput();
        switch (letter) {
            case "H", "V" -> {
                return letter;
            }
        }
        return getDirection();
    }

    public boolean validateCords(int x, int y) {
        return x < 10 && x >= 0 && y < 10 && y >= 0;
    }

}
