package tests;

import inputs.Textbook;
import inputs.UniClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TimetableScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimetableScreenTest {

    private TimetableScreen screenTimetable;
    private String filename = "listofclasses.csv";

    @BeforeEach
    void setup() {
        screenTimetable = new TimetableScreen();
    }

    // BASIC TESTS

    @Test
    void testPrintEmptyUniClassList() {
        screenTimetable.printStoredItems();
        assertEquals(0, screenTimetable.getListOfUniClasses().getClassList().size());
    }

    @Test
    void testHandleOptions1() {
        Scanner user_input = new Scanner("1\n");
        int choice = screenTimetable.handleOptions(user_input);
        assertEquals(1, choice);
    }

    @Test
    void testHandleOptions2() {
        Scanner user_input = new Scanner("2\n");
        int choice = screenTimetable.handleOptions(user_input);
        assertEquals(2, choice);
    }

    @Test
    void testHandleOptions3() {
        Scanner user_input = new Scanner("3\n");
        int choice = screenTimetable.handleOptions(user_input);
        assertEquals(3, choice);
    }

    @Test
    void testHandleOptions4() {
        Scanner user_input = new Scanner("4\n");
        int choice = screenTimetable.handleOptions(user_input);
        assertEquals(4, choice);
    }

    @Test
    void testHandleOptions5() {
        Scanner user_input = new Scanner("5\n");
        int choice = screenTimetable.handleOptions(user_input);
        assertEquals(5, choice);
    }

    @Test
    void testHandleOptions6() {
        Scanner user_input = new Scanner("6\n");
        int choice = screenTimetable.handleOptions(user_input);
        assertEquals(6, choice);
    }

    @Test
    void testAddOrViewInvalid() {
        Scanner user_input = new Scanner("sdfg\n7\n1\n");
        int choice = screenTimetable.handleOptions(user_input);
        assertEquals(1, choice);
    }

    @Test
    void testLoadItemsIntoListObject() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        Textbook t = new Textbook();
        UniClass c1 = new UniClass("LECTURE", "Class","Prof", "Room", 1400, 1500, days, t);
        UniClass c2 = new UniClass("LAB", "Class","Prof", "Room", 1400, 1500, days, t);
        screenTimetable.getListOfUniClasses().saveUniClass(c1);
        screenTimetable.getListOfUniClasses().saveUniClass(c2);


        screenTimetable.loadItemsIntoListObject();

        assertEquals(2, screenTimetable.getListOfUniClasses().getClassList().size());
    }
}
