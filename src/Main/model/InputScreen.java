package model;

import java.util.Scanner;

// interface for screens that deal with input classes
public interface InputScreen {

    int addOrView(Scanner user_input);

    void addingItemDetails();

    void addToListObject(Scanner user_input);

    int loadItemsIntoListObject();
}
