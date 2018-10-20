package tests;

import inputs.EventTask;
import inputs.GeneralTask;
import inputs.Task;

import org.junit.jupiter.api.Test;

// Testing of valid/invalid input handled in TasksScreenTest
class TaskTest {

    @Test
    void testGeneralTask() {
        // if it runs, the task was created.
        GeneralTask newGTask = new GeneralTask("TASK","This is a task", 10, 10, 18, "LOW");
    }

    @Test
    void testEventTask() {
        EventTask newETask = new EventTask("EVENT", "This is an event", 12, 11, 18,
                13, 11, 18, "MEDIUM", "NONE");
    }

    @Test
    void testPrintTasks() {
        GeneralTask newGTask = new GeneralTask("TASK","This is a task", 10, 10, 18, "LOW");
        EventTask newETask = new EventTask("EVENT", "This is an event", 12, 11, 18,
                13, 11, 18, "MEDIUM", "NONE");
        newGTask.printItem();
        newETask.printItem();

    }

    @Test
    void testDifferentImportanceLevels() {
        GeneralTask gTask1 = new GeneralTask("TASK","This is a task", 10, 10, 18, "LOW");
        EventTask eTask1 = new EventTask("EVENT", "This is an event", 12, 11, 18,
                13, 11, 18, "LOW", "NONE");
        gTask1.printItem();
        eTask1.printItem();

        GeneralTask gTask2 = new GeneralTask("TASK","This is a task", 10, 10, 18, "MEDIUM");
        EventTask eTask2 = new EventTask("EVENT", "This is an event", 12, 11, 18,
                13, 11, 18, "MEDIUM", "NONE");
        gTask2.printItem();
        eTask2.printItem();

        GeneralTask gTask3 = new GeneralTask("TASK","This is a task", 10, 10, 18, "HIGH");
        EventTask eTask3 = new EventTask("EVENT", "This is an event", 12, 11, 18,
                13, 11, 18, "HIGH", "NONE");
        gTask3.printItem();
        eTask3.printItem();

        GeneralTask gTask4 = new GeneralTask("TASK","This is a task", 10, 10, 18, "EXTREME");
        EventTask eTask4 = new EventTask("EVENT", "This is an event", 12, 11, 18,
                13, 11, 18, "EXTREME", "NONE");
        gTask4.printItem();
        eTask4.printItem();

    }


}
