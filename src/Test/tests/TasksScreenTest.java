package tests;

import inputs.EventTask;
import inputs.GeneralTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TasksScreen;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TasksScreenTest {

    private TasksScreen screenTasks;

    @BeforeEach
    void setup() {
        screenTasks = new TasksScreen();
    }

    // BASIC TESTS

    @Test
    void testHandleOptions1() {
        Scanner user_input = new Scanner("1\n");
        int choice = screenTasks.handleOptions(user_input);

        assertEquals(1, choice);
    }

    @Test
    void testHandleOptions2() {
        Scanner user_input = new Scanner("2\n");
        int choice = screenTasks.handleOptions(user_input);

        assertEquals(2, choice);
    }

    @Test
    void testHandleOptions3() {
        Scanner user_input = new Scanner("3\n");
        int choice = screenTasks.handleOptions(user_input);

        assertEquals(3, choice);
    }

    @Test
    void testAddOrViewInvalid() {
        Scanner user_input = new Scanner("sdfg\n5\n1\n");
        int choice = screenTasks.handleOptions(user_input);

        assertEquals(1, choice);
    }

    @Test
    void testLoadItemsIntoListObject() {
        GeneralTask newGTask = new GeneralTask("TASK","This is a task", 10, 10, 18, "LOW");
        EventTask newETask = new EventTask("EVENT", "This is an event", 12, 11, 18,
                13, 11, 18, "MEDIUM", "NONE");
        screenTasks.getLogt().saveTask(newGTask);
        screenTasks.getLoet().saveTask(newETask);
        screenTasks.loadItemsIntoListObject();
        assertEquals(1, screenTasks.getLogt().getTaskList().size());
        assertEquals(1, screenTasks.getLoet().getTaskList().size());
    }
}
