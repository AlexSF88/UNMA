package ru.al.view.wrappers;

import com.vaadin.ui.CustomComponent;
import ru.al.WebAppUI;
import ru.al.controller.IDiaryView;
import ru.al.model.Diary;
import ru.al.model.Customer;
import ru.al.view.views.DiaryView;

/**
 * Created by Alex on 19.11.2015.
 */
public class DiaryWrapper extends CustomComponent{
    Customer customer;
    WebAppUI ui;
    private IDiaryView diaryView;
    private Diary diary;

    public void init() {
//        ((CustomComponent)diaryView).setSizeFull();
        setCompositionRoot((CustomComponent)diaryView);
        setSizeFull();
    }

    public DiaryWrapper(Customer customer, WebAppUI ui, Diary diary, IDiaryView diaryView) {
        this.customer = customer;
        this.ui = ui;
        this.diaryView = diaryView;
        this.diary = diary;
        this.diaryView.setDiary(diary);
        init();
    }

    public DiaryWrapper(IDiaryView diaryView, Diary diary) {
        this.diaryView = diaryView;
        this.diary = diary;
        this.diaryView.setDiary(diary);
        init();
    }

    public IDiaryView getDiaryView() {
        return diaryView;
    }

    public void setDiaryView(DiaryView diaryView) {
        this.diaryView = diaryView;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }


}
