package model.collections;

import exceptions.date.BadDateInputException;
import exceptions.input.InvalidImportanceException;
import inputs.EventTask;
import model.inputHandling.TasksInputHandler;
import model.observerPattern.Subject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CollectionOfEventTasks extends Subject implements CollectionOfItems {

    private ArrayList<EventTask> eventTaskList;
    private TasksInputHandler inputHandler;

    public CollectionOfEventTasks() {
        eventTaskList = new ArrayList<>();
        inputHandler = new TasksInputHandler();
    }

    // REQUIRES: taskType == "EVENT"
    // MODIFIES: this
    // EFFECTS: gets input from the user used to create a GeneralTask
    public void addItem(Scanner user_input) {
        String taskType = "EVENT";
        System.out.println("Event Tasks require a few extra details:\n-> A start date\n-> An end date\n-> Optional Comments");
        String description = inputHandler.userDescription(user_input);
        try {
            System.out.println("START DAY");
            String startDay = inputHandler.userDay(user_input);
            String startMonth = inputHandler.userMonth(user_input);
            String startYear = inputHandler.userYear(user_input);
            inputHandler.validateDate(startDay, startMonth, startYear);
            System.out.println("END DAY:");
            String endDay = inputHandler.userDay(user_input);
            String endMonth = inputHandler.userMonth(user_input);
            String endYear = inputHandler.userYear(user_input);
            inputHandler.validateDate(endDay, endMonth, endYear);
            String importanceLevel = inputHandler.getImportanceLevel(user_input);
            System.out.println("If you have no comments, type NONE.");
            String comments = inputHandler.getComments(user_input);
            createTask(taskType, description, startDay, startMonth, startYear, endDay, endMonth, endYear, importanceLevel, comments);
        } catch (BadDateInputException bdiex) {
            System.out.println(bdiex.getMessage());
        } catch (InvalidImportanceException iiex) {
            System.out.println(iiex.getMessage());
        }
    }

    public void addItem(ArrayList<String> item) {
        String taskType = item.get(0);
        String description = item.get(1);
        String startDay = item.get(2);
        String startMonth = item.get(3);
        String startYear = item.get(4);
        String endDay = item.get(5);
        String endMonth = item.get(6);
        String endYear = item.get(7);
        String importanceLevel = item.get(8);
        String comments = item.get(9);

        createTask(taskType, description, startDay, startMonth, startYear, endDay, endMonth, endYear, importanceLevel, comments);
    }

    // REQUIRES: valid taskType, startDay, startMonth, startYear, endDay, endMonth, endYear, and importanceLevel
    // MODIFIES: this
    // EFFECTS: creates a new EventTask, adds it to a list, and saves it to a file.
    public void createTask(String taskType, String description, String startDay, String startMonth, String startYear,
                           String endDay, String endMonth, String endYear, String importanceLevel, String comments) {
        EventTask newETask = new EventTask(taskType, description, Integer.parseInt(startDay), Integer.parseInt(startMonth),
                Integer.parseInt(startYear), Integer.parseInt(endDay), Integer.parseInt(endMonth), Integer.parseInt(endYear),
                importanceLevel, comments);
        eventTaskList.add(newETask);
        saveTask(newETask);
        notifyObservers();
    }

    // EFFECTS: saves newTask to a file.
    public void saveTask(EventTask newETask) {
        String filename = "listofeventtasks.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newETask.getType() + "," + newETask.getDescription() + ",");
            bufferedWriter.write(newETask.getStartDay() + "," + newETask.getStartMonth() + "," + newETask.getStartYear() + ",");
            bufferedWriter.write(newETask.getEndDay() + "," + newETask.getEndMonth() + "," + newETask.getEndYear() + ",");
            bufferedWriter.write(newETask.getImportance() + "," + newETask.getComments());
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads an EventTask into the list
    public void loadTask(ArrayList<String> currentEventTask) {
        String taskType = currentEventTask.get(0);
        String description = currentEventTask.get(1);
        String startDay = currentEventTask.get(2);
        String startMonth = currentEventTask.get(3);
        String startYear = currentEventTask.get(4);
        String endDay = currentEventTask.get(5);
        String endMonth = currentEventTask.get(6);
        String endYear = currentEventTask.get(7);
        String importance = currentEventTask.get(8);
        String comments = currentEventTask.get(9);
        EventTask tempETask = new EventTask(taskType, description, Integer.parseInt(startDay), Integer.parseInt(startMonth),
                Integer.parseInt(startYear), Integer.parseInt(endDay), Integer.parseInt(endMonth), Integer.parseInt(endYear),
                importance, comments);
        eventTaskList.add(tempETask);
    }

    // EFFECTS: gets all stored tasks.
    public void printItems() {
        if (eventTaskList.size() == 1) {
            System.out.println("There is " + eventTaskList.size() + " event stored.");
        } else {
            System.out.println("There are " + eventTaskList.size() + " events stored.");
        }
        System.out.println();
        if (eventTaskList.size() > 0) {
            for (EventTask task : eventTaskList) {
                task.printItem();
                System.out.println();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an EventTask from the list.
    public void removeItem(Scanner user_input) {
        if (eventTaskList.isEmpty()) {
            System.out.println("You have no event tasks to remove.");
            return;
        }
        String description = inputHandler.userDescription(user_input);
        String startDay; String startMonth; String startYear; String endDay; String endMonth; String endYear;
        try {
            System.out.println("START DATE:");
            startDay = inputHandler.userDay(user_input);
            startMonth = inputHandler.userMonth(user_input);
            startYear = inputHandler.userYear(user_input);
            System.out.println("END DATE:");
            endDay = inputHandler.userDay(user_input);
            endMonth = inputHandler.userMonth(user_input);
            endYear = inputHandler.userYear(user_input);
            checkAndRemove(description, startDay, startMonth, startYear, endDay, endMonth, endYear);
        } catch (BadDateInputException bdiex) {
            System.out.println(bdiex.getMessage());
            System.out.println("Try removing the task again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an EventTask from the list.
    public void removeItem(ArrayList<String> item) {
        if (eventTaskList.isEmpty()) {
            return;
        }
        String description = item.get(1);
        String startDay; String startMonth; String startYear; String endDay; String endMonth; String endYear;
        startDay = item.get(2);
        startMonth = item.get(3);
        startYear = item.get(4);
        endDay = item.get(5);
        endMonth = item.get(6);
        endYear = item.get(7);
        checkAndRemove(description, startDay, startMonth, startYear, endDay, endMonth, endYear);
    }

    // MODIFIES: this
    // EFFECTS: removes an EventTask from the list.
    private void checkAndRemove(String description, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear) {
        for (EventTask task : eventTaskList) {
            if (description.equals(task.getDescription()) && Integer.parseInt(startDay) == task.getStartDay()
                    && Integer.parseInt(startMonth) == task.getStartMonth() && Integer.parseInt(startYear) == task.getStartYear()
                    && Integer.parseInt(endDay) == task.getEndDay() && Integer.parseInt(endMonth) == task.getEndMonth() &&
                    Integer.parseInt(endYear) == task.getEndYear()) {
                eventTaskList.remove(task);
                notifyObservers();
                break;
            } else {

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
        String filename = "listofeventtasks.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.close();
            for (EventTask et : eventTaskList) {
                saveTask(et);
            }
        } catch (IOException e) {
            System.out.println("Error while saving to file.");
        }
    }

    // EFFECTS: gets the eventTaskList
    public ArrayList<EventTask> getTaskList() {
        return eventTaskList;
    }

    public TasksInputHandler getInputHandler() {
        return inputHandler;
    }
}
