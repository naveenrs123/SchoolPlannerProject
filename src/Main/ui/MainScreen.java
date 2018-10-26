package ui;

public class MainScreen {


    // EFFECTS: creates a new MainScreen object.
    public MainScreen()
    {

    }

    // EFFECTS: outputs details about user's classes.
    public void myClasses(TimetableScreen screenTimetable) {

        int isClassesLoaded = screenTimetable.loadItems();

        if (isClassesLoaded == 0) {
            System.out.println();
            screenTimetable.printItems();
        } else if (isClassesLoaded == 1) {
            System.out.println("You have no classes.");
        } else if (isClassesLoaded == 2) {
            System.out.println("Unable to load classes. Oops!");
        }
    }

    // EFFECTS: outputs details about user's tasks.
    public void myTasks(TasksScreen screenTasks) {

        int isTasksLoaded = screenTasks.loadItems();

        if (isTasksLoaded == 0) {
            System.out.println();
            screenTasks.printItems();
        } else if (isTasksLoaded == 1) {
            System.out.println("You have no tasks.");
        } else if (isTasksLoaded == 2) {
            System.out.println("Unable to load tasks. Oops!");
        }
    }
}



