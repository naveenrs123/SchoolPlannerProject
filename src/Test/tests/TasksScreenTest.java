package tests;

import exceptions.date.BadDayException;
import exceptions.date.BadMonthException;
import exceptions.date.BadYearException;
import exceptions.date.InvalidDateException;
import exceptions.input.InvalidImportanceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TasksScreen;

import java.io.File;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
/*
class TasksScreenTest {

    private TasksScreen screenTasks;
    private String filename = "listoftasks.csv";


    @BeforeEach
    void setup() {
        screenTasks = new TasksScreen();
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
    void testGetEmptyTaskList() {
        screenTasks.printItems();

        assertEquals(0, screenTasks.getGeneralTaskList().size());
        assertEquals(0, screenTasks.getEventTaskList().size());
    }

    @Test
    void testAddOrView1() {
        Scanner user_input = new Scanner("1\n");
        int choice = screenTasks.addOrView(user_input);

        assertEquals(1, choice);
    }

    @Test
    void testAddOrView2() {
        Scanner user_input = new Scanner("2\n");
        int choice = screenTasks.addOrView(user_input);

        assertEquals(2, choice);
    }

    @Test
    void testAddOrViewInvalid() {
        Scanner user_input = new Scanner("sdfg\n5\n1\n");
        int choice = screenTasks.addOrView(user_input);

        assertEquals(1, choice);
    }

    // EXCEPTIONS TESTS

    @Test
    void testUserDayValid() {
        Scanner user_input = new Scanner("20");
        try {
            screenTasks.userDay(user_input);
        } catch (BadDayException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserDayInvalid() {
        Scanner user_input = new Scanner("32");
        try {
            screenTasks.userDay(user_input);
            fail("Exception not caught.");
        } catch (BadDayException e) {

        }
    }

    @Test
    void testUserMonthValid() {
        Scanner user_input = new Scanner("11");
        try {
            screenTasks.userMonth(user_input);
        } catch (BadMonthException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserMonthInvalid() {
        Scanner user_input = new Scanner("13");
        try {
            screenTasks.userMonth(user_input);
            fail("Exception not caught.");
        } catch (BadMonthException e) {

        }
    }

    @Test
    void testUserYearValid() {
        Scanner user_input = new Scanner("20");
        try {
            screenTasks.userYear(user_input);
        } catch (BadYearException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserYearInvalid() {
        Scanner user_input = new Scanner("100");
        try {
            screenTasks.userYear(user_input);
            fail("Exception not caught.");
        } catch (BadYearException e) {

        }
    }

    @Test
    void testValidateDateValid() {
        String day = "10";
        String month = "11";
        String year = "18";
        try {
            screenTasks.validateDate(day, month, year);
        } catch (InvalidDateException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testValidateDateInvalid() {
        String day = "29";
        String month = "2";
        String year = "18";
        try {
            screenTasks.validateDate(day, month, year);
            fail("Exception not caught.");
        } catch (InvalidDateException e) {

        }
    }

    @Test
    void testGetImportanceLevelValid() {
        Scanner user_input = new Scanner("LOW");
        try {
            screenTasks.getImportanceLevel(user_input);
        } catch (InvalidImportanceException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testGetImportanceLevelInvalid() {
        Scanner user_input = new Scanner("HYPER");
        try {
            screenTasks.getImportanceLevel(user_input);
            fail("Exception not caught.");
        } catch (InvalidImportanceException e) {

        }
    }

    // ADDING TASKS TESTS

    @Test
    void testAddTaskValidInput() {
        Scanner user_input = new Scanner("1\nA General Task Description\n10\n10\n18\nLOW");
        screenTasks.addUniClass(user_input);

        assertEquals(screenTasks.getGeneralTaskList().get(0).getType(), "TASK");
        assertEquals(screenTasks.getGeneralTaskList().get(0).getDescription(), "A General Task Description");
        assertEquals(screenTasks.getGeneralTaskList().get(0).getDay(), 10);
        assertEquals(screenTasks.getGeneralTaskList().get(0).getMonth(), 10);
        assertEquals(screenTasks.getGeneralTaskList().get(0).getYear(), 18);
        assertEquals(screenTasks.getGeneralTaskList().get(0).getImportance(), "LOW");

        Scanner user_input2 = new Scanner("2\nAn Event Description\n10\n11\n18\n12\n11\n18\nLOW\nNONE");
        screenTasks.addUniClass(user_input2);
        assertEquals("EVENT", screenTasks.getEventTaskList().get(0).getType());
        assertEquals("An Event Description", screenTasks.getEventTaskList().get(0).getDescription());
        assertEquals(10, screenTasks.getEventTaskList().get(0).getStartDay());
        assertEquals(11, screenTasks.getEventTaskList().get(0).getStartMonth());
        assertEquals(18, screenTasks.getEventTaskList().get(0).getStartYear());
        assertEquals(12, screenTasks.getEventTaskList().get(0).getEndDay());
        assertEquals(11, screenTasks.getEventTaskList().get(0).getEndMonth());
        assertEquals(18, screenTasks.getEventTaskList().get(0).getEndYear());
        assertEquals("LOW", screenTasks.getEventTaskList().get(0).getImportance());
        assertEquals("NONE", screenTasks.getEventTaskList().get(0).getComments());

    }

    @Test
    void testAddTaskAndView() {
        // adding was already tested.
        Scanner user_input = new Scanner("1\nA General Task Description\n10\n10\n18\nLOW");
        screenTasks.addUniClass(user_input);

        Scanner user_input2 = new Scanner("2\nAn Event Description\n10\n11\n18\n12\n11\n18\nLOW\nNONE");
        screenTasks.addUniClass(user_input2);

        screenTasks.printItems();
    }

    @Test
    void testAddMultipleTasksValidInput() {
        Scanner user_input1 = new Scanner("1\nA Task Description\n10\n11\n18\nLOW");
        screenTasks.addUniClass(user_input1);
        Scanner user_input2 = new Scanner("1\nAnother Task Description\n10\n10\n18\nMEDIUM");
        screenTasks.addUniClass(user_input2);
        Scanner user_input3 = new Scanner("2\nAn Event Description\n10\n11\n18\n11\n11\n18\nHIGH\nNONE");
        screenTasks.addUniClass(user_input3);
        Scanner user_input4 = new Scanner("2\nAnother Event Description\n29\n11\n18\n30\n11\n18\nEXTREME\nNONE");
        screenTasks.addUniClass(user_input4);

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(0).getType());
        assertEquals("A Task Description", screenTasks.getGeneralTaskList().get(0).getDescription());
        assertEquals(10, screenTasks.getGeneralTaskList().get(0).getDay());
        assertEquals(11, screenTasks.getGeneralTaskList().get(0).getMonth());
        assertEquals(18, screenTasks.getGeneralTaskList().get(0).getYear());
        assertEquals("LOW", screenTasks.getGeneralTaskList().get(0).getImportance());

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(0).getType());
        assertEquals("Another Task Description", screenTasks.getGeneralTaskList().get(1).getDescription());
        assertEquals(10, screenTasks.getGeneralTaskList().get(1).getDay());
        assertEquals(10, screenTasks.getGeneralTaskList().get(1).getMonth());
        assertEquals(18, screenTasks.getGeneralTaskList().get(1).getYear());
        assertEquals("MEDIUM", screenTasks.getGeneralTaskList().get(1).getImportance());

        assertEquals( "EVENT", screenTasks.getEventTaskList().get(0).getType());
        assertEquals("An Event Description", screenTasks.getEventTaskList().get(0).getDescription());
        assertEquals(10, screenTasks.getEventTaskList().get(0).getStartDay());
        assertEquals(11, screenTasks.getEventTaskList().get(0).getStartMonth());
        assertEquals(18, screenTasks.getEventTaskList().get(0).getStartYear());
        assertEquals(11, screenTasks.getEventTaskList().get(0).getEndDay());
        assertEquals(11, screenTasks.getEventTaskList().get(0).getEndMonth());
        assertEquals(18, screenTasks.getEventTaskList().get(0).getEndYear());
        assertEquals("HIGH", screenTasks.getEventTaskList().get(0).getImportance());
        assertEquals("NONE", screenTasks.getEventTaskList().get(0).getComments());

        assertEquals( "EVENT", screenTasks.getEventTaskList().get(1).getType());
        assertEquals("Another Event Description", screenTasks.getEventTaskList().get(1).getDescription());
        assertEquals(29, screenTasks.getEventTaskList().get(1).getStartDay());
        assertEquals(11, screenTasks.getEventTaskList().get(1).getStartMonth());
        assertEquals(18, screenTasks.getEventTaskList().get(1).getStartYear());
        assertEquals(30, screenTasks.getEventTaskList().get(1).getEndDay());
        assertEquals(11, screenTasks.getEventTaskList().get(1).getEndMonth());
        assertEquals(18, screenTasks.getEventTaskList().get(1).getEndYear());
        assertEquals("EXTREME", screenTasks.getEventTaskList().get(1).getImportance());
        assertEquals("NONE", screenTasks.getEventTaskList().get(1).getComments());
    }

    @Test
    void testAddMultipleTasksAndView() {
        // Adding multiple was already tested.
        Scanner user_input1 = new Scanner("1\nA Task Description\n10\n11\n18\nLOW");
        screenTasks.addUniClass(user_input1);
        Scanner user_input2 = new Scanner("1\nAnother Task Description\n10\n10\n18\nMEDIUM");
        screenTasks.addUniClass(user_input2);
        Scanner user_input3 = new Scanner("2\nAn Event Description\n10\n11\n18\n11\n11\n18\nHIGH\nNONE");
        screenTasks.addUniClass(user_input3);
        Scanner user_input4 = new Scanner("2\nAnother Event Description\n29\n11\n18\n30\n11\n18\nEXTREME\nNONE");
        screenTasks.addUniClass(user_input4);

        screenTasks.printItems();
    }

    @Test
        // the functions that get the day, month and year are identical for both types of tasks.
    void testAddMultipleTasksInvalidInputLower() {

        //invalid day (lower boundary)
        Scanner user_input1 = new Scanner("1\nA Task Description\n0\n11\n18\nLOW");
        screenTasks.addUniClass(user_input1);

        //invalid month (lower boundary)
        Scanner user_input2 = new Scanner("1\nAnother Task Description\n10\n0\n18\nMEDIUM");
        screenTasks.addUniClass(user_input2);

        //invalid year (lower boundary)
        Scanner user_input3 = new Scanner("2\nAn Event Description\n10\n11\n0\n11\n11\n18\nHIGH\nNONE");
        screenTasks.addUniClass(user_input3);

        //invalid importanceLevel
        Scanner user_input4 = new Scanner("2\nAnother Event Description\n29\n11\n18\n30\n11\n18\nHello\nNONE");
        screenTasks.addUniClass(user_input4);

        assertEquals(0, screenTasks.getGeneralTaskList().size());
        assertEquals(0, screenTasks.getEventTaskList().size());
    }

    @Test
    // the functions that get the day, month and year are identical for both types of tasks.
    void testAddMultipleTasksValidInputLower() {

        //valid day (lower boundary)
        Scanner user_input1 = new Scanner("1\nA Task Description\n1\n10\n19\nLOW");
        screenTasks.addUniClass(user_input1);

        //valid month (lower boundary)
        Scanner user_input2 = new Scanner("1\nAnother Task Description\n10\n1\n40\nMEDIUM");
        screenTasks.addUniClass(user_input2);

        //valid year (lower boundary)
        Scanner user_input3 = new Scanner("1\nYet Another Task Description\n10\n10\n18\nHIGH");
        screenTasks.addUniClass(user_input3);

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(0).getType());
        assertEquals("A Task Description", screenTasks.getGeneralTaskList().get(0).getDescription());
        assertEquals(1, screenTasks.getGeneralTaskList().get(0).getDay());
        assertEquals(10, screenTasks.getGeneralTaskList().get(0).getMonth());
        assertEquals(19, screenTasks.getGeneralTaskList().get(0).getYear());
        assertEquals("LOW", screenTasks.getGeneralTaskList().get(0).getImportance());

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(1).getType());
        assertEquals("Another Task Description", screenTasks.getGeneralTaskList().get(1).getDescription());
        assertEquals(10, screenTasks.getGeneralTaskList().get(1).getDay());
        assertEquals(1, screenTasks.getGeneralTaskList().get(1).getMonth());
        assertEquals(40, screenTasks.getGeneralTaskList().get(1).getYear());
        assertEquals("MEDIUM", screenTasks.getGeneralTaskList().get(1).getImportance());

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(0).getType());
        assertEquals("Yet Another Task Description", screenTasks.getGeneralTaskList().get(2).getDescription());
        assertEquals(10, screenTasks.getGeneralTaskList().get(2).getDay());
        assertEquals(10, screenTasks.getGeneralTaskList().get(2).getMonth());
        assertEquals(18, screenTasks.getGeneralTaskList().get(2).getYear());
        assertEquals("HIGH", screenTasks.getGeneralTaskList().get(2).getImportance());
    }

    @Test
    void testAddMultipleTasksInvalidInputUpper() {

        //invalid day (upper boundary)
        Scanner user_input1 = new Scanner("1\nA Task Description\n32\n10\n18\nLOW");
        screenTasks.addUniClass(user_input1);

        //invalid month (upper boundary)
        Scanner user_input2 = new Scanner("1\nAnother Task Description\n10\n13\n18\nMEDIUM");
        screenTasks.addUniClass(user_input2);

        //invalid year (upper boundary)
        Scanner user_input3 = new Scanner("1\nYet Another Task Description\n10\n10\n100\nHIGH");
        screenTasks.addUniClass(user_input3);

        //invalid day (upper boundary) but valid month
        Scanner user_input4 = new Scanner("1\nA Task Description\n31\n9\n18\nLOW");
        screenTasks.addUniClass(user_input4);

        //invalid day (upper boundary) but valid month
        Scanner user_input5 = new Scanner("1\nA Task Description\n29\n2\n19\nLOW");
        screenTasks.addUniClass(user_input5);

        //invalid day (upper boundary) but valid month and leap year.
        Scanner user_input6 = new Scanner("1\nA Task Description\n30\n2\n20\nLOW");
        screenTasks.addUniClass(user_input6);

        assertEquals(0, screenTasks.getGeneralTaskList().size());

    }

    @Test
    void testAddMultipleTasksValidInputUpper() {

        //valid day (upper boundary)
        Scanner user_input1 = new Scanner("1\nA Task Description\n31\n10\n18\nLOW");
        screenTasks.addUniClass(user_input1);

        //valid month (upper boundary)
        Scanner user_input2 = new Scanner("1\nAnother Task Description\n10\n12\n18\nMEDIUM");
        screenTasks.addUniClass(user_input2);

        //valid year (upper boundary)
        Scanner user_input3 = new Scanner("1\nYet Another Task Description\n10\n10\n99\nHIGH");
        screenTasks.addUniClass(user_input3);

        //valid day (upper boundary) + February (non-leap year)
        Scanner user_input4 = new Scanner("1\nTask 1\n28\n2\n19\nLOW");
        screenTasks.addUniClass(user_input4);

        //valid day (upper boundary) + February (non-leap year)
        Scanner user_input5 = new Scanner("1\nTask 2\n29\n2\n20\nLOW");
        screenTasks.addUniClass(user_input5);

        screenTasks.printItems();

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(0).getType());
        assertEquals("A Task Description", screenTasks.getGeneralTaskList().get(0).getDescription());
        assertEquals(31, screenTasks.getGeneralTaskList().get(0).getDay());
        assertEquals(10, screenTasks.getGeneralTaskList().get(0).getMonth());
        assertEquals(18, screenTasks.getGeneralTaskList().get(0).getYear());
        assertEquals("LOW", screenTasks.getGeneralTaskList().get(0).getImportance());

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(1).getType());
        assertEquals("Another Task Description", screenTasks.getGeneralTaskList().get(1).getDescription());
        assertEquals(10, screenTasks.getGeneralTaskList().get(1).getDay());
        assertEquals(12, screenTasks.getGeneralTaskList().get(1).getMonth());
        assertEquals(18, screenTasks.getGeneralTaskList().get(1).getYear());
        assertEquals("MEDIUM", screenTasks.getGeneralTaskList().get(1).getImportance());

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(2).getType());
        assertEquals("Yet Another Task Description", screenTasks.getGeneralTaskList().get(2).getDescription());
        assertEquals(10, screenTasks.getGeneralTaskList().get(2).getDay());
        assertEquals(10, screenTasks.getGeneralTaskList().get(2).getMonth());
        assertEquals(99, screenTasks.getGeneralTaskList().get(2).getYear());
        assertEquals("HIGH", screenTasks.getGeneralTaskList().get(2).getImportance());

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(3).getType());
        assertEquals("Task 1", screenTasks.getGeneralTaskList().get(3).getDescription());
        assertEquals(28, screenTasks.getGeneralTaskList().get(3).getDay());
        assertEquals(2, screenTasks.getGeneralTaskList().get(3).getMonth());
        assertEquals(19, screenTasks.getGeneralTaskList().get(3).getYear());
        assertEquals("LOW", screenTasks.getGeneralTaskList().get(3).getImportance());

        assertEquals( "TASK", screenTasks.getGeneralTaskList().get(4).getType());
        assertEquals("Task 2", screenTasks.getGeneralTaskList().get(4).getDescription());
        assertEquals(29, screenTasks.getGeneralTaskList().get(4).getDay());
        assertEquals(2, screenTasks.getGeneralTaskList().get(4).getMonth());
        assertEquals(20, screenTasks.getGeneralTaskList().get(4).getYear());
        assertEquals("LOW", screenTasks.getGeneralTaskList().get(4).getImportance());
    }
}

*/
