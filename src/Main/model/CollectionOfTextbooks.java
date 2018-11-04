package model;

import inputs.Textbook;
import inputs.UniClass;

import java.util.ArrayList;

public class CollectionOfTextbooks {

    ArrayList<Textbook> textbooksList;

    // EFFECTS: creates a new list of textbooks.
    public CollectionOfTextbooks() {
        textbooksList = new ArrayList<>();
    }

    // MODIFIES: this.
    // EFFECTS: adds a textbooks to the list.
    public void addTextbook(UniClass uniClass) {
        textbooksList.add(uniClass.getTextbook());
    }

    // EFFECTS: prints out the textbooks.
    public void printTextbooks() {
        System.out.println("**TEXTBOOKS**\n");
        if (textbooksList.size() != 0) {
            for (Textbook t : textbooksList) {
                t.printItem();
                System.out.println("Used in: " + t.getAssociatedClass().getName());
                System.out.println();
            }
        } else {
            System.out.println("You have no textbooks attached to classes.");
        }
    }

    // EFFECTS: removes a textbook from the list.
    public void removeTextbook(UniClass uniClass) {
        textbooksList.remove(uniClass.getTextbook());
    }

    // EFFECTS: gets the textbook list.
    public ArrayList<Textbook> getTextbooksList() {
        return textbooksList;
    }
}
