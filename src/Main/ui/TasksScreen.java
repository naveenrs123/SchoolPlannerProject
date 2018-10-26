package ui;

import exceptions.date.*;
import exceptions.input.InvalidImportanceException;
import inputs.EventTask;
import inputs.GeneralTask;
import model.InputScreen;
import model.Loadable;

import java.io.*;
import java.util.*;

public class TasksScreen implements InputScreen, Loadable {

    private ArrayList<GeneralTask> generalTaskList = new ArrayList<>();
    private ArrayList<EventTask> eventTaskList = new ArrayList<>();

    // Creates a new TasksScreen object.
    public TasksScreen() {

    }

    // EFFECTS: gets input from the user to decide whether they want to add tasks or view tasks.
    public int addOrView(Scanner user_input) {
        int addOrView;
        while (true) {
            try {
                System.out.println("What do you want to do?");
                System.out.println("1. View Tasks\n2. Add Tasks");
                addOrView = user_input.nextInt();
                user_input.nextLine();
                if (addOrView == 1) {
                    return 1;
                } else if (addOrView == 2) {
                    return 2;
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

    // EFFECTS: gets task description from the user.
    public String userDescription(Scanner user_input) {
        System.out.print("Enter a description: ");
        String description = user_input.nextLine();
        return description;
    }

    // EFFECTS: gets and checks day provided by the user.
    public String userDay(Scanner user_input) throws BadDayException {
        System.out.print("Enter the day: ");
        String day = user_input.nextLine();
        if (!(day.matches("^[0-9]*$")) || Integer.parseInt(day) > 31 || Integer.parseInt(day) <= 0) {
            throw new BadDayException("You entered an invalid day. Your task was not created.");
        } else {
            return day;
        }
    }

    // EFFECTS: gets and checks month provided by the user.
    public String userMonth(Scanner user_input) throws BadMonthException {
        System.out.print("Enter the month: ");
        String month = user_input.nextLine();
        if (!(month.matches("^[0-9]*$")) || Integer.parseInt(month) > 12 || Integer.parseInt(month) <= 0) {
            throw new BadMonthException("You entered an invalid month. Your task was not created.");
        } else {
            return month;
        }
    }

    // EFFECTS: gets and checks year provided by the user.
    public String userYear(Scanner user_input) throws BadYearException {
        System.out.print("Enter the year: ");
        String year = user_input.nextLine();
        if (!(year.matches("^[0-9]*$")) || Integer.parseInt(year) > 99 || Integer.parseInt(year) < 18) {
            throw new BadYearException("You entered an invalid year. Your task was not created.");
        } else {
            return year;
        }
    }

    // EFFECTS: checks date provided by the user.
    public boolean validateDate(String day, String month, String year) throws InvalidDateException {
        int intDay = Integer.parseInt(day);
        int intMonth = Integer.parseInt(month);
        int intYear = Integer.parseInt(year);
        ArrayList<Integer> months30 = new ArrayList<>(Arrays.asList(4, 6, 9, 11));

        if ((months30.contains(intMonth) && intDay > 30) || (intMonth == 2 && intDay > 28 && intYear % 4 != 0)
                || (intMonth == 2 && intDay > 29 && intYear % 4 == 0)) {
            throw new InvalidDateException("Your date does not make sense. Your task was not created.");
        } else {
            return true;
        }
    }
    // EFFECTS: gets and checks importance level provided by the user.
    public String getImportanceLevel(Scanner user_input) throws InvalidImportanceException {
        System.out.print("Enter the importance level: ");
        String importanceLevel = user_input.nextLine().toUpperCase();
        if (!(importanceLevel.equals("LOW") || importanceLevel.equals("MEDIUM")
                || importanceLevel.equals("HIGH") || importanceLevel.equals("EXTREME"))) {
            throw new InvalidImportanceException("You entered an invalid importance level. Your task was not created.");
        } else {
            return importanceLevel;
        }
    }

    // EFFECTS: gets comments from the user.
    public String getComments(Scanner user_input) {
        System.out.print("Comments: ");
        String comments = user_input.nextLine();
        return comments;
    }

    // REQUIRES: valid taskType, day, month, year and importanceLevel
    // MODIFIES: this
    // EFFECTS: creates a new GeneralTask, adds it to a list, and saves it to a file.
    public void createGeneralTask(String taskType, String description, String day, String month, String year, String importanceLevel) {

        GeneralTask newGTask = new GeneralTask(taskType, description, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year),
                importanceLevel);
        generalTaskList.add(newGTask);
        saveGeneralTask(newGTask);
        System.out.println("A new task has been created.");
    }

    // REQUIRES: valid taskType, startDay, startMonth, startYear, endDay, endMonth, endYear, and importanceLevel
    // MODIFIES: this
    // EFFECTS: creates a new EventTask, adds it to a list, and saves it to a file.
    public void createEventTask(String taskType, String description, String startDay, String startMonth, String startYear,
                            String endDay, String endMonth, String endYear, String importanceLevel, String comments) {

        EventTask newETask = new EventTask(taskType, description, Integer.parseInt(startDay), Integer.parseInt(startMonth),
                Integer.parseInt(startYear), Integer.parseInt(endDay), Integer.parseInt(endMonth), Integer.parseInt(endYear),
                importanceLevel, comments);

        eventTaskList.add(newETask);
        saveEventTask(newETask);
        System.out.println("A new task has been created.");
    }

    // EFFECTS: prints out basic details of all tasks.
    public void itemDetails() {
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

    // REQUIRES: taskType == "TASK"
    // MODIFIES: this
    // EFFECTS: gets input from the user used to create a GeneralTask
    public void addGeneralTask(Scanner user_input, String taskType) {
        System.out.println("General tasks need no extra details.");
        String description = userDescription(user_input);

        try {
            String day = userDay(user_input);
            String month = userMonth(user_input);
            String year = userYear(user_input);
            validateDate(day, month, year);
            String importanceLevel = getImportanceLevel(user_input);

            createGeneralTask(taskType, description, day, month, year, importanceLevel);
        } catch (BadDateInputException bdiex) {
            System.out.println(bdiex.getMessage());
        } catch (InvalidImportanceException iiex) {
            System.out.println(iiex.getMessage());
        } finally {
            System.out.println("Adding Process finished.");
        }
    }

    // REQUIRES: taskType == "EVENT"
    // MODIFIES: this
    // EFFECTS: gets input from the user used to create a GeneralTask
    public void addEvent(Scanner user_input, String taskType) {
        System.out.println("Event Tasks require a few extra details:\n-> A start date\n-> An end date\n-> Optional Comments");
        String description = userDescription(user_input);

        try {

            System.out.println("START DAY");
            String startDay = userDay(user_input);
            String startMonth = userMonth(user_input);
            String startYear = userYear(user_input);
            validateDate(startDay, startMonth, startYear);

            System.out.println("END DAY:");
            String endDay = userDay(user_input);
            String endMonth = userMonth(user_input);
            String endYear = userYear(user_input);
            validateDate(endDay, endMonth, endYear);

            String importanceLevel = getImportanceLevel(user_input);
            System.out.println("If you have no comments, type NONE.");
            String comments = getComments(user_input);

            createEventTask(taskType, description, startDay, startMonth, startYear, endDay, endMonth, endYear, importanceLevel, comments);
        } catch (BadDateInputException bdiex) {
            System.out.println(bdiex.getMessage());
        } catch (InvalidImportanceException iiex) {
            System.out.println(iiex.getMessage());
        } finally {
            System.out.println("Adding process finished.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Allows user to add a Task and then stores the created Task. Task is not added if
    //          user input is invalid.
    public void addItem(Scanner user_input)
    {
        itemDetails();
        String taskType = chooseTaskType(user_input);

        if (taskType.equals("TASK")) {
            addGeneralTask(user_input, taskType);
        } else if (taskType.equals("EVENT")) {
            addEvent(user_input, taskType);
        } else {
            System.out.println("Something went wrong, your item was not created.");
        }
    }

    // EFFECTS: gets all stored tasks.
    public void printItems() {

        System.out.println("**TASKS**\n");

        if (eventTaskList.size() > 0) {
            for (EventTask task : eventTaskList) {
                task.printItem();
                System.out.println();
            }
        }
        if (generalTaskList.size() > 0) {
            for (GeneralTask task : generalTaskList) {
                task.printItem();
                System.out.println();
            }
        }

        if (generalTaskList.size() == 0 && eventTaskList.size() == 0) {
            System.out.println("You have no tasks.");
        }
    }

    // EFFECTS: saves newTask to a file.
    public void saveGeneralTask(GeneralTask newGTask) {

        String filename = "listoftasks.csv";
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

    // EFFECTS: saves newTask to a file.
    public void saveEventTask(EventTask newETask) {

        String filename = "listoftasks.csv";
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

    // loads a single item.
    public void loadSingleItem(String currentItem) {
        ArrayList<String> tempTaskDetails;
        tempTaskDetails = new ArrayList<>(Arrays.asList(currentItem.split(",")));

        String taskType = tempTaskDetails.get(0);

        if (taskType.equals("TASK")) {
            loadGeneralTask(tempTaskDetails);
        } else {
            loadEventTask(tempTaskDetails);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a GeneralTask into the list
    public void loadGeneralTask(ArrayList<String> currentGeneralTask) {
        String taskType = currentGeneralTask.get(0);
        String description = currentGeneralTask.get(1);
        String day = currentGeneralTask.get(2);
        String month = currentGeneralTask.get(3);
        String year = currentGeneralTask.get(4);
        String importance = currentGeneralTask.get(5);

        GeneralTask tempGTask = new GeneralTask(taskType, description, Integer.parseInt(day), Integer.parseInt(month),
                Integer.parseInt(year), importance);
        generalTaskList.add(tempGTask);
    }


    // MODIFIES: this
    // EFFECTS: loads an EventTask into the list
    public void loadEventTask(ArrayList<String> currentEventTask) {
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
    // EFFECTS: loads all tasks stored in a file into the generalTaskList.
    public int loadItems() {
        String filename = "listoftasks.csv";
        String currentTask;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((currentTask = bufferedReader.readLine()) != null) {
                loadSingleItem(currentTask);
            }
        }
        catch (FileNotFoundException fnfex) {
            return 1;
        } catch (IOException ioex) {
            return 2;
        }

        return 0;
    }

    // EFFECTS: gets the generalTaskList
    public ArrayList<GeneralTask> getGeneralTaskList() {
        return generalTaskList;
    }

    // EFFECTS: gets the eventTaskList
    public ArrayList<EventTask> getEventTaskList() {
        return eventTaskList;
    }
}
