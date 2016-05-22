package ru.al.view.views;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.Action;
import com.vaadin.ui.*;
import ru.al.controller.IDiaryView;
import ru.al.controller.Observable;
import ru.al.controller.Observer;
import ru.al.controller.ViewMediator;
import ru.al.model.Diary;
import ru.al.model.Note;
import ru.al.model.Customer;
import ru.al.view.views.layouts.BodyLayout;
import ru.al.view.views.layouts.CustomTreeTable;
import ru.al.view.components.NavigationTree;
import ru.al.view.wrappers.ViewUtils;

/**
 * Created by Alex on 19.11.2015.
 */
public class DiaryView extends CustomComponent implements IDiaryView, Observer{
    Diary diary;
    CustomTreeTable treeTable = new CustomTreeTable();
    ViewMediator viewMediator;
    public DiaryView() {

    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
        init ();
    }

    public void init (){
        TabSheet diaryLayout = new TabSheet();
        diaryLayout.setSizeFull();

        BodyLayout noteTab = new BodyLayout();
        noteTab.setParameters(50, 50, Unit.PERCENTAGE);
        noteTab.setSplitPosition(50, Unit.PERCENTAGE);
        RichTextArea textArea = new RichTextArea();

        HorizontalLayout pbLayout = new HorizontalLayout();
        pbLayout.setSizeFull();
        pbLayout.setSpacing(true);
        ProgressBar pBar = new ProgressBar();
        pBar.setWidth(100, Unit.PERCENTAGE);
        treeTable.setValue(diary);
        diary.setAccomplishedSubTask(10);
        pBar.setValue(0.5f);
        Property pBarProperty = new ObjectProperty(Float.valueOf(diary.getStatus()));
//        pBar.setPropertyDataSource(pBarProperty);
        Label statusLabel = new Label();
        Property statusProperty = new ObjectProperty(diary.getStatus());
        statusLabel.setPropertyDataSource(statusProperty);
        pbLayout.addComponent(pBar);
//        pbLayout.addComponent(statusLabel);
        textArea.setImmediate(true);
        textArea.addValueChangeListener(event1 -> {
//            Item item = (Item)treeTable.getValue();
            Object item = event1.getProperty().getValue();

            if(item != null && treeTable.getValue() != null) {
                ((Note) treeTable.getItem(treeTable.getValue()).getItemProperty("Title").getValue()).setText(item.toString());
//            ((Note)item.getItemProperty("Title").getValue()).setText(event1.getProperty().getValue().toString());
//                System.out.println(treeTable.getItem(treeTable.getValue()).getItemProperty("Title"));
//            treeTable.getItem(treeTable.getValue()).getItemProperty("Text").setValue(event1.getProperty().getValue().toString());
            }
        });
        textArea.setSizeFull();
//        DraggingField leftDraggingField = new DraggingField();
//        DraggingField rightDraggingField = new DraggingField();



        treeTable.setSelectable(true);
        treeTable.setSizeFull();
        treeTable.addValueChangeListener(event -> {
            Item item = treeTable.getItem(event.getProperty().getValue());
            if (item != null) {
                ObjectProperty property =
                        new ObjectProperty(((Note) treeTable.getItem(treeTable.getValue()).getItemProperty("Title").getValue()).getText(), String.class);
                textArea.setPropertyDataSource(property);
//                String text = ((Note) item.getItemProperty("Title").getValue()).getText();
//                textArea.setValue(text);
            }
        });
        initTree(treeTable, diary);
//        treeTable.setContainerDataSource(ViewUtils.initTree(treeTable, diary));
//        initTree();
//        treeTable.setContainerDataSource();

//        treeTable.addContainerProperty("Title", Note.class, null);

//        treeTable.addContainerProperty("Text", Integer.class, 0);
//        treeTable.addContainerProperty("Creation Date", Date.class, new Date());
//        populateWithRandomHierarchicalData(treeTable);
        VerticalLayout leftSide = new VerticalLayout();
        leftSide.setSizeFull();
//        leftSide.addComponent(pBar);
//        leftSide.setExpandRatio(pBar, 0.1f);
        leftSide.addComponent(textArea);
        leftSide.setExpandRatio(textArea, 3.0f);
//        leftSide.setComponentAlignment(pBar, Alignment.MIDDLE_CENTER);
        noteTab.setFirstComponent(leftSide);
        noteTab.setSecondComponent(treeTable);
        noteTab.setSizeFull();

//        dailyTab.addComponent(textArea);
//        dailyTab.addComponent(rightDraggingField);
//        dailyTab.setComponentAlignment(textArea, Alignment.MIDDLE_LEFT);
//        dailyTab.setComponentAlignment(rightDraggingField, Alignment.MIDDLE_RIGHT);

        HorizontalLayout dailyTab = new HorizontalLayout();
        dailyTab.setSizeFull();
        diaryLayout.addTab(noteTab, "Notes");
        diaryLayout.addTab(dailyTab, "New Task");

        HorizontalLayout diaryTitleLayout = new HorizontalLayout();
        diaryTitleLayout.setSpacing(true);


//        BeanItem<Note> noteBean = new BeanItem<Note>(note);
//        Form form = new Form();
//        form.setItemDataSource(noteBean);
//        FormLayout fl = new FormLayout();
//        fl.addComponent(form);
//        fl.setSizeFull();
//        fl.setMargin(true);
//        dailyTab.addComponent(fl);


        setCompositionRoot(diaryLayout);
        setSizeFull();

        System.out.println("test! " + treeTable.getItem(1));

        final Action addAction = new Action("Новая задача");
        final Action delAction = new Action("Удалить ветку задач");
        final Action delOneAction = new Action("Удалить задачу");
        final Action accomplishAction = new Action("Изменить статус");
        final Action openAction = new Action("Редактировать");
        treeTable.addItemClickListener(event -> {
            if(event.isDoubleClick()){
                ViewUtils.editWindow((Note) event.getItem().getItemProperty("Title").getValue());
            }
        });
        treeTable.addItemClickListener(event -> {
            if (event.getButton().getName().equals("right")) {
//                accomplishAction.setCaption(((Note) event.getItem().getItemProperty("title").getValue()).isActive() ? "Выполнить" : "Открыть");
            }
        });
        treeTable.addActionHandler(new Action.Handler() {

            public void handleAction(Action action, Object sender,
                                     Object target) {
                if (target == null) {
                    target = treeTable.firstItemId();
                }
                Note targetNote = (Note) treeTable.getItem(target).getItemProperty("Title").getValue();
                if (action.equals(addAction)) {
                    Note newNote = new Note("Новая задача", diary.getOwner());


                    targetNote.addChild(newNote);
//                    Object newItem = treeTable.addItem(newNote);
//                    treeTable.setChildrenAllowed(newItem, false);
//                    treeTable.setChildrenAllowed(target, true);
//                    treeTable.setParent(newItem, target);
//                    setContent(WebAppUI.selectedDiaryWrapper.getDiaryView().getContent());
//                    NavigationTree.getTree().cdiary.getOwner()));
                } else if (action.equals(delAction)) {
//                    Item item = tree.getItem(target);
                    targetNote.removeAll();
//                    if (treeTable.getParent(target) != null) {
//                        treeTable.setChildrenAllowed(treeTable.getParent(target), treeTable.hasChildren(treeTable.getParent(target)));
//                    }
//                    ViewUtils.removeTreeItem(treeTable, target);
//                    NavigationTree.getTree().setContainerDataSource(ViewUtils.fillDiaryContainer(diary.getOwner()));
//                    diary.updateStatus();
//                    refreshData();
                } else if (action.equals(openAction)) {
                    ViewUtils.editWindow((Note) treeTable.getItem(target).getItemProperty("Title").getValue());
//                    ui.addWindow(new TaskWindow((Note) target));
                } else if (action.equals(delOneAction)) {
                    targetNote.remove();
                    if (treeTable.getParent(target) != null) {
                        treeTable.setChildrenAllowed(treeTable.getParent(target), treeTable.getChildren(treeTable.getParent(target)).size() > 1);
                    }
                    treeTable.removeItem(target);
                    diary.updateStatus();
                    refreshData();
                    NavigationTree.getTree().setContainerDataSource(ViewUtils.fillDiaryContainer(diary.getOwner()));
                } else if (action.equals(accomplishAction)) {
                    targetNote.setActive(!targetNote.isActive());
                    NavigationTree.getTree().setContainerDataSource(ViewUtils.fillDiaryContainer(diary.getOwner()));
                    diary.updateStatus();
                    refreshData();
                }

            }

            public Action[] getActions(Object target, Object sender) {
                return new Action[]{addAction, delAction, delOneAction, accomplishAction, openAction};
            }
        });
        treeTable.setImmediate(true);
    }

