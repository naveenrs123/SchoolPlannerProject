package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.MainScreen;
import ui.TasksScreen;
import ui.TimetableScreen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class MainScreenTest {

    private TimetableScreen screenTimetable;
    private TasksScreen screenTasks;
    private MainScreen screenMain;

    @BeforeEach
    void setup() {
        screenMain = new MainScreen();
    }

    @Test
    void testMyClassesNoFile() {
        screenTimetable = new TimetableScreen();
        screenMain.myClasses(screenTimetable);
    }

    @Test
    void testMyTasksNoFile() {
        screenTasks = new TasksScreen();
        screenMain.myTasks(screenTasks);
    }

    @Test
    void testMyClassesFile() {
        screenTimetable = new TimetableScreen();
        String filename = "listofclasses.csv";

        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("CPSC 210,Elisa Baniassad,SWNG 122,1200,1300,1,3,5");
            bufferedWriter.newLine();
            bufferedWriter.write("MATH 221,Gyo-Taek Jin,LSK 201,1000,1000,1,3,5");
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        screenMain.myClasses(screenTimetable);
        File file = new File("/Users/naveensivasankar/Desktop/ /UBC/Year 2/CPSC 210/Personal Project/projectw1_team413/listofclasses.csv");
        if (file.delete()) {
            System.out.println("File deleted successfully.");
        }
        else {
            System.out.println("Error deleting file.");
        }

    }

    @Test
    void testMyTasksFile() {

        screenTasks = new TasksScreen();
        String filename = "listoftasks.csv";

        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("TASK,A Task,10,10,18,LOW");
            bufferedWriter.newLine();
            bufferedWriter.write("EVENT,An Event,11,12,19,12,12,19,MEDIUM,show up 30 minutes before start of event");
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        screenMain.myTasks(screenTasks);
        File file = new File("/Users/naveensivasankar/Desktop/ /UBC/Year 2/CPSC 210/Personal Project/projectw1_team413/listoftasks.csv");
        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Error deleting file.");
        }
    }
}
