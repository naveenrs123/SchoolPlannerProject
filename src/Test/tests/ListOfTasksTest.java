package tests;

import exceptions.date.BadDayException;
import exceptions.date.BadMonthException;
import exceptions.date.BadYearException;
import exceptions.date.InvalidDateException;
import exceptions.input.InvalidImportanceException;
import model.ListOfEventTasks;
import model.ListOfGeneralTasks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfTasksTest {

    private ListOfGeneralTasks generalTasks;
    private ListOfEventTasks eventTasks;
    private String filename1 = "listofgeneraltasks.csv";
    private String filename2 = "listofeventtasks.csv";

    @BeforeEach
    void setup() {
        generalTasks = new ListOfGeneralTasks();
        eventTasks = new ListOfEventTasks();
    }

    @AfterEach
    void deleteFiles()
    {
        File file1 = new File("/Users/naveensivasankar/Desktop/ /UBC/Year 2/CPSC 210/Personal Project/projectw1_team413/" + filename1);
        if (file1.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Error deleting file.");
        }

        File file2 = new File("/Users/naveensivasankar/Desktop/ /UBC/Year 2/CPSC 210/Personal Project/projectw1_team413/" + filename2);
        if (file2.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Error deleting file.");
        }
    }

    @Test
    void testGetEmptyTaskList() {
        assertEquals(0, generalTasks.getTaskList().size());
        assertEquals(0, eventTasks.getTaskList().size());
    }

    // EXCEPTIONS TESTS

    @Test
    void testUserDayValid() {
        Scanner user_input1 = new Scanner("20");
        Scanner user_input2 = new Scanner("20");
        try {
            generalTasks.userDay(user_input1);
            eventTasks.userDay(user_input2);
        } catch (BadDayException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserDayInvalid() {
        Scanner user_input = new Scanner("32");
        try {
            generalTasks.userDay(user_input);
            fail("Exception not caught.");
        } catch (BadDayException e) {

        }
    }

    @Test
    void testUserMonthValid() {
        Scanner user_input1 = new Scanner("11");
        Scanner user_input2 = new Scanner("11");
        try {
            generalTasks.userMonth(user_input1);
            eventTasks.userMonth(user_input2);
        } catch (BadMonthException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserMonthInvalid() {
        Scanner user_input = new Scanner("13");
        try {
            generalTasks.userMonth(user_input);
            fail("Exception not caught.");
        } catch (BadMonthException e) {

        }
    }

    @Test
    void testUserYearValid() {
        Scanner user_input1 = new Scanner("20");
        Scanner user_input2 = new Scanner("20");
        try {
            generalTasks.userYear(user_input1);
            eventTasks.userYear(user_input2);
        } catch (BadYearException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserYearInvalid() {
        Scanner user_input = new Scanner("100");
        try {
            generalTasks.userYear(user_input);
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
            generalTasks.validateDate(day, month, year);
            eventTasks.validateDate(day, month, year);
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
            generalTasks.validateDate(day, month, year);
            fail("Exception not caught.");
        } catch (InvalidDateException e) {

        }
    }

    @Test
    void testGetImportanceLevelValid() {
        Scanner user_input1 = new Scanner("LOW");
        Scanner user_input2 = new Scanner("LOW");
        try {
            generalTasks.getImportanceLevel(user_input1);
            eventTasks.getImportanceLevel(user_input2);
        } catch (InvalidImportanceException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testGetImportanceLevelInvalid() {
        Scanner user_input = new Scanner("HYPER");
        try {
            generalTasks.getImportanceLevel(user_input);
            fail("Exception not caught.");
        } catch (InvalidImportanceException e) {

        }
    }

    // ADDING TASKS TESTS

    @Test
    void testAddTaskValidInput() {
        Scanner user_input = new Scanner("A General Task Description\n10\n10\n18\nLOW");
        generalTasks.addTask(user_input, "TASK");

        assertEquals(generalTasks.getTaskList().get(0).getType(), "TASK");
        assertEquals(generalTasks.getTaskList().get(0).getDescription(), "A General Task Description");
        assertEquals(generalTasks.getTaskList().get(0).getDay(), 10);
        assertEquals(generalTasks.getTaskList().get(0).getMonth(), 10);
        assertEquals(generalTasks.getTaskList().get(0).getYear(), 18);
        assertEquals(generalTasks.getTaskList().get(0).getImportance(), "LOW");

        Scanner user_input2 = new Scanner("An Event Description\n10\n11\n18\n12\n11\n18\nLOW\nNONE");

        eventTasks.addTask(user_input2, "EVENT");
        assertEquals("EVENT", eventTasks.getTaskList().get(0).getType());
        assertEquals("An Event Description", eventTasks.getTaskList().get(0).getDescription());
        assertEquals(10, eventTasks.getTaskList().get(0).getStartDay());
        assertEquals(11, eventTasks.getTaskList().get(0).getStartMonth());
        assertEquals(18, eventTasks.getTaskList().get(0).getStartYear());
        assertEquals(12, eventTasks.getTaskList().get(0).getEndDay());
        assertEquals(11, eventTasks.getTaskList().get(0).getEndMonth());
        assertEquals(18, eventTasks.getTaskList().get(0).getEndYear());
        assertEquals("LOW", eventTasks.getTaskList().get(0).getImportance());
        assertEquals("NONE", eventTasks.getTaskList().get(0).getComments());

    }

    @Test
    void testAddTaskAndView() {
        // adding was already tested.
        Scanner user_input = new Scanner("A General Task Description\n10\n10\n18\nLOW");
        generalTasks.addTask(user_input, "TASK");

        Scanner user_input2 = new Scanner("An Event Description\n10\n11\n18\n12\n11\n18\nLOW\nNONE");
        eventTasks.addTask(user_input2, "EVENT");

        generalTasks.printItems();
        eventTasks.printItems();
    }

    @Test
    void testAddMultipleTasksValidInput() {
        Scanner user_input1 = new Scanner("A Task Description\n10\n11\n18\nLOW");
        generalTasks.addTask(user_input1, "TASK");
        Scanner user_input2 = new Scanner("Another Task Description\n10\n10\n18\nMEDIUM");
        generalTasks.addTask(user_input2, "TASK");
        Scanner user_input3 = new Scanner("An Event Description\n10\n11\n18\n11\n11\n18\nHIGH\nNONE");
        eventTasks.addTask(user_input3, "EVENT");
        Scanner user_input4 = new Scanner("Another Event Description\n29\n11\n18\n30\n11\n18\nEXTREME\nNONE");
        eventTasks.addTask(user_input4, "EVENT");

        assertEquals( "TASK", generalTasks.getTaskList().get(0).getType());
        assertEquals("A Task Description", generalTasks.getTaskList().get(0).getDescription());
        assertEquals(10, generalTasks.getTaskList().get(0).getDay());
        assertEquals(11, generalTasks.getTaskList().get(0).getMonth());
        assertEquals(18, generalTasks.getTaskList().get(0).getYear());
        assertEquals("LOW", generalTasks.getTaskList().get(0).getImportance());

        assertEquals( "TASK", generalTasks.getTaskList().get(1).getType());
        assertEquals("Another Task Description", generalTasks.getTaskList().get(1).getDescription());
        assertEquals(10, generalTasks.getTaskList().get(1).getDay());
        assertEquals(10, generalTasks.getTaskList().get(1).getMonth());
        assertEquals(18, generalTasks.getTaskList().get(1).getYear());
        assertEquals("MEDIUM", generalTasks.getTaskList().get(1).getImportance());

        assertEquals( "EVENT", eventTasks.getTaskList().get(0).getType());
        assertEquals("An Event Description", eventTasks.getTaskList().get(0).getDescription());
        assertEquals(10, eventTasks.getTaskList().get(0).getStartDay());
        assertEquals(11, eventTasks.getTaskList().get(0).getStartMonth());
        assertEquals(18, eventTasks.getTaskList().get(0).getStartYear());
        assertEquals(11, eventTasks.getTaskList().get(0).getEndDay());
        assertEquals(11, eventTasks.getTaskList().get(0).getEndMonth());
        assertEquals(18, eventTasks.getTaskList().get(0).getEndYear());
        assertEquals("HIGH", eventTasks.getTaskList().get(0).getImportance());
        assertEquals("NONE", eventTasks.getTaskList().get(0).getComments());

        assertEquals( "EVENT", eventTasks.getTaskList().get(1).getType());
        assertEquals("Another Event Description", eventTasks.getTaskList().get(1).getDescription());
        assertEquals(29, eventTasks.getTaskList().get(1).getStartDay());
        assertEquals(11, eventTasks.getTaskList().get(1).getStartMonth());
        assertEquals(18, eventTasks.getTaskList().get(1).getStartYear());
        assertEquals(30, eventTasks.getTaskList().get(1).getEndDay());
        assertEquals(11, eventTasks.getTaskList().get(1).getEndMonth());
        assertEquals(18, eventTasks.getTaskList().get(1).getEndYear());
        assertEquals("EXTREME", eventTasks.getTaskList().get(1).getImportance());
        assertEquals("NONE", eventTasks.getTaskList().get(1).getComments());
    }

    @Test
    void testAddMultipleTasksAndView() {
        // Adding multiple was already tested.
        Scanner user_input1 = new Scanner("A Task Description\n10\n11\n18\nLOW");
        generalTasks.addTask(user_input1, "TASK");
        Scanner user_input2 = new Scanner("Another Task Description\n10\n10\n18\nMEDIUM");
        generalTasks.addTask(user_input2, "TASK");
        Scanner user_input3 = new Scanner("An Event Description\n10\n11\n18\n11\n11\n18\nHIGH\nNONE");
        eventTasks.addTask(user_input3, "EVENT");
        Scanner user_input4 = new Scanner("Another Event Description\n29\n11\n18\n30\n11\n18\nEXTREME\nNONE");
        eventTasks.addTask(user_input4, "EVENT");

        eventTasks.printItems();
        generalTasks.printItems();
    }

    @Test
        // the functions that get the day, month and year are identical for both types of tasks.
    void testAddMultipleTasksInvalidInputLower() {

        //invalid day (lower boundary)
        Scanner user_input1 = new Scanner("A Task Description\n0\n11\n18\nLOW");
        generalTasks.addTask(user_input1, "TASK");

        //invalid month (lower boundary)
        Scanner user_input2 = new Scanner("Another Task Description\n10\n0\n18\nMEDIUM");
        generalTasks.addTask(user_input2, "TASK");

        //invalid year (lower boundary)
        Scanner user_input3 = new Scanner("An Event Description\n10\n11\n0\n11\n11\n18\nHIGH\nNONE");
        eventTasks.addTask(user_input3, "EVENT");

        //invalid importanceLevel
        Scanner user_input4 = new Scanner("Another Event Description\n29\n11\n18\n30\n11\n18\nHello\nNONE");
        eventTasks.addTask(user_input4, "EVENT");

        assertEquals(0, generalTasks.getTaskList().size());
        assertEquals(0, eventTasks.getTaskList().size());
    }

    @Test
        // the functions that get the day, month and year are identical for both types of tasks.
    void testAddMultipleTasksValidInputLower() {

        //valid day (lower boundary)
        Scanner user_input1 = new Scanner("A Task Description\n1\n10\n19\nLOW");
        generalTasks.addTask(user_input1, "TASK");

        //valid month (lower boundary)
        Scanner user_input2 = new Scanner("Another Task Description\n10\n1\n40\nMEDIUM");
        generalTasks.addTask(user_input2, "TASK");

        //valid year (lower boundary)
        Scanner user_input3 = new Scanner("Yet Another Task Description\n10\n10\n18\nHIGH");
        generalTasks.addTask(user_input3, "TASK");

        assertEquals( "TASK", generalTasks.getTaskList().get(0).getType());
        assertEquals("A Task Description", generalTasks.getTaskList().get(0).getDescription());
        assertEquals(1, generalTasks.getTaskList().get(0).getDay());
        assertEquals(10, generalTasks.getTaskList().get(0).getMonth());
        assertEquals(19, generalTasks.getTaskList().get(0).getYear());
        assertEquals("LOW", generalTasks.getTaskList().get(0).getImportance());

        assertEquals( "TASK", generalTasks.getTaskList().get(1).getType());
        assertEquals("Another Task Description", generalTasks.getTaskList().get(1).getDescription());
        assertEquals(10, generalTasks.getTaskList().get(1).getDay());
        assertEquals(1, generalTasks.getTaskList().get(1).getMonth());
        assertEquals(40, generalTasks.getTaskList().get(1).getYear());
        assertEquals("MEDIUM", generalTasks.getTaskList().get(1).getImportance());

        assertEquals( "TASK", generalTasks.getTaskList().get(0).getType());
        assertEquals("Yet Another Task Description", generalTasks.getTaskList().get(2).getDescription());
        assertEquals(10, generalTasks.getTaskList().get(2).getDay());
        assertEquals(10, generalTasks.getTaskList().get(2).getMonth());
        assertEquals(18, generalTasks.getTaskList().get(2).getYear());
        assertEquals("HIGH", generalTasks.getTaskList().get(2).getImportance());
    }

    @Test
    void testAddMultipleTasksInvalidInputUpper() {

        //invalid day (upper boundary)
        Scanner user_input1 = new Scanner("A Task Description\n32\n10\n18\nLOW");
        generalTasks.addTask(user_input1, "TASK");

        //invalid month (upper boundary)
        Scanner user_input2 = new Scanner("Another Task Description\n10\n13\n18\nMEDIUM");
        generalTasks.addTask(user_input2, "TASK");

        //invalid year (upper boundary)
        Scanner user_input3 = new Scanner("Yet Another Task Description\n10\n10\n100\nHIGH");
        generalTasks.addTask(user_input3, "TASK");

        //invalid day (upper boundary) but valid month
        Scanner user_input4 = new Scanner("A Task Description\n31\n9\n18\nLOW");
        generalTasks.addTask(user_input4, "TASK");

        //invalid day (upper boundary) but valid month
        Scanner user_input5 = new Scanner("A Task Description\n29\n2\n19\nLOW");
        generalTasks.addTask(user_input5, "TASK");

        //invalid day (upper boundary) but valid month and leap year.
        Scanner user_input6 = new Scanner("A Task Description\n30\n2\n20\nLOW");
        generalTasks.addTask(user_input6, "TASK");

        assertEquals(0, generalTasks.getTaskList().size());

    }

    @Test
    void testAddMultipleTasksValidInputUpper() {

        //valid day (upper boundary)
        Scanner user_input1 = new Scanner("A Task Description\n31\n10\n18\nLOW");
        generalTasks.addTask(user_input1, "TASK");

        //valid month (upper boundary)
        Scanner user_input2 = new Scanner("Another Task Description\n10\n12\n18\nMEDIUM");
        generalTasks.addTask(user_input2, "TASK");

        //valid year (upper boundary)
        Scanner user_input3 = new Scanner("Yet Another Task Description\n10\n10\n99\nHIGH");
        generalTasks.addTask(user_input3, "TASK");

        //valid day (upper boundary) + February (non-leap year)
        Scanner user_input4 = new Scanner("Task 1\n28\n2\n19\nLOW");
        generalTasks.addTask(user_input4, "TASK");

        //valid day (upper boundary) + February (non-leap year)
        Scanner user_input5 = new Scanner("Task 2\n29\n2\n20\nLOW");
        generalTasks.addTask(user_input5, "TASK");

        generalTasks.printItems();

        assertEquals( "TASK", generalTasks.getTaskList().get(0).getType());
        assertEquals("A Task Description", generalTasks.getTaskList().get(0).getDescription());
        assertEquals(31, generalTasks.getTaskList().get(0).getDay());
        assertEquals(10, generalTasks.getTaskList().get(0).getMonth());
        assertEquals(18, generalTasks.getTaskList().get(0).getYear());
        assertEquals("LOW", generalTasks.getTaskList().get(0).getImportance());

        assertEquals( "TASK", generalTasks.getTaskList().get(1).getType());
        assertEquals("Another Task Description", generalTasks.getTaskList().get(1).getDescription());
        assertEquals(10, generalTasks.getTaskList().get(1).getDay());
        assertEquals(12, generalTasks.getTaskList().get(1).getMonth());
        assertEquals(18, generalTasks.getTaskList().get(1).getYear());
        assertEquals("MEDIUM", generalTasks.getTaskList().get(1).getImportance());

        assertEquals( "TASK", generalTasks.getTaskList().get(2).getType());
        assertEquals("Yet Another Task Description", generalTasks.getTaskList().get(2).getDescription());
        assertEquals(10, generalTasks.getTaskList().get(2).getDay());
        assertEquals(10, generalTasks.getTaskList().get(2).getMonth());
        assertEquals(99, generalTasks.getTaskList().get(2).getYear());
        assertEquals("HIGH", generalTasks.getTaskList().get(2).getImportance());

        assertEquals( "TASK", generalTasks.getTaskList().get(3).getType());
        assertEquals("Task 1", generalTasks.getTaskList().get(3).getDescription());
        assertEquals(28, generalTasks.getTaskList().get(3).getDay());
        assertEquals(2, generalTasks.getTaskList().get(3).getMonth());
        assertEquals(19, generalTasks.getTaskList().get(3).getYear());
        assertEquals("LOW", generalTasks.getTaskList().get(3).getImportance());

        assertEquals( "TASK", generalTasks.getTaskList().get(4).getType());
        assertEquals("Task 2", generalTasks.getTaskList().get(4).getDescription());
        assertEquals(29, generalTasks.getTaskList().get(4).getDay());
        assertEquals(2, generalTasks.getTaskList().get(4).getMonth());
        assertEquals(20, generalTasks.getTaskList().get(4).getYear());
        assertEquals("LOW", generalTasks.getTaskList().get(4).getImportance());
    }

    @Test
    void testRemoveItem() {
        Scanner user_input1 = new Scanner("A Task Description\n10\n11\n18\nLOW");
        generalTasks.addTask(user_input1, "TASK");

        Scanner user_input2 = new Scanner("A Task Description\n10\n11\n18");
        generalTasks.removeItem(user_input2);

        assertEquals(0, generalTasks.getTaskList().size());

        Scanner user_input3 = new Scanner("An Event Description\n10\n11\n18\n11\n11\n18\nHIGH\nNONE");
        eventTasks.addTask(user_input3, "EVENT");

        Scanner user_input4 = new Scanner("An Event Description\n10\n11\n18\n11\n11\n18");
        eventTasks.removeItem(user_input4);

        assertEquals(0, eventTasks.getTaskList().size());
    }

    @Test
    void testRemoveItemMultiple() {
        Scanner user_input1 = new Scanner("A Task Description\n10\n11\n18\nLOW");
        generalTasks.addTask(user_input1, "TASK");
        Scanner user_input2 = new Scanner("Another Task Description\n10\n10\n18\nMEDIUM");
        generalTasks.addTask(user_input2, "TASK");

        Scanner user_input3a = new Scanner("A Task Description\n10\n11\n18");
        Scanner user_input3b = new Scanner("Another Task Description\n10\n10\n18");
        generalTasks.removeItem(user_input3a);
        generalTasks.removeItem(user_input3b);
        assertEquals(0, generalTasks.getTaskList().size());

        Scanner user_input4 = new Scanner("An Event Description\n10\n11\n18\n11\n11\n18\nHIGH\nNONE");
        eventTasks.addTask(user_input4, "EVENT");
        Scanner user_input5 = new Scanner("Another Event Description\n29\n11\n18\n30\n11\n18\nEXTREME\nNONE");
        eventTasks.addTask(user_input5, "EVENT");

        Scanner user_input6a = new Scanner("An Event Description\n10\n11\n18\n11\n11\n18");
        Scanner user_input6b = new Scanner("Another Event Description\n29\n11\n18\n30\n11\n18");
        eventTasks.removeItem(user_input6a);
        eventTasks.removeItem(user_input6b);
        assertEquals(0, eventTasks.getTaskList().size());
    }


}

