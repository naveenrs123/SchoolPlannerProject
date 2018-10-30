package model;

import java.util.Scanner;

public interface CollectionOfItems {

    void loadSingleItem(String currentItem);

    void saveCollection();

    void removeItem(Scanner user_input);

    void printItems();
}
