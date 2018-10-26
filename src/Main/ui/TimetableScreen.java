package ui;

import exceptions.choices.InvalidChoiceException;
import exceptions.input.BadClassTypeException;
import exceptions.input.BadTimeException;
import inputs.UniClass;
import model.InputScreen;
import model.Loadable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TimetableScreen implements InputScreen, Loadable {

    private ArrayList<UniClass> classList = new ArrayList<>();

    // EFFECTS: creates a new TimetableScreen object.
    public TimetableScreen() {

    }

    // EFFECTS: gets input from the user to decide whether they want to add classes or view classes.
    public int addOrView(Scanner user_input) {
        int addOrView;
        while (true) {
            try {
                System.out.println("What do you want to do?");
                System.out.println("1. View Classes\n2. Add Classes");
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

    public String userClassName(Scanner user_input) {
        System.out.print("Class Name: ");
        String name = user_input.nextLine();
        return name;
    }

    public String userClassType(Scanner user_input) throws BadClassTypeException {
        System.out.print("Class Type: ");
        String classType = user_input.nextLine().toUpperCase();

        switch (classType) {
            case "LECTURE":
                return "LECTURE";
            case "LAB":
                return "LAB";
            case "DISCUSSION":
                return "DISCUSSION";
            case "TUTORIAL":
                return "TUTORIAL";
            default:
                throw new BadClassTypeException("You didn't enter a valid class type. Your task was not created.");
        }
    }

    public String userProf(Scanner user_input) {
        System.out.print("Professor: ");
        String prof = user_input.nextLine();
        return prof;
    }

    public String userLocation(Scanner user_input) {
        System.out.print("Location: ");
        String location = user_input.nextLine();
        return location;
    }

    public String userStartTime(Scanner user_input) throws BadTimeException {
        System.out.print("Start Time: ");
        String startTime = user_input.nextLine();
        // midnight is 0000
        if (Integer.parseInt(startTime) < 0 || Integer.parseInt(startTime) > 2359) {
            throw new BadTimeException("Invalid start time. Your class was not created.");
        } else {
            return startTime;
        }
    }

    public String userEndTime(Scanner user_input) throws BadTimeException {
        System.out.print("End Time: ");
        String endTime = user_input.nextLine();
        // midnight is 0000
        if (Integer.parseInt(endTime) < 0 || Integer.parseInt(endTime) > 2359) {
            throw new BadTimeException("Invalid end time. Your class was not created.");
        } else {
            return endTime;
        }
    }

    public ArrayList<Integer> userDays (Scanner user_input) {
        ArrayList<Integer> days = new ArrayList<>();
        System.out.println("Days (type 0 when done):");
        while (true) {
            int day;
            try {
                day = user_input.nextInt();
                if (day == 0) {
                    break;
                } else if (day < 0 || day > 7) {
                    System.out.println("Enter a valid day (between 1 and 7 or 0 if you're finished.)");
                } else {
                    days.add(day);
                }
            } catch (InputMismatchException n) {
                System.out.println("Enter a valid day.");
                user_input.nextLine();
            }
        }
        return days;
    }

    public void createClass(String name, String classType, String prof, String location, String startTime, String endTime, ArrayList<Integer> days) {
        UniClass newClass = new UniClass(name, classType, prof, location, Integer.parseInt(startTime), Integer.parseInt(endTime), days);
        classList.add(newClass);
        saveUniClass(newClass);
        System.out.println("A new class has been created.");
    }

    public void itemDetails() {
        System.out.println("All classes must have the following fields:");
        System.out.println("-> A class Type (LECTURE/LAB/DISCUSSION/TUTORIAL)\n-> A name\n" +
                "-> A professor\n-> A location\n-> A start time\n-> An end time.\n-> Days of the week");
        System.out.println("N.B. Start and End Time must be in 24hr format e.g. 1400 = 2 pm.");
        System.out.println("N.B. Enter the number that corresponds to the day e.g. Monday = 1, Thursday = 4 etc.");
    }

    // MODIFIES: this
    // EFFECTS: allows user to create a new UniClass and then saves the created UniClass.
    public void addItem(Scanner user_input) {
        itemDetails();

        try {
            String classType = userClassType(user_input);
            String name = userClassName(user_input);
            String prof = userProf(user_input);
            String location = userLocation(user_input);
            String startTime = userStartTime(user_input);
            String endTime = userEndTime(user_input);
            ArrayList<Integer> days = userDays(user_input);
            if (days.size() == 0) {
                System.out.println("No days entered. Your class was not created.");
                return;
            }

            createClass(classType, name, prof, location, startTime, endTime, days);

        } catch (BadClassTypeException bctex) {
            System.out.println(bctex.getMessage());
        } catch (BadTimeException btex) {
            System.out.println(btex.getMessage());
        }
    }

    // EFFECTS: outputs stored UniClasses
    public void printItems() {
        if (classList.size() > 0) {
            System.out.println("**CLASSES**\n");
            for (UniClass uniClass : classList) {
                uniClass.printItem();
                System.out.println();
            }
        } else {
            System.out.println("You have no classes.");
        }
    }

    // EFFECTS: saves uc to a file.
    public void saveUniClass(UniClass uc) {
        String filename = "listofclasses.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(uc.getClassType() + "," + uc.getName() + "," + uc.getProf() + "," + uc.getLocation() + ",");
            bufferedWriter.write(uc.getStartTime() + "," + uc.getEndTime());
            ArrayList<Integer> days = uc.getDays();
            for (int day : days) {
                bufferedWriter.write("," + day);
            }
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSingleItem(String currentItem) {
        ArrayList<String> tempClassDetails;

        tempClassDetails = new ArrayList<>(Arrays.asList(currentItem.split(",")));
        String classType = tempClassDetails.get(0);
        String className = tempClassDetails.get(1);
        String prof = tempClassDetails.get(2);
        String room = tempClassDetails.get(3);
        String start = tempClassDetails.get(4);
        String end = tempClassDetails.get(5);

        ArrayList<Integer> days = new ArrayList<>();
        for (int i = 6; i < tempClassDetails.size(); i++) {
            String day = tempClassDetails.get(i);
            days.add(Integer.parseInt(day));
        }

        UniClass tempUniClass = new UniClass(classType, className, prof, room, Integer.parseInt(start), Integer.parseInt(end), days);
        classList.add(tempUniClass);
    }

    // MODIFIES: this
    // EFFECTS: loads all classes from a file into the classList.
    public int loadItems() {
        String filename = "listofclasses.csv";
        String currentClass;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((currentClass = bufferedReader.readLine()) != null) {
                loadSingleItem(currentClass);
            }
        } catch (FileNotFoundException fnfex) {
            return 1;
        } catch (IOException ioex) {
            return 2;
        }
        return 0;
    }

    // EFFECTS: gets the classList
    public ArrayList<UniClass> getClassList() {
        return classList;
    }

}
