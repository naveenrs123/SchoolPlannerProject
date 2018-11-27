package tests;

import inputs.Textbook;
import inputs.UniClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/*
public class TextbookTest {

    Textbook t;

    @BeforeEach
    void setup() {

    }

    @Test
    void testConstructor() {
        Textbook t1 = new Textbook();
        Textbook t2 = new Textbook("Title", "Author", 300);

        assertEquals(null, t1.getTitle());
        assertEquals(null, t1.getAuthor());
        assertEquals(0, t1.getPages());

        assertEquals("Title", t2.getTitle());
        assertEquals("Author", t2.getAuthor());
        assertEquals(300, t2.getPages());
    }

    @Test
    void testSetDetails() {
        Scanner user_input = new Scanner("Title\nAuthor\n300");
        Textbook t = new Textbook();
        t.setDetails(user_input);

        assertEquals("Title", t.getTitle());
        assertEquals("Author", t.getAuthor());
        assertEquals(300, t.getPages());
    }

    @Test
    void testSetDetailsInvalidPages() {
        Scanner user_input = new Scanner("Title\nAuthor\n-300\n-2\n300");
        Textbook t = new Textbook();
        t.setDetails(user_input);

        assertEquals("Title", t.getTitle());
        assertEquals("Author", t.getAuthor());
        assertEquals(300, t.getPages());
    }

    @Test
    void testAddUniClass() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook();
        UniClass newUniClass = new UniClass("LECTURE", "Class", "Prof", "Room", 1400, 1500, days, t);
        Textbook tNew = new Textbook("Title", "Author", 300);
        tNew.addUniClass(newUniClass);

        assertEquals("Title", tNew.getTitle());
        assertEquals("Author", tNew.getAuthor());
        assertEquals(300, tNew.getPages());
        assertEquals(newUniClass, tNew.getAssociatedClass());
    }

    @Test
    void testRemoveUniClass() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook();
        UniClass newUniClass = new UniClass("LECTURE", "Class", "Prof", "Room", 1400, 1500, days, t);
        Textbook tNew = new Textbook("Title", "Author", 300);
        tNew.addUniClass(newUniClass);

        tNew.removeUniClass(newUniClass);

        assertEquals("Title", tNew.getTitle());
        assertEquals("Author", tNew.getAuthor());
        assertEquals(300, tNew.getPages());
        assertEquals(new UniClass(), tNew.getAssociatedClass());
    }

}
*/
