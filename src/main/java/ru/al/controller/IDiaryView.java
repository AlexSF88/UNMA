package ru.al.controller;

import ru.al.model.Diary;
import ru.al.model.Note;

/**
 * Created by Alex on 19.11.2015.
 */
public interface IDiaryView extends IViewCollegue{

    public void setDiary(Diary diary);
    public void setContent(Note note);
    public Note getContent();
    public void remove(Note note);
    public void addNote(Note note, Note parentNote);
    public void refreshData();
}
