package model.collections;

import exceptions.date.BadDateInputException;
import exceptions.input.InvalidImportanceException;
import inputs.GeneralTask;
import model.inputHandling.TasksInputHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CollectionOfGeneralTasks implements CollectionOfItems {

    private ArrayList<GeneralTask> generalTaskList;
    private TasksInputHandler inputHandler;

    public CollectionOfGeneralTasks() {
        generalTaskList = new ArrayList<>();
        inputHandler = new TasksInputHandler();

    }

    // REQUIRES: taskType == "TASK"
    // MODIFIES: this
    // EFFECTS: gets input from the user used to create a GeneralTask
    public void addItem(Scanner user_input) {
        String taskType = "TASK";
        System.out.println("General tasks need no extra details.");
        String description = inputHandler.userDescription(user_input);
        try {
            String day = inputHandler.userDay(user_input);
            String month = inputHandler.userMonth(user_input);
            String year = inputHandler.userYear(user_input);
            inputHandler.validateDate(day, month, year);
            String importanceLevel = inputHandler.getImportanceLevel(user_input);
            createTask(taskType, description, day, month, year, importanceLevel);
        } catch (BadDateInputException bdiex) {
            System.out.println(bdiex.getMessage()); System.out.println("Your task was not created.");
        } catch (InvalidImportanceException iiex) {
            System.out.println(iiex.getMessage()); System.out.println("Your task was not created.");
        } finally {
            System.out.println("Adding Process finished.");
        }
    }

    // REQUIRES: valid taskType, day, month, year and importanceLevel
    // MODIFIES: this
    // EFFECTS: creates a new GeneralTask, adds it to a list, and saves it to a file.
    public void createTask(String taskType, String description, String day, String month, String year, String importanceLevel) {
        GeneralTask newGTask = new GeneralTask(taskType, description, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year),
                importanceLevel);
        generalTaskList.add(newGTask);
        saveTask(newGTask);
    }

    // EFFECTS: saves newTask to a file.
    public void saveTask(GeneralTask newGTask) {

        String filename = "listofgeneraltasks.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newGTask.getType() + "," + newGTask.getDescription() + ",");
            bufferedWriter.write(newGTask.getDay() + "," + newGTask.getMonth() + "," + newGTask.getYear() + ",");
            bufferedWriter.write(newGTask.getImportance());
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a GeneralTask into the list
    public void loadTask(ArrayList<String> currentTask) {
        String taskType = currentTask.get(0);
        String description = currentTask.get(1);
        String day = currentTask.get(2);
        String month = currentTask.get(3);
        String year = currentTask.get(4);
        String importance = currentTask.get(5);

        GeneralTask tempGTask = new GeneralTask(taskType, description, Integer.parseInt(day), Integer.parseInt(month),
                Integer.parseInt(year), importance);
        generalTaskList.add(tempGTask);
    }

    // MODIFIES: this
    // EFFECTS: removes a GeneralTask from the list.
    public void removeItem(Scanner user_input) {
        if (generalTaskList.isEmpty()) {
            System.out.println("You have no general tasks to remove.");
            return;
        }
        String description = inputHandler.userDescription(user_input);
        String day; String month; String year;
        try {
            day = inputHandler.userDay(user_input);
            month = inputHandler.userMonth(user_input);
            year = inputHandler.userYear(user_input);
            checkAndRemove(description, day, month, year);
        } catch (BadDateInputException bdiex) {
            System.out.println(bdiex.getMessage());
            System.out.println("Try removing the task again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a GeneralTask from the list.
    private void checkAndRemove(String description, String day, String month, String year) {
        for (GeneralTask task : generalTaskList) {
            if (description.equals(task.getDescription()) && Integer.parseInt(day) == task.getDay()
                    && Integer.parseInt(month) == task.getMonth() && Integer.parseInt(year) == task.getYear()) {
                generalTaskList.remove(task);
                System.out.println("The task was removed successfully.");
                break;
            } else {
                System.out.println("The task you tried to remove was not found.");
            }
        }
    }

    // EFFECTS: gets all stored tasks.
    public void printItems() {
        if (generalTaskList.size() == 1) {
            System.out.println("There is " + generalTaskList.size() + " task stored.");
        } else {
            System.out.println("There are " + generalTaskList.size() + " tasks stored.");
        }
        System.out.println();
        if (generalTaskList.size() > 0) {
            for (GeneralTask task : generalTaskList) {
                task.printItem();
                System.out.println();
            }
        }
    }

    // loads a single item.
    public void loadSingleItem(String currentItem) {
        ArrayList<String> tempTaskDetails;
        tempTaskDetails = new ArrayList<>(Arrays.asList(currentItem.split(",")));
        loadTask(tempTaskDetails);
    }

    // EFFECTS: saves all the generalTasks to a file.
    public void saveCollection() {
        String filename = "listofgeneraltasks.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.close();
            for (GeneralTask gt : generalTaskList) {
                saveTask(gt);
            }
        } catch (IOException e) {
            System.out.println("Error while saving to file.");
        }
    }

    // EFFECTS: gets the generalTaskList
    public ArrayList<GeneralTask> getTaskList() {
        return generalTaskList;
    }

    public TasksInputHandler getInputHandler() {
        return inputHandler;
    }
}

