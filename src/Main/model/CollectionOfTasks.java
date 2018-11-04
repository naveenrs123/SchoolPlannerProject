package model;

import exceptions.date.*;
import exceptions.input.BadClassTypeException;
import exceptions.input.BadTimeException;
import exceptions.input.InvalidImportanceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class CollectionOfTasks implements CollectionOfItems {

    public CollectionOfTasks() {

    }

    // USER INPUT METHODS FOR DETAILS OF A TASK (BEGIN)

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
            throw new BadDayException("You entered an invalid day.");
        } else {
            return day;
        }
    }

    // EFFECTS: gets and checks month provided by the user.
    public String userMonth(Scanner user_input) throws BadMonthException {
        System.out.print("Enter the month: ");
        String month = user_input.nextLine();
        if (!(month.matches("^[0-9]*$")) || Integer.parseInt(month) > 12 || Integer.parseInt(month) <= 0) {
            throw new BadMonthException("You entered an invalid month.");
        } else {
            return month;
        }
    }

    // EFFECTS: gets and checks year provided by the user.
    public String userYear(Scanner user_input) throws BadYearException {
        System.out.print("Enter the year: ");
        String year = user_input.nextLine();
        if (!(year.matches("^[0-9]*$")) || Integer.parseInt(year) > 99 || Integer.parseInt(year) < 18) {
            throw new BadYearException("You entered an invalid year.");
        } else {
            return year;
        }
    }

    // EFFECTS: gets name of class from the user and returns it.
    public String userClassName(Scanner user_input) {
        System.out.print("Class Name: ");
        String name = user_input.nextLine();
        return name;
    }

    // EFFECTS: checks date provided by the user.
    public boolean validateDate(String day, String month, String year) throws InvalidDateException {
        int intDay = Integer.parseInt(day);
        int intMonth = Integer.parseInt(month);
        int intYear = Integer.parseInt(year);
        ArrayList<Integer> months30 = new ArrayList<>(Arrays.asList(4, 6, 9, 11));

        if ((months30.contains(intMonth) && intDay > 30) || (intMonth == 2 && intDay > 28 && intYear % 4 != 0)
                || (intMonth == 2 && intDay > 29 && intYear % 4 == 0)) {
            throw new InvalidDateException("Your date does not make sense.");
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
            throw new InvalidImportanceException("You entered an invalid importance level.");
        } else {
            return importanceLevel;
        }
    }

    // EFFECTS: gets class type from the user and throws an exception if the type is invalid.
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

    // EFFECTS: gets class type from the user and returns it. Keeps asking for input until valid class type is entered.
    public String userClassTypeForSearching(Scanner user_input) {
        System.out.print("Class Type: ");
        String classType;

        do {
            classType = user_input.nextLine().toUpperCase();
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
                    System.out.println("You didn't enter a valid class type.");
            }
        } while (true);
    }

    // EFFECTS: gets professor from the user and returns it.
    public String userProf(Scanner user_input) {
        System.out.print("Professor: ");
        String prof = user_input.nextLine();
        return prof;
    }

    // EFFECTS: gets location from the user and returns it.
    public String userLocation(Scanner user_input) {
        System.out.print("Location: ");
        String location = user_input.nextLine();
        return location;
    }

    // EFFECTS: gets startTime from the user and throws an exception if the time is invalid, returns the startTime.
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

    // EFFECTS: gets endTime from the user and throws an exception if the time is invalid, returns the endTime.
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

    // EFFECTS: gets the days from the user and returns it as an ArrayList.
    public ArrayList<Integer> userDays(Scanner user_input) {
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
}
