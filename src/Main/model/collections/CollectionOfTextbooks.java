package model.collections;

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

    // EFFECTS: removes a textbook from the list.
    public void removeTextbook(UniClass uniClass) {
        if (!uniClass.getTextbook().equals(new Textbook())) {
            textbooksList.remove(uniClass.getTextbook());
        }
    }

    // EFFECTS: gets the textbook list.
    public ArrayList<Textbook> getTextbooksList() {
        return textbooksList;
    }
}
