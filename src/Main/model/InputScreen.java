package model;

import java.util.Scanner;

// interface for screens that deal with input classes
public interface InputScreen {

    int addOrView(Scanner user_input);

    void addItem(Scanner user_input);

    void printItems();

    void itemDetails();
}
