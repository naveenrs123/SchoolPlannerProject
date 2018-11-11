package model.inputHandling;

import exceptions.input.BadClassTypeException;
import exceptions.input.BadTimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UniClassInputHandler extends CollectionInputHandler {

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

    // EFFECTS: gets name of class from the user and returns it.
    public String userClassName(Scanner user_input) {
        return getString(user_input, "Class Name: ");
    }

    // EFFECTS: gets professor from the user and returns it.
    public String userProf(Scanner user_input) {
        return getString(user_input, "Professor: ");
    }

    // EFFECTS: gets location from the user and returns it.
    public String userLocation(Scanner user_input) {
        return getString(user_input, "Location: ");
    }

    // EFFECTS: gets key from the user.
    public ArrayList<String> userKey(Scanner user_input) {
        String classType = userClassTypeForSearching(user_input);
        String name = userClassName(user_input);
        return new ArrayList<>(Arrays.asList(classType, name));
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

    // EFFECTS: asks the user if they want to add a textbook, asking for input until a valid option is entered.
    public boolean wantToAddTextbook(Scanner user_input) {
        String choice;
        do {
            System.out.println("Do you want to add a textbook to this class? (Y/N):");
            choice = user_input.nextLine().toUpperCase();
            if (choice.equals("Y")) {
                return true;
            } else if (choice.equals("N")) {
                return false;
            } else {
                System.out.println("Enter Y or N.");
            }
        } while (true);
    }


    // TIME AND DAY (BEGIN)

    // EFFECTS: gets startTime from the user and throws an exception if the time is invalid, returns the startTime.
    public String userStartTime(Scanner user_input) throws BadTimeException {
        System.out.print("Start Time: ");
        String startTime = user_input.nextLine();
        // midnight is 0000
        try {
            if (Integer.parseInt(startTime) < 0 || Integer.parseInt(startTime) > 2359) {
                throw new BadTimeException("Invalid start time. Your class was not created.");
            } else {
                return startTime;
            }
        } catch (NumberFormatException nfex) {
            throw new BadTimeException("Invalid start time. Your class was not created.");
        }
    }

    // EFFECTS: gets endTime from the user and throws an exception if the time is invalid, returns the endTime.
    public String userEndTime(Scanner user_input) throws BadTimeException {
        System.out.print("End Time: ");
        String endTime = user_input.nextLine();
        // midnight is 0000
        try {
            if (Integer.parseInt(endTime) < 0 || Integer.parseInt(endTime) > 2359) {
                throw new BadTimeException("Invalid end time. Your class was not created.");
            } else {
                return endTime;
            }
        } catch (NumberFormatException nfex) {
            throw new BadTimeException("Invalid end time. Your class was not created.");
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

    // TIME AND DAY (END)
}
