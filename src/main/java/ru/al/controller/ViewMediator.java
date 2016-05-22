package ru.al.controller;

import ru.al.model.Note;

/**
 * Created by Alex on 05.01.2016.
 */
public interface ViewMediator {
    public void setNavigationPanel(INavigationPanel sidePanel);
    public void setDiaryView(IDiaryView diaryView);
    public void setHeaderMenu(IHeaderMenu headerMenu);
    public INavigationPanel getNavigationPanel();
    public IDiaryView getDiaryView();
    public IHeaderMenu getHeaderMenu();
    public void refreshNavigationPanel();
    public void setDiaryViewContent(Note note);
}
