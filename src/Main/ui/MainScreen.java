package ui;

import model.CollectionOfTextbooks;

public class MainScreen {


    // EFFECTS: creates a new MainScreen object.
    public MainScreen()
    {

    }

    // EFFECTS: outputs details about user's classes.
    public void myClasses(TimetableScreen screenTimetable) {

        int isClassesLoaded = screenTimetable.loadItemsIntoListObject();

        if (isClassesLoaded == 0) {
            System.out.println();
            screenTimetable.printStoredItems();
        } else if (isClassesLoaded == 1) {
            System.out.println("You have no classes.");
        } else if (isClassesLoaded == 2) {
            System.out.println("Unable to load classes. Oops!");
        }
    }

    // EFFECTS: outputs details about user's tasks.
    public void myTasks(TasksScreen screenTasks) {

        int isTasksLoaded = screenTasks.loadItemsIntoListObject();

        if (isTasksLoaded == 0) {
            screenTasks.printStoredItems();
        } else if (isTasksLoaded == 1) {
            System.out.println("You have no tasks.");
        } else if (isTasksLoaded == 2) {
            System.out.println("Unable to load tasks. Oops!");
        }
    }

    // EFFECTS: outputs details about user's tasks.
    public void myTextbooks(TimetableScreen screenTimetable) {
        CollectionOfTextbooks textbooks = screenTimetable.getListOfUniClasses().getCollectionOfTextbooks();
        textbooks.printTextbooks();
    }
}



