package tests;

import inputs.Textbook;
import inputs.UniClass;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
// Testing of valid/invalid input handled in TimetableScreenTest
class UniClassTest {

    @Test
    void testConstructor() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook();
        UniClass newUniClass = new UniClass("LECTURE", "Class", "Prof", "Room", 1400, 1500, days, t);
    }

    @Test
    void testPrintUniClass() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook();
        UniClass newUniClass = new UniClass("TUTORIAL", "Class","Prof", "Room", 1400, 1500, days, t);
        newUniClass.printItem();

    }

    @Test
    void testDifferentClassTypes() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook();
        UniClass c1 = new UniClass("LECTURE", "Class","Prof", "Room", 1400, 1500, days, t);
        UniClass c2 = new UniClass("LAB", "Class","Prof", "Room", 1400, 1500, days, t);
        UniClass c3 = new UniClass("TUTORIAL", "Class","Prof", "Room", 1400, 1500, days, t);
        UniClass c4 = new UniClass("DISCUSSION", "Class","Prof", "Room", 1400, 1500, days, t);

        c1.printItem();
        c2.printItem();
        c3.printItem();
        c4.printItem();
    }

    @Test
    void testAddTextbook() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook();
        UniClass newUniClass = new UniClass("TUTORIAL", "Class","Prof", "Room", 1400, 1500, days, t);
        Textbook newT = new Textbook("Textbook", "Author", 300);
        newUniClass.addTextbook(newT);

        assertEquals("Textbook", newUniClass.getTextbook().getTitle());
        assertEquals("Author", newUniClass.getTextbook().getAuthor());
        assertEquals(300, newUniClass.getTextbook().getPages());
        assertEquals(newUniClass, newUniClass.getTextbook().getAssociatedClass());

    }

    @Test
    void testRemoveTextbook() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook("Textbook", "Author", 300);
        UniClass newUniClass = new UniClass("TUTORIAL", "Class","Prof", "Room", 1400, 1500, days, t);
        newUniClass.removeTextbook(t);

        assertEquals(null, newUniClass.getTextbook().getTitle());
        assertEquals(null, newUniClass.getTextbook().getAuthor());
        assertEquals(0, newUniClass.getTextbook().getPages());
        assertEquals(new UniClass(), newUniClass.getTextbook().getAssociatedClass());
    }



}
*/