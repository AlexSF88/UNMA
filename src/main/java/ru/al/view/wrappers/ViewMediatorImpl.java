package ru.al.view.wrappers;

import ru.al.controller.IDiaryView;
import ru.al.controller.IHeaderMenu;
import ru.al.controller.INavigationPanel;
import ru.al.controller.ViewMediator;
import ru.al.model.Note;

/**
 * Created by Alex on 06.01.2016.
 */
public class ViewMediatorImpl implements ViewMediator {
    private INavigationPanel navigationPanel;
    private IDiaryView diaryView;
    private IHeaderMenu headerMenu;

    @Override
    public void setNavigationPanel(INavigationPanel navigationPanel) {
        this.navigationPanel = navigationPanel;
        navigationPanel.setViewMediator(this);
    }

    @Override
    public void setDiaryView(IDiaryView diaryView) {
        this.diaryView = diaryView;
        diaryView.setViewMediator(this);
    }

    @Override
    public void setHeaderMenu(IHeaderMenu headerMenu) {
        this.headerMenu = headerMenu;
        headerMenu.setViewMediator(this);
    }

    @Override
    public INavigationPanel getNavigationPanel() {
        return navigationPanel;
    }

    @Override
    public IDiaryView getDiaryView() {
        return diaryView;
    }

    @Override
    public IHeaderMenu getHeaderMenu() {
        return headerMenu;
    }

    @Override
    public void refreshNavigationPanel() {

    }

    @Override
    public void setDiaryViewContent(Note note) {
        diaryView.setContent(note);
    }
}
