package ui;

import main.SchoolPlanner;

import javax.swing.*;

public class MainScreen extends JPanel {


    public MainScreen(String name)
    {

    }

    public void myClasses() {
        System.out.println("You have no upcoming classes today.");
    }

    public void myTasks() {
        System.out.println("You have no tasks due.");
    }


        /*


        Scanner user_input = new Scanner(System.in);

        System.out.println("What would you like to do?");
        System.out.println("1: Tasks   2: Timetable   3: Settings ");
        String makeNewTask = user_input.next();


        while (!(makeNewTask.matches("^[0-9]*$") || makeNewTask.length() != 1))
        {
            System.out.println("Your input is invalid, please enter a valid option.");
            makeNewTask = user_input.next();
        }



        if (Integer.parseInt(makeNewTask) == 1)
        {
            screenMain.frame.setVisible(false);
            screenTasks.frame.setVisible(true);
        }
        else if (Integer.parseInt(makeNewTask) == 2)
        {
            TimetableScreen timetable = new TimetableScreen();
        }
        else if (Integer.parseInt(makeNewTask) == 3)
        {
            SettingsScreen settings = new SettingsScreen();
        }
        else
        {
            System.out.println("You did not enter a valid option.");
        }

        */

}



