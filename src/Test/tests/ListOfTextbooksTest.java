package tests;

import inputs.Textbook;
import inputs.UniClass;
import model.ListOfTextbooks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfTextbooksTest {

    ListOfTextbooks lot;

    @BeforeEach
    void setup() {
        lot = new ListOfTextbooks();
    }

    @Test
    void testAddTextbook() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook("Title", "Author", 300);
        UniClass newUniClass = new UniClass("LECTURE", "Class", "Prof", "Room", 1400, 1500, days, t);
        lot.addTextbook(newUniClass);

        assertEquals(1, lot.getTextbooksList().size());
    }

    @Test
    void testRemoveTextbook() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook("Title", "Author", 300);
        UniClass newUniClass = new UniClass("LECTURE", "Class", "Prof", "Room", 1400, 1500, days, t);
        lot.addTextbook(newUniClass);

        lot.removeTextbook(newUniClass);

        assertEquals(0, lot.getTextbooksList().size());
    }

    @Test
    void testAddTextbookMultiple() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook("Title", "Author", 300);
        UniClass newUniClass = new UniClass("LECTURE", "Class", "Prof", "Room", 1400, 1500, days, t);
        UniClass newUniClass2 = new UniClass("LAB", "Class", "Prof", "Room", 1500, 1600, days, t);
        lot.addTextbook(newUniClass);
        lot.addTextbook(newUniClass2);

        assertEquals(2, lot.getTextbooksList().size());
    }

    @Test
    void testRemoveTextbookMultiple() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook("Title", "Author", 300);
        UniClass newUniClass = new UniClass("LECTURE", "Class", "Prof", "Room", 1400, 1500, days, t);
        UniClass newUniClass2 = new UniClass("LAB", "Class", "Prof", "Room", 1500, 1600, days, t);
        lot.addTextbook(newUniClass);
        lot.addTextbook(newUniClass2);

        lot.removeTextbook(newUniClass);
        lot.removeTextbook(newUniClass2);

        assertEquals(0, lot.getTextbooksList().size());
    }


}
