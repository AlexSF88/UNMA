package ru.al.view.views.layouts;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import ru.al.WebAppUI;
import ru.al.model.Customer;
import ru.al.view.views.DiaryView;
import ru.al.view.wrappers.DiaryWrapper;

/**
 * Created by Alex on 06.01.2016.
 */
public class WorkTable extends GridLayout{
    public WorkTable(WebAppUI ui, Customer customer, DiaryView diaryView) {
        DiaryWrapper selectedDiaryWrapper;
        selectedDiaryWrapper = new DiaryWrapper(customer, ui, customer.getDiaries().get(0), diaryView);
        DragAndDropWrapper tableSideDrop;
        setColumns(7);
        setRows(7);
        setSizeFull();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(i == 0 || i == 6 || j == 0 || j == 6) {
                    HorizontalLayout tableSideLayout = new HorizontalLayout();
                    tableSideLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
                    tableSideLayout.setSizeFull();
                    tableSideDrop = new DragAndDropWrapper(tableSideLayout);
                    tableSideDrop.setSizeFull();
                    tableSideDrop.setDropHandler(new DropHandler() {
                        @Override
                        public void drop(DragAndDropEvent event) {
                            tableSideLayout.removeAllComponents();
                            tableSideLayout.addComponent(event.getTransferable().getSourceComponent());
                        }

                        @Override
                        public AcceptCriterion getAcceptCriterion() {
                            return AcceptAll.get();
                        }
                    });
                    addComponent(tableSideDrop, i, j);
                }
            }
        }
//        tableLayout.removeComponent(1, 1);
//        tableLayout.removeComponent(1, 1);
        HorizontalLayout tableDiaryDrop = new HorizontalLayout();
        tableDiaryDrop.setSizeFull();

        DragAndDropWrapper tableDrop = new DragAndDropWrapper(tableDiaryDrop);
        tableDrop.setSizeFull();
        tableDrop.setDropHandler(new DropHandler() {
            @Override
            public void drop(DragAndDropEvent event) {
                tableDiaryDrop.removeAllComponents();
                tableDiaryDrop.addComponent(event.getTransferable().getSourceComponent());
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        });

        tableDiaryDrop.addComponent(selectedDiaryWrapper);
//        tableDiaryDrop.addComponent(dndDiary);
        addComponent(tableDrop, 1, 1, 5, 5);
    }
}
