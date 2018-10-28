package tests;

import exceptions.input.BadClassTypeException;
import exceptions.input.BadTimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TimetableScreen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
/*
class TimetableScreenTest {

    private TimetableScreen screenTimetable;
    private String filename = "listofclasses.csv";

    @BeforeEach
    void setup() {
        screenTimetable = new TimetableScreen();
    }

    @AfterEach
    void deleteFile()
    {
        File file = new File("/Users/naveensivasankar/Desktop/ /UBC/Year 2/CPSC 210/Personal Project/projectw1_team413/" + filename);

        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Error deleting file.");
        }
    }

    // BASIC TESTS

    @Test
    void testPrintEmptyUniClassList() {
        screenTimetable.printItems();
        assertEquals(0, screenTimetable.getClassList().size());
    }

    @Test
    void testAddOrView1() {
        Scanner user_input = new Scanner("1\n");
        int choice = screenTimetable.addOrView(user_input);
        assertEquals(1, choice);
    }

    @Test
    void testAddOrView2() {
        Scanner user_input = new Scanner("2\n");
        int choice = screenTimetable.addOrView(user_input);
        assertEquals(2, choice);
    }

    @Test
    void testAddOrViewInvalid() {
        Scanner user_input = new Scanner("sdfg\n5\n1\n");
        int choice = screenTimetable.addOrView(user_input);
        assertEquals(1, choice);
    }

    // EXCEPTIONS TESTS

    @Test
    void testUserClassTypeValid() {
        Scanner user_input = new Scanner("LECTURE");
        try {
            screenTimetable.userClassType(user_input);
        } catch (BadClassTypeException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserClassTypeInvalid() {
        Scanner user_input = new Scanner("OUTDOOR EXPLORATION");
        try {
            screenTimetable.userClassType(user_input);
            fail("Exception not caught.");
        } catch (BadClassTypeException e) {

        }
    }

    @Test
    void testUserStartTimeValid() {
        Scanner user_input = new Scanner("1200");
        try {
            screenTimetable.userStartTime(user_input);
        } catch (BadTimeException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserStartTimeInvalid() {
        Scanner user_input = new Scanner("2400");
        try {
            screenTimetable.userStartTime(user_input);
            fail("Exception not caught.");
        } catch (BadTimeException e) {

        }
    }

    @Test
    void testUserEndTimeValid() {
        Scanner user_input = new Scanner("0000");
        try {
            screenTimetable.userEndTime(user_input);
        } catch (BadTimeException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserEndTimeInvalid() {
        Scanner user_input = new Scanner("2400");
        try {
            screenTimetable.userStartTime(user_input);
            fail("Exception not caught.");
        } catch (BadTimeException e) {

        }
    }

    // ADDING CLASSES TESTS

    @Test
    void testAddUniClassValidInput() {
        Scanner user_input = new Scanner("Tutorial\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0");
        screenTimetable.addUniClass(user_input);

        assertEquals("TUTORIAL", screenTimetable.getClassList().get(0).getClassType());
        assertEquals("CPSC 210", screenTimetable.getClassList().get(0).getName());
        assertEquals("Elisa Baniassad", screenTimetable.getClassList().get(0).getProf());
        assertEquals("SWNG 122", screenTimetable.getClassList().get(0).getLocation());
        assertEquals(1200, screenTimetable.getClassList().get(0).getStartTime());
        assertEquals(1300, screenTimetable.getClassList().get(0).getEndTime());

        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        assertEquals(days, screenTimetable.getClassList().get(0).getDays());
    }

    @Test
    void testAddUniClassAndView() {
        // adding one class already tested.
        Scanner user_input = new Scanner("CPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0");
        screenTimetable.addUniClass(user_input);
        screenTimetable.printItems();
    }

    @Test
    void testAddMultipleUniClassesValidInput() {
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0");
        screenTimetable.addUniClass(user_input1);

        Scanner user_input2 = new Scanner("TUTORIAL\nCPSC 310\nElisa Baniassad\nWESB 100\n0800\n0900\n2\n4\n0");
        screenTimetable.addUniClass(user_input2);

        Scanner user_input3 = new Scanner("DISCUSSION\nMATH 221\nSome Dude\nLSK 201\n1700\n1800\n1\n3\n5\n0");
        screenTimetable.addUniClass(user_input3);

        assertEquals("LECTURE", screenTimetable.getClassList().get(0).getClassType());
        assertEquals("CPSC 210", screenTimetable.getClassList().get(0).getName());
        assertEquals("Elisa Baniassad", screenTimetable.getClassList().get(0).getProf());
        assertEquals("SWNG 122", screenTimetable.getClassList().get(0).getLocation());
        assertEquals(1200, screenTimetable.getClassList().get(0).getStartTime());
        assertEquals(1300, screenTimetable.getClassList().get(0).getEndTime());

        ArrayList<Integer> days0 = new ArrayList<>(Arrays.asList(1, 3, 5));
        assertEquals(days0, screenTimetable.getClassList().get(0).getDays());

        assertEquals("TUTORIAL", screenTimetable.getClassList().get(1).getClassType());
        assertEquals("CPSC 310", screenTimetable.getClassList().get(1).getName());
        assertEquals("Elisa Baniassad", screenTimetable.getClassList().get(1).getProf());
        assertEquals("WESB 100", screenTimetable.getClassList().get(1).getLocation());
        assertEquals(800, screenTimetable.getClassList().get(1).getStartTime());
        assertEquals(900, screenTimetable.getClassList().get(1).getEndTime());

        ArrayList<Integer> days1 = new ArrayList<>(Arrays.asList(2, 4));
        assertEquals(days1, screenTimetable.getClassList().get(1).getDays());

        assertEquals("DISCUSSION", screenTimetable.getClassList().get(2).getClassType());
        assertEquals("MATH 221", screenTimetable.getClassList().get(2).getName());
        assertEquals("Some Dude", screenTimetable.getClassList().get(2).getProf());
        assertEquals("LSK 201", screenTimetable.getClassList().get(2).getLocation());
        assertEquals(1700, screenTimetable.getClassList().get(2).getStartTime());
        assertEquals(1800, screenTimetable.getClassList().get(2).getEndTime());

        ArrayList<Integer> days2 = new ArrayList<>(Arrays.asList(1, 3, 5));
        assertEquals(days2, screenTimetable.getClassList().get(2).getDays());
    }

    @Test
    void testAddMultipleUniClassesAndView() {
        // adding multiple classes already tested.
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0");
        screenTimetable.addUniClass(user_input1);

        Scanner user_input2 = new Scanner("LAB\nCPSC 310\nElisa Baniassad\nWESB 100\n0800\n0900\n2\n4\n0");
        screenTimetable.addUniClass(user_input2);

        Scanner user_input3 = new Scanner("TUTORIAL\nMATH 221\nSome Dude\nLSK 201\n1700\n1800\n1\n3\n5\n0");
        screenTimetable.addUniClass(user_input3);

        screenTimetable.printItems();
    }

    @Test
    void testAddMultipleUniClassesInvalidTimes() {
        // If all tasks were not created, test succeeded.
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n-1\n1300\n1\n3\n5\n0");
        screenTimetable.addUniClass(user_input1);

        Scanner user_input2 = new Scanner("LAB\nCPSC 310\nElisa Baniassad\nWESB 100\n0800\n2400\n2\n4\n0");
        screenTimetable.addUniClass(user_input2);

        Scanner user_input3 = new Scanner("TUTORIAL\nMATH 221\nSome Dude\nLSK 201\n2401\n1800\n1\n3\n5\n0");
        screenTimetable.addUniClass(user_input3);

        assertEquals(0, screenTimetable.getClassList().size());
    }

    @Test
    void testAddUniClassesInvalidDays() {
        // If task was not created, test succeeded.
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\nsdfg\n9\n-1\n0");
        screenTimetable.addUniClass(user_input1);
        assertEquals(0, screenTimetable.getClassList().size());


    }

}

*/
