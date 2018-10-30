package ui;

import model.InputScreen;
import model.CollectionOfGeneralTasks;
import model.CollectionOfEventTasks;

import java.io.*;
import java.util.*;

public class TasksScreen implements InputScreen {

    private CollectionOfGeneralTasks logt = new CollectionOfGeneralTasks();
    private CollectionOfEventTasks loet = new CollectionOfEventTasks();

    // Creates a new TasksScreen object.
    public TasksScreen() {

    }

    // EFFECTS: gets input from the user to decide whether they want to add tasks or view tasks.
    public int handleOptions(Scanner user_input) {
        int addOrView;
        while (true) {
            try {
                System.out.println("What do you want to do?");
                System.out.println("1. View Tasks\n2. Add Tasks\n3. Remove Tasks");
                addOrView = user_input.nextInt();
                user_input.nextLine();
                if (addOrView == 1) {
                    return 1;
                } else if (addOrView == 2) {
                    return 2;
                } else if (addOrView == 3) {
                    return 3;
                } else {
                    System.out.println("Enter a valid choice.");
                }
            }
            catch (InputMismatchException n) {
                System.out.println("You must enter an integer.");
                user_input.nextLine();
            }
        }
    }

    // EFFECTS: prints out basic details of all tasks.
    public void addingItemDetails() {
        System.out.println("All tasks must have the following fields:");
        System.out.println("-> A Task Type\n-> A Text Description\n-> A numerical date (day, month, year)\n" +
                "-> A rank of importance (LOW, MEDIUM, HIGH, EXTREME");
    }

    // EFFECTS: gets the user's choice of task to add.
    public String chooseTaskType(Scanner user_input) {
        System.out.println("What task would you like to add?\n1. Task\n2. Event");
        String choice = user_input.nextLine();

        while (!(choice.matches("^[0-9]*$")) || !(choice.equals("1") || choice.equals("2"))) {
            System.out.println("You didn't choose a valid option, try again!");
            choice = user_input.nextLine();
        }
        if (choice.equals("1")) {
            return "TASK";
        } else if (choice.equals("2")) {
            return "EVENT";
        } else {
            return "";
        }
    }

    // EFFECTS: gets the user's choice of task to add.
    public String chooseTaskTypeForRemoval(Scanner user_input) {
        System.out.print("Task Type: ");
        String choice = user_input.nextLine().toUpperCase();
        while (!(choice.equals("TASK") || choice.equals("EVENT"))) {
            System.out.println("You didn't choose a valid Task Type, try again.");
            choice = user_input.nextLine();
        }
        return choice;
    }

    // MODIFIES: this
    // EFFECTS: Allows user to add a Task and then stores the created Task. Task is not added if
    //          user input is invalid.
    public void addToListObject(Scanner user_input)
    {
        addingItemDetails();
        String taskType = chooseTaskType(user_input);

        if (taskType.equals("TASK")) {
            logt.addTask(user_input, taskType);
        } else if (taskType.equals("EVENT")) {
            loet.addTask(user_input, taskType);
        } else {
            System.out.println("Something went wrong, your item was not created.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads all tasks stored in a file into the generalTaskList.
    public int loadItemsIntoListObject() {
        try {
            loadEventTaskIntoListObject();
            loadGeneralTaskIntoListObject();
        }
        catch (FileNotFoundException fnfex) {
            return 1;
        } catch (IOException ioex) {
            return 2;
        }
        return 0;
    }

    public void loadGeneralTaskIntoListObject() throws FileNotFoundException, IOException {
        String filename = "listofgeneraltasks.csv";

        String currentTask;
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((currentTask = bufferedReader.readLine()) != null) {
                logt.loadSingleItem(currentTask);
            }

    }

    public void loadEventTaskIntoListObject() throws FileNotFoundException, IOException {
        String filename = "listofeventtasks.csv";

        String currentTask;
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((currentTask = bufferedReader.readLine()) != null) {
            loet.loadSingleItem(currentTask);
        }

    }

    public void removeItem(Scanner user_input) {
        loet.printItems();
        logt.printItems();
        System.out.println("To remove a task, you must select a task type, its description and its date.");
        String taskType = chooseTaskTypeForRemoval(user_input);
        if (taskType.equals("TASK")) {
            logt.removeItem(user_input);
        } if (taskType.equals("EVENT")) {
            loet.removeItem(user_input);
        }

    }

    public void printStoredItems() {
        System.out.println("**TASKS**\n");
        loet.printItems();
        logt.printItems();
    }

    public void saveList() {
        logt.saveCollection();
        loet.saveCollection();
    }

    public CollectionOfEventTasks getLoet() {
        return loet;
    }

    public CollectionOfGeneralTasks getLogt() {
        return logt;
    }
}