    public void refreshTreeTable(){
        initTree(treeTable, diary);
    }

    public TreeTable getTreeTable() {
        return treeTable;
    }

    public void setTreeTable(CustomTreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public void setContent(Note rootNote) {
        if(rootNote != null) {
            treeTable.removeAllItems();
            final Object rootId = treeTable.addItem(rootNote);
            treeTable.setCollapsed(rootId, false);
            fillTreeTable(treeTable, rootNote, rootId);
        }
    }

    public static TreeTable initTree(Diary diary){
        return initTree(new CustomTreeTable(), diary);
    }

    public static CustomTreeTable initTree(CustomTreeTable treeTable, Diary diary){
        treeTable.removeAllItems();
        treeTable.addContainerProperty("Title", Note.class, "-");
        treeTable.setColumnExpandRatio("Title", 0.4f);
        treeTable.setColumnAlignment("Title", Table.Align.LEFT);
        treeTable.addContainerProperty("Status", String.class, 0);
//        treeTable.setColumnExpandRatio("Status", 0.2f);
        treeTable.setColumnWidth("Status", 40);
        treeTable.setColumnAlignment("Status", Table.Align.CENTER);
        treeTable.addContainerProperty("To do", Integer.class, 0);
//        treeTable.setColumnExpandRatio("Tasks", 0.2f);
        treeTable.setColumnWidth("To do", 40);
        treeTable.setColumnAlignment("To do", Table.Align.CENTER);
        treeTable.addContainerProperty("Done", Integer.class, 0);
//        treeTable.setColumnExpandRatio("Tasks", 0.2f);
        treeTable.setColumnWidth("Done", 40);
        treeTable.setColumnAlignment("Done", Table.Align.CENTER);
        treeTable.addContainerProperty("Author", Customer.class, "-");
        treeTable.setColumnExpandRatio("Author", 0.2f);
        treeTable.setColumnAlignment("Author", Table.Align.CENTER);
        treeTable.addContainerProperty("Last update", String.class, "-");
//        treeTable.setColumnExpandRatio("Last update", 0.3f);
        treeTable.setColumnAlignment("Last update", Table.Align.CENTER);
        treeTable.setColumnWidth("Last update", 95);
        for (Note note : diary.getNotes()) {
            final Object parentId = treeTable.addItem(note);
            if(note.getNotes().size() == 0){
                treeTable.setChildrenAllowed(parentId, false);
            } else {
                treeTable.setCollapsed(parentId, false);
                for (Note subNote : note.getNotes()) {
                    final Object childId = treeTable.addItem(subNote);
                    treeTable.setParent(childId, parentId);
                    treeTable.setCollapsed(childId, false);
                    //                System.out.println("test: " + parentId + " child: " + childId);
                    fillTreeTable(treeTable, subNote, childId);
                }

            }
        }
        return treeTable;
    }

    public static void fillTreeTable(CustomTreeTable treeTable, Note rootNote, Object rootId){
        if(rootNote!=null) {
            if (rootNote.getNotes().size() == 0) {
                treeTable.setChildrenAllowed(rootId, false);
            } else {
                for (Note note : rootNote.getNotes()) {
                    final Object childId = treeTable.addItem(note);
                    treeTable.setParent(childId, rootId);
                    treeTable.setCollapsed(childId, false);
                    if (note.getNotes().size() == 0) {
                        treeTable.setChildrenAllowed(childId, false);
                    } else {
                        for (Note subNote : note.getNotes()) {
                            final Object subNoteId = treeTable.addItem(subNote);
                            treeTable.setParent(subNoteId, childId);
                            treeTable.setCollapsed(subNoteId, false);
                            fillTreeTable(treeTable, subNote, subNoteId);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Note getContent() {
        return (Note) treeTable.getItem(treeTable.firstItemId()).getItemProperty("Title").getValue();
    }
    @Override
    public void refreshData(){
       setContent(getContent());
    }

    @Override
    public void remove(Note note) {

    }

    @Override
    public void addNote(Note newNote, Note parentNote) {

    }

    @Override
    public void update(Observable o) {
        System.out.println("refreshTreeTable");
        setContent((Note)treeTable.getItem(treeTable.getItemIds().iterator().next()).getItemProperty("Title").getValue());
    }

    @Override
    public void setViewMediator(ViewMediator viewMediator) {
        this.viewMediator = viewMediator;
    }
}
