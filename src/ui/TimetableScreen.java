package ui;

public class TimetableScreen {

    private String userName = "Naveen";

    private TimetableScreen() { System.out.println("Timetable\n"); }

    public void getUserName() { System.out.println("Welcome back " +  userName + "!"); }

    public void myClasses() { System.out.println("You have no upcoming classes today.");}

    public void myTasks() { System.out.println("You have no tasks due."); }

    public static void main(String args[]) {

        TimetableScreen timetable = new TimetableScreen();
        timetable.getUserName();
        timetable.myClasses();
        timetable.myTasks();
    }

}


