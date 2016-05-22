package ru.al.view.views.layouts;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import ru.al.WebAppUI;
import ru.al.model.Customer;
import ru.al.view.components.DraggingField;
import ru.al.view.views.TaskView;

/**
 * Created by Alex on 07.11.2015.
 */
public class WallLayout extends GridLayout{
    DraggingField leftSide;
    DraggingField rightSide;
    int freeRows = 10;
    private WebAppUI ui;
    private Customer customer;

    public WallLayout() {
        this.ui = ui;
        this.customer = ui.getCustomer();
        setColumns(3);
        setRows(3);
        leftSide = new DraggingField();
        rightSide = new DraggingField();
        leftSide.setSpacing(true);
        rightSide.setSpacing(true);
        rightSide.setSizeFull();
        leftSide.setSizeFull();
        addComponent(leftSide, 1, 1);
        addComponent(rightSide, 2, 1);
        setComponentAlignment(leftSide, Alignment.MIDDLE_CENTER);
        setComponentAlignment(rightSide, Alignment.MIDDLE_CENTER);
        setSizeFull();
        setRowExpandRatio(0, 0.2f);
        setRowExpandRatio(1, 0.6f);
        setRowExpandRatio(2, 0.2f);
    }

    public void init(){

    }

    public void createTask(TaskView task){
        if(freeRows > 5){
            leftSide.addDropComponent(task);
//            leftSide.setComponentAlignment(task, Alignment.TOP_CENTER);
            freeRows--;
        } else if(freeRows >0){
            rightSide.addDropComponent(task);
//            rightSide.setComponentAlignment(task, Alignment.TOP_CENTER);
            freeRows--;
        }
    }

    public void refreshNoteBook(){
        freeRows = 10;
        removeAllComponents();
        leftSide = new DraggingField();
        rightSide = new DraggingField();
        addComponent(leftSide, 1, 1);
        addComponent(rightSide, 2, 1);
    }
}
