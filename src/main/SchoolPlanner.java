package main;

import ui.*;

import javax.swing.*;

public class SchoolPlanner {

    public JFrame frame = new JFrame("School Planner");
    public MainScreen screenMain = new MainScreen("Naveen");
    public TimetableScreen screenTimetable = new TimetableScreen();
    public TasksScreen screenTasks = new TasksScreen();
    public SettingsScreen screenSettings = new SettingsScreen();
    public AddTaskScreen screenAddTask = new AddTaskScreen();
    public AddClassScreen screenAddClass = new AddClassScreen();
    static public String userName;



    public static void main(String[] args) {

        SchoolPlanner sp = new SchoolPlanner();

        JTabbedPane tabs = new JTabbedPane();

        sp.frame.setSize(800, 500);

        sp.screenTimetable.setSize(800, 500);
        sp.screenMain.setSize(800, 500);
        sp.screenTasks.setSize(800, 500);
        sp.screenSettings.setSize(800, 500);

        tabs.addTab("Home", sp.screenMain);
        tabs.addTab("Timetable", sp.screenTimetable);
        tabs.addTab("Tasks", sp.screenTasks);
        tabs.addTab("Settings", sp.screenSettings);

        sp.frame.add(tabs);

        sp.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sp.frame.setVisible(true);

    }
}
