package ru.al.view.views.layouts;

import com.vaadin.ui.*;
import ru.al.model.Note;
import ru.al.model.Task;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Alex on 09.11.2015.
 */
public class TaskWindow extends Window {
    private VerticalLayout verticalLayout;
    private HorizontalLayout headerLayout;
    private HorizontalLayout buttonLayout;
    private TextField title;
    private Label creationDate;
    private DateField storyDate;
    private RichTextArea description;
    private Button confirm;
    private Button cancel;
    private Note task;

    public TaskWindow(Note task) {
        this.task = task;

        setWidth(40, Unit.PERCENTAGE);
        setHeight(80, Unit.PERCENTAGE);
        verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setMargin(true);
        headerLayout = new HorizontalLayout();
        buttonLayout = new HorizontalLayout();
        title = new TextField();
        creationDate = new Label();
        storyDate = new DateField();
        storyDate.setValue(new Date());
        description = new RichTextArea();
        description.setSizeFull();

        confirm = new Button("Confirm");
        cancel = new Button("Cancel");

        confirm.addClickListener(clickEvent1 -> {
            setTask();
            this.close();
        });
        cancel.addClickListener(clickEvent -> {
            this.close();
        });

        buttonLayout.addComponent(confirm);
        buttonLayout.addComponent(cancel);

        headerLayout.addComponent(title);
        headerLayout.addComponent(storyDate);

        verticalLayout.addComponent(headerLayout);
//        verticalLayout.addComponent(creationDate);
        verticalLayout.addComponent(description);
        verticalLayout.addComponent(buttonLayout);
        verticalLayout.setExpandRatio(description, 0.8f);
        verticalLayout.setComponentAlignment(headerLayout, Alignment.TOP_CENTER);
//        verticalLayout.setComponentAlignment(storyDate, Alignment.TOP_CENTER);
        verticalLayout.setComponentAlignment(description, Alignment.TOP_CENTER);
        verticalLayout.setComponentAlignment(buttonLayout, Alignment.TOP_CENTER);

        setContent(verticalLayout);
        headerLayout.setMargin(true);
        headerLayout.setSpacing(true);
        verticalLayout.setSpacing(true);
        buttonLayout.setSpacing(true);
        init();
        center();
    }

    public void init(){
        title.setValue(task.getTitle());
        LocalDate ld = task.getStoryDate();
        if (ld != null) {
            Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date res = Date.from(instant);
            storyDate.setValue(res);
        }
        description.setValue(task.getText());
    }

    public void setTask(){
        task.setTitle(title.getValue());
        task.setText(description.getValue());
        task.setStoryDate(storyDate.getValue());
    }
}
