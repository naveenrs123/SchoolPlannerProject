package model.collections;

import java.util.Scanner;

public interface CollectionOfItems {

    void loadSingleItem(String currentItem);

    void saveCollection();

    void addItem(Scanner user_input);

    void removeItem(Scanner user_input);

    void printItems();
}
