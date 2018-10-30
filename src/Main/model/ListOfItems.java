package model;

import java.util.Scanner;

public interface ListOfItems {

    void loadSingleItem(String currentItem);

    void saveList();

    void removeItem(Scanner user_input);

    void printItems();
}
