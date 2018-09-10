package ui;

public class MainScreen {

    private String userName = "Naveen";

    private MainScreen() {
        System.out.println("Timetable\n");
    }

    public void getUserName() {
        System.out.println("Welcome back " + userName + "!");
    }

    public void myClasses() {
        System.out.println("You have no upcoming classes today.");
    }

    public void myTasks() {
        System.out.println("You have no tasks due.");
    }

    public static void main(String args[]) {

        MainScreen screenMain = new MainScreen();
        screenMain.getUserName();
        screenMain.myClasses();
        screenMain.myTasks();

    }

}


