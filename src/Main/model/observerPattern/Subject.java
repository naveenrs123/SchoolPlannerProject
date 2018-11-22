package model.observerPattern;

import model.observerPattern.Observer;

import java.util.ArrayList;

public class Subject {

    ArrayList<Observer> observers;

    public Subject() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }
    
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
