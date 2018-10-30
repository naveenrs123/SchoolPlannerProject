package model;

import inputs.Textbook;
import inputs.UniClass;

import java.util.ArrayList;

public class ListOfTextbooks {

    ArrayList<Textbook> textbooksList;

    public ListOfTextbooks() {
        textbooksList = new ArrayList<>();
    }

    public void addTextbook(UniClass uniClass) {
        textbooksList.add(uniClass.getTextbook());
    }

    public void printTextbooks() {
        System.out.println("**TEXTBOOKS**\n");

        if (textbooksList.size() != 0) {
            for (Textbook t : textbooksList) {
                t.printTextbook();
                System.out.println("Used in: " + t.getAssociatedClass().getName());
                System.out.println();
            }
        } else {
            System.out.println("You have no textbooks attached to classes.");
        }
    }

    public void removeTextbook(UniClass uniClass) {
        textbooksList.remove(uniClass.getTextbook());
    }

    public ArrayList<Textbook> getTextbooksList() {
        return textbooksList;
    }
}
