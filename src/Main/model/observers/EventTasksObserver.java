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
        // no subject.getState() function implemented because existing getters can get the state for me.
        numTasks = collectionOfEventTasks.getTaskList().size();
        if (numTasks == 1) {
            System.out.println("There is " + numTasks + " event stored.");
        } else {
            System.out.println("There are " + numTasks + " events stored.");
        }

    }
}
