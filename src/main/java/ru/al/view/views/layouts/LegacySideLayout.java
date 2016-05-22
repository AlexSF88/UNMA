package ru.al.view.views.layouts;

import com.vaadin.ui.*;
import ru.al.WebAppUI;
import ru.al.model.Note;
import ru.al.model.Diary;
import ru.al.model.Task;
import ru.al.model.Customer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alex on 07.11.2015.
 */
public class LegacySideLayout extends VerticalLayout {
    Label label;
    Button addTask;
    TextField taskField;
    Button addNoteBook;
    TextField noteBookField;
    ComboBox noteBookCombo;
    ComboBox subNoteCombo;

    Customer customer;
    WebAppUI ui;

    public LegacySideLayout(WebAppUI ui) {
        this.ui = ui;
        this.customer = ui.getCustomer();
        label = new Label();
        label.setValue(customer.getLogin() + ", " + customer.getEmail());
        label.setSizeUndefined();
//        label.setContentMode(ContentMode.HTML);
//        label.setWidth(80, Unit.PERCENTAGE);
        addTask = new Button("Create task");
        taskField = new TextField("New task:");

        System.out.println("Task data: " + customer);
        addTask.addClickListener(clickEvent -> {
            Task tmp = new Task("", customer);
            tmp.setTitle(taskField.getValue());
//            bodyLayout.createTask(new TaskView(tmp));
            System.out.println("Note: " + subNoteCombo.getValue());
            if(subNoteCombo.getValue() instanceof Task){
                customer.getSelectedTask().getNotes().add(tmp);
                System.out.println("Selected Task: " + customer.getSelectedTask());
                System.out.println("Selected Task Notes: "+ customer.getSelectedTask().getNotes());
            } else {
                subNoteCombo.addItem(tmp);
                customer.getSelectedNB().getNotes().add(tmp);
                System.out.println(customer.getSelectedNB().getNotes());
            }
            taskField.clear();
        });
        noteBookCombo = new ComboBox("My NoteBooks");
//        noteBookCombo.addItems(customer.getDiaries());
        noteBookCombo.setNewItemHandler(new AbstractSelect.NewItemHandler() {
            @Override
            public void addNewItem(final String newItemCaption) {
                boolean newItem = true;
                for (final Object itemId : noteBookCombo.getItemIds()) {
                    if (newItemCaption.equalsIgnoreCase(noteBookCombo
                            .getItemCaption(itemId))) {
                        newItem = false;
                        break;
                    }
                }
                if (newItem) {
                    // Adds new option
                    noteBookCombo.addItem(newItemCaption);
                }
            }
        });
        noteBookCombo.addValueChangeListener(e -> {
            Notification.show("NB changed:",
                    String.valueOf(e.getProperty().getValue()),
                    Notification.Type.TRAY_NOTIFICATION);
            Diary nb = (Diary) e.getProperty().getValue();
            System.out.println("e.getProperty().getValue()"+e.getProperty().getValue());
            if(e.getProperty().getValue() != null) {
                customer.setSelectedNB(nb);
                refreshBody(nb.getNotes());
            }
//                    (Diary) noteBookCombo.getItem(e.getProperty().getValue()).);
        });

        subNoteCombo = new ComboBox("My Task");
//        noteBookCombo.addItems(customer.getDiaries());
        subNoteCombo.setNewItemHandler(new AbstractSelect.NewItemHandler() {
            @Override
            public void addNewItem(final String newItemCaption) {
                boolean newItem = true;
                for (final Object itemId : subNoteCombo.getItemIds()) {
                    if (newItemCaption.equalsIgnoreCase(subNoteCombo
                            .getItemCaption(itemId))) {
                        newItem = false;
                        break;
                    }
                }
                if (newItem) {
                    // Adds new option
                    subNoteCombo.addItem(newItemCaption);
                }
            }
        });
        subNoteCombo.addValueChangeListener(e -> {
            Notification.show("Task changed:",
                    String.valueOf(e.getProperty().getValue()),
                    Notification.Type.TRAY_NOTIFICATION);
//            bodyLayout.refreshNoteBook();
            System.out.println("Selected Task: " + customer.getSelectedTask());
            Note selectedNote = (Note) e.getProperty().getValue();
            System.out.println("selectedNote: " + selectedNote);

            if (selectedNote == null) {
                ArrayList<Note> subTasks = new ArrayList<>(customer.getSelectedNB().getNotes());
                refreshBody(subTasks);
            } else if (selectedNote instanceof Task) {
                customer.setSelectedTask((Task) selectedNote);
//                ArrayList<Task> subTasks = ((Task) selectedNote).getNotes();
//                refreshBody(subTasks);
            }
//                    (Diary) noteBookCombo.getItem(e.getProperty().getValue()).);
        });
        noteBookField = new TextField("Create new NoteBook:");
        addNoteBook = new Button("Create NB");
        addNoteBook.addClickListener(clickEvent -> {
            Diary nb = new Diary(noteBookField.getValue(), customer);
            customer.setSelectedNB(nb);
            customer.getDiaries().add(nb);
            noteBookCombo.addItem(nb);
            noteBookCombo.setValue(nb);
            noteBookField.clear();
        });
        addComponent(label);
        addComponent(noteBookCombo);
        addComponent(noteBookField);
        addComponent(addNoteBook);
        addComponent(subNoteCombo);
        addComponent(taskField);
        addComponent(addTask);

        setComponentAlignment(noteBookCombo, Alignment.TOP_CENTER);
        setComponentAlignment(label, Alignment.TOP_CENTER);
        setComponentAlignment(taskField, Alignment.MIDDLE_CENTER);
        setComponentAlignment(addTask, Alignment.BOTTOM_CENTER);
        setComponentAlignment(noteBookField, Alignment.BOTTOM_CENTER);
        setComponentAlignment(addNoteBook, Alignment.BOTTOM_CENTER);
        setComponentAlignment(subNoteCombo, Alignment.BOTTOM_CENTER);

        setSpacing(true);
        init();
    }

    public void refreshBody(List notes) {
//        bodyLayout.refreshNoteBook();
        for (Object note : notes) {
            System.out.println(note);
            if(note instanceof Task) {
                Task task = (Task) note;
//                bodyLayout.createTask(new TaskView(task));
            } else if(note instanceof Note) {
                Task task = (Task) note;
//                bodyLayout.createTask(new TaskView(task));
            }
        }
    }

    public void init(){
        noteBookCombo.addItems(customer.getDiaries());
        noteBookCombo.setImmediate(true);
        noteBookCombo.setNullSelectionItemId(0);
        System.out.println(customer.getDiaries());
    }

    public ComboBox getNoteBookCombo() {
        return noteBookCombo;
    }

    public void setNoteBookCombo(ComboBox noteBookCombo) {
        this.noteBookCombo = noteBookCombo;
    }

    public TextField getNoteBookField() {
        return noteBookField;
    }

    public void setNoteBookField(TextField noteBookField) {
        this.noteBookField = noteBookField;
    }
}
