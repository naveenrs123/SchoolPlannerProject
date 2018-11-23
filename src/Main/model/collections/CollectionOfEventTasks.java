package model.collections;

import exceptions.EmptyCollectionException;
import exceptions.ItemNotFoundException;
import inputs.EventTask;
import model.observerPattern.Subject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CollectionOfEventTasks extends Subject implements CollectionOfItems {

    private ArrayList<EventTask> eventTaskList;

    public CollectionOfEventTasks() {
        eventTaskList = new ArrayList<>();
    }

    public void addItem(ArrayList<String> item) throws IOException {
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
                           String endDay, String endMonth, String endYear, String importanceLevel, String comments) throws IOException {
        EventTask newETask = new EventTask(taskType, description, Integer.parseInt(startDay), Integer.parseInt(startMonth),
                Integer.parseInt(startYear), Integer.parseInt(endDay), Integer.parseInt(endMonth), Integer.parseInt(endYear),
                importanceLevel, comments);
        eventTaskList.add(newETask);
        saveTask(newETask);
        notifyObservers();
    }

    // EFFECTS: saves newTask to a file.
    public void saveTask(EventTask newETask) throws IOException {
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
            throw new IOException();
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

    // MODIFIES: this
    // EFFECTS: removes an EventTask from the list.
    public void removeItem(ArrayList<String> item) throws ItemNotFoundException, EmptyCollectionException {
        if (eventTaskList.isEmpty()) {
            throw new EmptyCollectionException();
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
    private void checkAndRemove(String description, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear) throws ItemNotFoundException {
        for (EventTask task : eventTaskList) {
            if (description.equals(task.getDescription()) && Integer.parseInt(startDay) == task.getStartDay()
                    && Integer.parseInt(startMonth) == task.getStartMonth() && Integer.parseInt(startYear) == task.getStartYear()
                    && Integer.parseInt(endDay) == task.getEndDay() && Integer.parseInt(endMonth) == task.getEndMonth() &&
                    Integer.parseInt(endYear) == task.getEndYear()) {
                eventTaskList.remove(task);
                notifyObservers();
                return;
            }
        }
        throw new ItemNotFoundException();
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
}
