package main;


import ui.*;

import java.util.Scanner;

public class SchoolPlanner {

    private String userName;
    private MainScreen screenMain;
    private TimetableScreen screenTimetable;
    private TasksScreen screenTasks;
    private SettingsScreen screenSettings;

    public SchoolPlanner(String name) {
        userName = name;
        screenMain = new MainScreen();
        screenTimetable = new TimetableScreen();
        screenTasks = new TasksScreen();
        screenSettings = new SettingsScreen();
        System.out.println("Welcome to your School Planner, " + userName);
    }

    public int navigationInput() {

        Scanner user_input = new Scanner(System.in);

        while (true)
        {
            System.out.println("\nWhere would you like to go?");
            System.out.println("1: Tasks\n2: Timetable\n3: Settings");
            System.out.print("Enter your choice here: ");

            String inputChoice = user_input.next();

            while (!(inputChoice.matches("^[0-9]*$") || inputChoice.length() != 1))
            {
                System.out.println("Your input is invalid, please enter a valid option.");
                inputChoice = user_input.next();
            }

            if (inputChoice.equals("1"))
            {
                screenTasks.welcomeMessage();
                return 1;
            }
            else if (inputChoice.equals("2"))
            {
                screenTimetable.welcomeMessage();
                return 2;
            }
            else if (inputChoice.equals("3"))
            {
                screenSettings.welcomeMessage();
                return 3;
            }
            else
            {
                System.out.println("You did not enter a valid option.");
            }

        }

    }

    public static void main(String[] args) {

        SchoolPlanner sp = new SchoolPlanner("Naveen");
        sp.screenMain.myClasses();
        sp.screenMain.myTasks();

        int choice = sp.navigationInput();

        if (choice == 1)
        {
            sp.screenTasks.addTask();
            sp.screenTasks.getTasks();
        }
        if (choice == 2)
        {
            sp.screenTimetable.addUniClass();
            sp.screenTimetable.getUniClasses();
        }

    }
}
