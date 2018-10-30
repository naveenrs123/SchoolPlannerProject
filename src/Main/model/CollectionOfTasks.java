package model;

import exceptions.date.*;
import exceptions.input.InvalidImportanceException;

import java.util.ArrayList;
import java.util.Arrays;
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

    // ABSTRACT METHODS

    public abstract void addTask(Scanner user_input, String taskType);

}
