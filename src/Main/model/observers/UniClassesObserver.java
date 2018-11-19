package model.observers;

import model.collections.CollectionOfUniClasses;

public class UniClassesObserver implements Observer {

    int numClasses = 0;
    CollectionOfUniClasses collectionOfUniClasses;

    public UniClassesObserver(CollectionOfUniClasses couc) {
        collectionOfUniClasses = couc;
    }

    @Override
    public void update() {
        // no subject.getState() function implemented because existing getters can get the state for me.
        numClasses = collectionOfUniClasses.getClassMap().size();
        if (numClasses == 1) {
            System.out.println("There is " + numClasses + " class stored.");
        } else {
            System.out.println("There are " + numClasses + " classes stored.");
        }
    }
}
