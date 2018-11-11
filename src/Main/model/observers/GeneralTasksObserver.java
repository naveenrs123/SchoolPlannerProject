package model.observers;

import model.collections.CollectionOfGeneralTasks;

public class GeneralTasksObserver implements Observer {

    int numTasks = 0;
    CollectionOfGeneralTasks collectionOfGeneralTasks;

    public GeneralTasksObserver(CollectionOfGeneralTasks cogl) {
        collectionOfGeneralTasks = cogl;
    }

    @Override
    public void update() {
        numTasks = collectionOfGeneralTasks.getTaskList().size();
        if (numTasks == 1) {
            System.out.println("There is " + numTasks + " task stored.");
        } else {
            System.out.println("There are " + numTasks + " tasks stored.");
        }
    }
}
