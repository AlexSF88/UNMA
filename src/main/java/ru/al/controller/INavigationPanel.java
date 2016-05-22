package ru.al.controller;

import ru.al.model.Note;

/**
 * Created by Alex on 23.01.2016.
 */
public interface INavigationPanel extends IViewCollegue{
    public void setContent(Note note);
    public void updateContent(Note note);
    public void removeContent(Note note);
}
