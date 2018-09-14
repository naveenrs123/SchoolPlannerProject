package ui;

import inputs.UniClass;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TimetableScreen {

    ArrayList<UniClass> classList = new ArrayList<>();

    public TimetableScreen() {

    }

    public void welcomeMessage() {
        System.out.println("You have not added any classes.");
        System.out.println("Let's add one!");
    }

    public void addUniClass() {
        Scanner user_input = new Scanner(System.in);

        System.out.println("All classes must have the following fields:");
        System.out.println("-> A name\n-> A professor\n-> A location\n-> A start time\n-> An end time.\n-> Days of the week");
        System.out.println("N.B. Start and End Time must be in 24hr format e.g. 1400 = 2 pm.");
        System.out.println("N.B. Enter the number that corresponds to the day e.g. Monday = 1, Thursday = 4 etc.");

        System.out.print("Class Name: ");
        String name = user_input.nextLine();

        System.out.print("Professor: ");
        String prof = user_input.nextLine();

        System.out.print("Location: ");
        String location = user_input.nextLine();

        System.out.print("Start Time: ");
        String startTime = user_input.nextLine();

        System.out.print("End Time: ");
        String endTime = user_input.nextLine();

        int[] days = new int[5];

        System.out.println("Days (type 0 when done):");

        for (int i = 0; i < days.length; i++)
        {
            int day = user_input.nextInt();
            if (day == 0)
            {
                break;
            }
            else
            {
                days[i] = day;
            }

        }

        UniClass newClass = new UniClass(name, prof, location, Integer.parseInt(startTime), Integer.parseInt(endTime), days);
        classList.add(newClass);
    }

    public void getUniClasses() {
        for (UniClass uniClass : classList)
        {
            uniClass.printUniClass();
        }
    }

}
