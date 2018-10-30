package model;

import exceptions.choices.BadNavInputException;
import ui.*;

import java.util.Scanner;

public class SchoolPlanner {

    private String userName;
    private MainScreen screenMain;
    private TimetableScreen screenTimetable;
    private TasksScreen screenTasks;
    private SettingsScreen screenSettings;

    // MODIFIES: this
    // EFFECTS: Creates a new SchoolPlanner object. Sets its fields and creates the screens of the school
    //          planner. Outputs a welcome message.
    public SchoolPlanner(String name) {
        userName = name;
        screenMain = new MainScreen();
        screenTimetable = new TimetableScreen();
        screenTasks = new TasksScreen();
        screenSettings = new SettingsScreen();
        System.out.println("Welcome to your School Planner, " + userName);
    }

    // EFFECTS: gets input from user about returning to the menu, and returns a boolean.
    public boolean backToMenu(Scanner user_input) {
        String backToMenu;
        do {
            System.out.println("Do you want to go back to the main menu? (Y/N)");
            backToMenu = user_input.nextLine();
            if (backToMenu.toUpperCase().equals("Y")) { }
            else if (backToMenu.toUpperCase().equals("N")) {
                return true;
            } else {
                System.out.println("You didn't enter a valid option.");
            }
        }
        while (!(backToMenu.toUpperCase().equals("Y") || backToMenu.toUpperCase().equals("N")));
        return false;
    }

    // EFFECTS: prints information for navigationInput
    public void navInputDetails() {
        System.out.println("\nWhere would you like to go?");
        System.out.println("1: Tasks\n2: Timetable\n3: Settings\n4: Quit");
        System.out.print("Enter your choice here: ");
    }

    // EFFECTS: gets input from the user about which screen they want to visit. If incorrect choice is provided,
    //          asks user to input a valid choice. Returns a number corresponding to their choice.
    public int navigationInput(Scanner user_input) {
        while (true) {
            navInputDetails();
            String inputChoice = user_input.next();
            // input must be a single integer: 1, 2, 3 or 4
            while (!(inputChoice.matches("^[0-9]*$") && inputChoice.length() == 1)) {
                System.out.println("Your input is invalid, please enter a valid option.");
                navInputDetails();
                inputChoice = user_input.next();
            }
            user_input.nextLine();
            try {
                int choice = verifyNavInput(inputChoice);
                return choice;
            } catch (BadNavInputException icex) {
                System.out.println("Invalid choice entered, please enter a valid option.");
            }
        }
    }

    // EFFECTS: checks user input from navigationInput and throws BadNavInputException if input is not valid. (HELPER)
    public int verifyNavInput(String inputChoice) throws BadNavInputException {

        if (inputChoice.equals("1")) {
            return 1;
        } else if (inputChoice.equals("2")) {
            return 2;
        } else if (inputChoice.equals("3")) {
            return 3;
        } else if (inputChoice.equals("4")) {
            return 4;
        } else {
            throw new BadNavInputException();
        }
    }

    // EFFECTS: handles choices for adding or viewing tasks (HELPER).
    public void handleAddOrViewTask(Scanner user_input) {
        boolean addingTasks = true;
        int addOrViewTask = screenTasks.handleOptions(user_input);
        if (addOrViewTask == 1) {
            screenTasks.printStoredItems();
        }
        else if (addOrViewTask == 2) {
            while (addingTasks) {
                screenTasks.addToListObject(user_input);
                screenTasks.printStoredItems();
                addingTasks = backToMenu(user_input);
            }
        }
        else if (addOrViewTask == 3) {
            while (addingTasks) {
                screenTasks.removeItem(user_input);
                screenTasks.printStoredItems();
                addingTasks = backToMenu(user_input);
            }
        }

    }

    // EFFECTS: handles choices for adding or viewing classes (HELPER).
    public void handleAddOrViewClasses(Scanner user_input) {
        boolean managingClasses = true;
        int addOrViewClasses = screenTimetable.handleOptions(user_input);
        if (addOrViewClasses == 1) {
            screenTimetable.printStoredItems();
        }
        else if (addOrViewClasses == 2) {
            while (managingClasses) {
                screenTimetable.addToListObject(user_input);
                screenTimetable.printStoredItems();
                managingClasses = backToMenu(user_input);
            }
        }
        else if (addOrViewClasses == 3) {
            while(managingClasses) {
                screenTimetable.removeItem(user_input);
                screenTimetable.printStoredItems();
                managingClasses = backToMenu(user_input);
            }
        }
        else if (addOrViewClasses == 4) {
            while (managingClasses) {
                screenTimetable.removeTextbook(user_input);
                screenTimetable.printStoredItems();
                managingClasses = backToMenu(user_input);
            }
        }
        else if (addOrViewClasses == 5) {
            while (managingClasses) {
                screenTimetable.addTextbook(user_input);
                screenTimetable.printStoredItems();
                managingClasses = backToMenu(user_input);
            }
        }
        else if (addOrViewClasses == 6) {
                screenTimetable.printTextbooks();
        }
    }

    // MODIFIES: this
    // EFFECTS: runs the program once a new SchoolPlanner is created.
    private void run() {
        Scanner user_input = new Scanner(System.in);
        screenMain.myClasses(screenTimetable);
        screenMain.myTasks(screenTasks);
        screenMain.myTextbooks(screenTimetable);

        while (true) {
            int choice = navigationInput(user_input);
                if (choice == 1) {
                    handleAddOrViewTask(user_input);
                } else if (choice == 2) {
                    handleAddOrViewClasses(user_input);
                } else if (choice == 3) {
                    screenSettings.welcomeMessage();
                } else if (choice == 4) {
                    System.out.println("Goodbye " + userName + ", come back soon!");
                    screenTimetable.saveList();
                    screenTasks.saveList();
                    System.exit(0);
                } else {
                    System.out.println("You did not enter a valid option.");
                }
        }

    }

    public static void main(String[] args) {
        SchoolPlanner sp = new SchoolPlanner("Naveen");
        sp.run();
    }

}
