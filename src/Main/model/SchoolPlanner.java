package model;

import model.observerPattern.Observer;
import ui.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SchoolPlanner implements Observer {

    private String userName;
    private MainScreen screenMain;
    private TimetableScreen screenTimetable;
    private TasksScreen screenTasks;
    private JFrame frame = new JFrame("School Planner");


    // MODIFIES: this
    // EFFECTS: Creates a new SchoolPlanner object. Sets its fields and creates the screens of the school
    //          planner. Outputs a welcome message.
    public SchoolPlanner(String name) {
        userName = name;
        screenMain = new MainScreen();
        screenTimetable = new TimetableScreen();
        screenTasks = new TasksScreen();
        screenTimetable.getListOfUniClasses().addObserver(this);
        screenTasks.getLoet().addObserver(this);
        screenTasks.getLogt().addObserver(this);

    }

    /*
    public static void getDataFromWeb() throws MalformedURLException, IOException {
        BufferedReader br = null;
        try {
            String theURL = "https://www.ugrad.cs.ubc.ca/~cs210/2018w1/welcomemsg.html"; //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            System.out.println(sb);
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
    */

    @Override
    public void update() {
        screenMain.myClasses(screenTimetable);
        screenMain.myTasks(screenTasks);
        screenMain.myTextbooks(screenTimetable);
    }

    // MODIFIES: this
    // EFFECTS: runs the program once a new SchoolPlanner is created.
    private void run() {
        JTabbedPane tabs = new JTabbedPane();

        frame.setResizable(false);

        frame.setSize(620, 600);
        screenTimetable.setSize(620, 600);
        screenMain.setSize(620, 600);
        screenTasks.setSize(620, 600);

        tabs.addTab("Home", screenMain);
        tabs.addTab("Timetable", screenTimetable);
        tabs.addTab("Tasks", screenTasks);
        frame.add(tabs);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                screenTimetable.saveList();
                screenTasks.saveList();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        screenMain.myClasses(screenTimetable);
        screenMain.myTasks(screenTasks);
        screenMain.myTextbooks(screenTimetable);
    }

    public static void main(String[] args) {
        SchoolPlanner sp = new SchoolPlanner("Naveen");
        sp.run();
    }


}
