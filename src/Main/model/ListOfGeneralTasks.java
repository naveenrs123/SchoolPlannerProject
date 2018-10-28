package model;

import exceptions.date.BadDateInputException;
import exceptions.input.InvalidImportanceException;
import inputs.GeneralTask;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ListOfGeneralTasks extends ListOfTasks {

    private ArrayList<GeneralTask> generalTaskList;

    public ListOfGeneralTasks() {
        generalTaskList = new ArrayList<>();
    }

    // REQUIRES: taskType == "TASK"
    // MODIFIES: this
    // EFFECTS: gets input from the user used to create a GeneralTask
    public void addTask(Scanner user_input, String taskType) {
        System.out.println("General tasks need no extra details.");
        String description = userDescription(user_input);

        try {
            String day = userDay(user_input);
            String month = userMonth(user_input);
            String year = userYear(user_input);
            validateDate(day, month, year);
            String importanceLevel = getImportanceLevel(user_input);

            createTask(taskType, description, day, month, year, importanceLevel);
        } catch (BadDateInputException bdiex) {
            System.out.println(bdiex.getMessage());
        } catch (InvalidImportanceException iiex) {
            System.out.println(iiex.getMessage());
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
        System.out.println("A new task has been created.");
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

    // EFFECTS: gets all stored tasks.
    public void printItems() {

        if (generalTaskList.size() > 0) {
            for (GeneralTask task : generalTaskList) {
                task.printItem();
                System.out.println();
            }
        } else {
            System.out.println("You have no general tasks.");
            System.out.println();
        }
    }

    // loads a single item.
    public void loadSingleItem(String currentItem) {
        ArrayList<String> tempTaskDetails;
        tempTaskDetails = new ArrayList<>(Arrays.asList(currentItem.split(",")));
        loadTask(tempTaskDetails);
    }

    // EFFECTS: gets the generalTaskList
    public ArrayList<GeneralTask> getGeneralTaskList() {
        return generalTaskList;
    }
}

