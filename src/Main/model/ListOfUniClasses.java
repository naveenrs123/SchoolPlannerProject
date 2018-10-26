package model;

import exceptions.input.BadClassTypeException;
import exceptions.input.BadTimeException;
import inputs.UniClass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ListOfUniClasses {

    private ArrayList<UniClass> classList = new ArrayList<>();

    public  ListOfUniClasses() {

    }

    // USER INPUT FUNCTIONS FOR DETAILS OF A UNICLASS (BEGIN)

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

    // USER INPUT FUNCTIONS FOR DETAILS OF A UNICLASS (END)
}
