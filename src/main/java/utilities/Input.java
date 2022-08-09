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


}
