package model;

import java.util.Scanner;

// interface for screens that deal with input classes
public interface InputScreen {

    int handleOptions(Scanner user_input);

    void addingItemDetails();

    void addToListObject(Scanner user_input);

    void loadItemsIntoListObject();

    int getloadState();
}
