package utilities;

import java.util.Locale;
import java.util.Scanner;

public class Input {
    Scanner sc = new Scanner(System.in);

    public void getUserShot(){
        String input;
        do {
            input = sc.nextLine().toUpperCase(Locale.ROOT);
        } while (coordinatesInputValidator(input));
    }



    public boolean coordinatesInputValidator(String input){
        if (input.toCharArray().length > 2){
            System.out.println("Type only Coordinates (ex. A3)!");
            return true;
        }

        try {
            int intInput = Integer.parseInt(input.split("")[1]);
        } catch (NumberFormatException e){
            System.out.println("Wrong input!");
            return true;
        }

        return false;
    }


}
