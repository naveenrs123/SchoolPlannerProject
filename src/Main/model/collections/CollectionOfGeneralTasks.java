package model.collections;

import exceptions.EmptyCollectionException;
import exceptions.ItemNotFoundException;
import inputs.GeneralTask;
import model.observerPattern.Subject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CollectionOfGeneralTasks extends Subject implements CollectionOfItems {

    private ArrayList<GeneralTask> generalTaskList;

    public CollectionOfGeneralTasks() {
        generalTaskList = new ArrayList<>();
    }

    public void addItem(ArrayList<String> item) throws IOException {
        String taskType = item.get(0);
        String description = item.get(1);
        String day = item.get(2);
        String month = item.get(3);
        String year = item.get(4);
        String importanceLevel = item.get(5);
        createTask(taskType, description, day, month, year, importanceLevel);

    }

    // REQUIRES: valid taskType, day, month, year and importanceLevel
    // MODIFIES: this
    // EFFECTS: creates a new GeneralTask, adds it to a list, and saves it to a file.
    public void createTask(String taskType, String description, String day, String month, String year, String importanceLevel) throws IOException {
        GeneralTask newGTask = new GeneralTask(taskType, description, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year),
                importanceLevel);
        generalTaskList.add(newGTask);
        saveTask(newGTask);
        notifyObservers();
    }

    // EFFECTS: saves newTask to a file.
    public void saveTask(GeneralTask newGTask) throws IOException {
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
            throw new IOException();
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
    public void removeItem(ArrayList<String> item) throws ItemNotFoundException, EmptyCollectionException {
        if (generalTaskList.isEmpty()) {
            throw new EmptyCollectionException();
        }
        String description = item.get(1);
        String day; String month; String year;
        day = item.get(2);
        month = item.get(3);
        year = item.get(4);
        checkAndRemove(description, day, month, year);
    }

    // MODIFIES: this
    // EFFECTS: removes a GeneralTask from the list.
    private void checkAndRemove(String description, String day, String month, String year) throws ItemNotFoundException {
        for (GeneralTask task : generalTaskList) {
            if (description.equals(task.getDescription()) && Integer.parseInt(day) == task.getDay()
                    && Integer.parseInt(month) == task.getMonth() && Integer.parseInt(year) == task.getYear()) {
                generalTaskList.remove(task);
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
}

