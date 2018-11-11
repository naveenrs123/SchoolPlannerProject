package model.observers;

import model.collections.CollectionOfEventTasks;

public class EventTasksObserver implements Observer {

    int numTasks = 0;
    CollectionOfEventTasks collectionOfEventTasks;

    public EventTasksObserver(CollectionOfEventTasks coel) {
        collectionOfEventTasks = coel;
    }

    @Override
    public void update() {
        numTasks = collectionOfEventTasks.getTaskList().size();
        if (numTasks == 1) {
            System.out.println("There is " + numTasks + " event stored.");
        } else {
            System.out.println("There are " + numTasks + " events stored.");
        }

    }
}
