package ru.al.controller;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Alex on 05.01.2016.
 */
public interface Observable {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void update();
    void addAllObservers(Collection<Observer> observers);
}
