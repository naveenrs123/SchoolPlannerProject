package model.inputHandling;

import java.util.Scanner;

public class CollectionInputHandler {

    protected String getString(Scanner user_input, String s) {
        System.out.print(s);
        String stringInput = user_input.nextLine();
        return stringInput;
    }
}
