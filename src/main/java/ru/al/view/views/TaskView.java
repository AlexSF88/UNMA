package ru.al.view.views;

import com.vaadin.ui.*;
import ru.al.controller.INoteView;
import ru.al.model.Task;
import ru.al.view.components.EditableLabel;
import ru.al.view.views.layouts.TaskWindow;

/**
 * Created by Alex on 07.11.2015.
 */
public class TaskView extends HorizontalLayout implements INoteView {
    private String taskId;
    private EditableLabel titleLabel;
    private ProgressBar progressBar;
    private VerticalLayout verticalLayout;
    private Button button;
    private Task task;

    public TaskView(Task task) {
        this.task = task;
        paint();
    }

    public void paint(){
        titleLabel = new EditableLabel();
        titleLabel.setValue(task.getTitle());
        verticalLayout = new VerticalLayout();
        progressBar = new ProgressBar();
        button = new Button("Edit");
        button.addClickListener(clickEvent -> {
            task.setTitle(titleLabel.toString());
            getParent().getUI().addWindow(new TaskWindow(task));
        });

        progressBar.setSizeFull();
        progressBar.setEnabled(false);
        verticalLayout.addComponent(titleLabel);
        verticalLayout.addComponent(progressBar);
        addComponent(verticalLayout);
        addComponent(button);
        setSpacing(true);
        setMargin(true);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String id) {
        this.taskId = id;
    }

    public Float getProgress() {
        return progressBar.getValue();
    }

    public void setProgress(Float progress) {
        progressBar.setValue(progress);
    }

}
