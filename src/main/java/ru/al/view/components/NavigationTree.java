package ru.al.view.components;

/**
 * Created by Alex on 16.11.2015.
 */
import java.util.*;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Action;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.*;
import ru.al.WebAppUI;
import ru.al.controller.INavigationPanel;
import ru.al.controller.Observable;
import ru.al.controller.Observer;
import ru.al.controller.ViewMediator;
import ru.al.model.Customer;
import ru.al.model.Diary;
import ru.al.model.Note;
import ru.al.view.views.layouts.ConfirmationDialog;
import ru.al.view.views.layouts.TaskWindow;
import ru.al.view.wrappers.ViewUtils;


public class NavigationTree extends VerticalLayout implements Observer, INavigationPanel {
    private static final long serialVersionUID = -4292553844521293140L;
    private VerticalLayout layout;
    private Customer customer;
    private static Tree tree;
    private WebAppUI ui;
    private ViewMediator viewMediator;

    public WebAppUI getWebAppUI() {
        return ui;
    }

    public static Tree getTree() {
        return tree;
    }

    @Override
    public void update(Observable o) {
//        tree.getItem(o);
//        ArrayList children = new ArrayList();
//        children.addAll(tree.getChildren(o));
//        Iterator iterator = children.iterator();
//        Object item = tree.addItem(o);
//        Object parent = tree.getParent(o);
//        tree.setParent(item, parent);
//        while (iterator.hasNext()) {
//            Object c = iterator.next();
//            tree.setParent(c, item);
//        }
//        tree.removeItem(o);
        tree.setContainerDataSource(ViewUtils.fillDiaryContainer(customer));
    }

    public NavigationTree(WebAppUI ui, Customer customer) {
        setCaption("Navigation");
        setMargin(true);
        setSizeFull();

        this.ui = ui;
        this.customer = customer;
        layout = new VerticalLayout();

        tree = new Tree();
        tree.setDragMode(Tree.TreeDragMode.NODE);
        tree.setDropHandler(new DropHandler() {
            @Override
            public void drop(final DragAndDropEvent dropEvent) {
                // criteria verify that this is safe
                final DataBoundTransferable t = (DataBoundTransferable) dropEvent
                        .getTransferable();
                final Container sourceContainer = t.getSourceContainer();
                final Object sourceItemId = t.getItemId();
                final Item sourceItem = sourceContainer.getItem(sourceItemId);
//                final String name = sourceItem.getItemProperty("name")
//                        .toString();
//                final String category = sourceItem.getItemProperty("category")
//                        .toString();

                final AbstractSelect.AbstractSelectTargetDetails dropData = ((AbstractSelect.AbstractSelectTargetDetails) dropEvent
                        .getTargetDetails());
                final Object targetItemId = dropData.getItemIdOver();

                // find category in target: the target node itself or its parent
                System.out.println(t);
                System.out.println(sourceContainer);
                System.out.println(sourceItemId);
                System.out.println(dropData);
                System.out.println(targetItemId);
                System.out.println(dropEvent.getTransferable().getClass());
                System.out.println(dropData.getClass());
                if (targetItemId != null) {
//                    if (!dropEvent.getTransferable().getSourceComponent().getClass().equals(dropData.getTarget().getClass())) {
                        // move item from table to category'
                        final Object newItemId = tree.addItem(dropEvent.getTransferable().getSourceComponent().getId());
                    System.out.println("add " + dropEvent.getTransferable().getSourceComponent());
//                        tree.getItem(newItemId)
//                                .getItemProperty(ExampleUtil.hw_PROPERTY_NAME)
//                                .setValue(name);
                        tree.setParent(sourceItemId, targetItemId);
//                        tree.setChildrenAllowed(newItemId, false);

                        sourceContainer.removeItem(sourceItemId);
//                    } else {
//                        final String message = "Wrong target!";
//                        Notification.show(message,
//                                Notification.Type.WARNING_MESSAGE);
//                    }
                }
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                // Only allow dropping of data bound transferables within
                // folders.
                // In this example, checking for the correct category in drop()
                // rather than in the criteria.
                return AbstractSelect.AcceptItem.ALL;
            }
        });

//        ContextMenu.ContextMenuOpenedListener.TreeListener treeItemListener = event -> {
//            // Handle click based on item from event
//        };
//
//        ContextMenu contextMenu = new ContextMenu();
//        ContextMenu.ContextMenuItem rootID = contextMenu.addItem("Navigation Tree");
//
//        ContextMenu.ContextMenuItem addID = contextMenu.addItem("Создать");
//        ContextMenu.ContextMenuItem deleteID = contextMenu.addItem("Удалить");
//        ContextMenu.ContextMenuItem openID = contextMenu.addItem("Открыть в новом окне");
//        contextMenu.setAsTreeContextMenu(tree);


//        contextMenu.addContextMenuTreeListener(treeItemListener);

        tree.addItemClickListener(event -> {
            if (event.isDoubleClick()) {
                System.out.println("Open... " + (Note) event.getItemId());

                ViewUtils.editWindow((Note) event.getItemId());
            }
        });

        final Action newDiaryAction = new Action("Новый проект");
        final Action addAction = new Action("Новая задача");
        final Action delAction = new Action("Удалить ветку задач");
        final Action delOneAction = new Action("Удалить задачу");
        final Action accomplishAction = new Action("Выполнить");
        final Action openAction = new Action("Редактировать");
        final Action refreshAction = new Action("Обновить");
        tree.addActionHandler(new Action.Handler() {

            public void handleAction(Action action, Object sender,
                                     Object target) {
//                Notification.show("Ok, here I go to " + action.getCaption()+ " " + target.toString());
                if (action.equals(addAction)){
                    Note newNote = new Note("Новая задача", customer);
                    Note targetNote = (Note)target;
                    targetNote.addChild(newNote);
                    tree.addItem(newNote);
                    tree.setChildrenAllowed(newNote, false);
                    tree.setChildrenAllowed(targetNote, true);
                    tree.setParent(newNote, targetNote);

//                    WebAppUI.selectedDiaryWrapper.getDiaryView().refreshData();

                } else if (action.equals(delAction)){
//                    Item item = tree.getItem(target);
                    ConfirmationDialog confirmationDialog = new ConfirmationDialog("Удаление", "Удалить ветвь записей?");
                    Button button =
                            new Button("Подтвердить", event -> {
                    if(tree.getParent(target) != null && tree.getChildren(tree.getParent(target))!= null) {
                        tree.setChildrenAllowed(tree.getParent(target), tree.getChildren(tree.getParent(target)).size() > 1);
                    }
                    System.out.println("rem: " + (Note)target + " " + ((Note) target).getParent());
                    ((Note)target).removeAll();
//                    ViewUtils.removeTreeItem(tree, target);
                                confirmationDialog.close();
                            });
                    confirmationDialog.addFooterComponent(button);
                    confirmationDialog.addFooterComponent(new Button("Отмена", event -> {
                        confirmationDialog.close();
                    }));
                    UI.getCurrent().addWindow(confirmationDialog);
                } else if (action.equals(openAction)){
                    ui.addWindow(new TaskWindow((Note)target));
                } else if(action.equals(delOneAction)) {
                    ConfirmationDialog confirmationDialog = new ConfirmationDialog("Удаление", "Удалить запись?");
                    confirmationDialog.addFooterComponent(new Button("Подтвердить", event -> {
                        Object parent = tree.getParent(target);
                        if (parent != null) {
                            Collection tmp = tree.getChildren(target);
                            if (tmp != null && !tmp.isEmpty()) {
                                ArrayList<Object> childrens = new ArrayList<>();
                                childrens.addAll(tmp);
                                Collections.reverse(childrens);

                                tree.setChildrenAllowed(tree.getParent(target), tree.getChildren(parent).size() > 1);
                                for (Object child : childrens) {
                                    tree.setParent(child, tree.getParent(target));
                                }
                            }
                            ((Note) target).remove();
                            tree.removeItem(target);
                        } else {
                            ViewUtils.removeTreeItem(tree, target);
                            ((Note) target).remove();
                        }
                        confirmationDialog.close();
                    }));
                    confirmationDialog.addFooterComponent(new Button("Отмена", event -> {
                        confirmationDialog.close();
                    }));
                    UI.getCurrent().addWindow(confirmationDialog);

                } else if(action.equals(newDiaryAction)) {
//                    Object newDiary = tree.addItem(new Diary("Новый проект", customer));
                    Diary diary = new Diary("Новый проект", customer);
                    customer.getDiaries().add(diary);
                    tree.addItem(diary);
                    tree.setChildrenAllowed(diary, false);
                } else if (action.equals(refreshAction)){
                    tree.setContainerDataSource(ViewUtils.fillDiaryContainer(customer));
                }
            }

            public Action[] getActions(Object target, Object sender) {
                return new Action[]{newDiaryAction, addAction, delAction, delOneAction, openAction, refreshAction};
            }
        });
        tree.setImmediate(true);
        addComponent(tree);
        initNavigationTree(customer);
        System.out.println(customer.getDiaries());
//        itemClickListener();
//        setCompositionRoot(layout);
        tree.addValueChangeListener(event -> {
            tree.expandItem(event.getProperty().getValue());
//            WebAppUI.selectedDiaryWrapper.getDiaryView().setDataRoot((Note) event.getProperty().getValue());
            viewMediator.setDiaryViewContent((Note) event.getProperty().getValue());
            System.out.println("root = "+(Note)event.getProperty().getValue());
        });
    }

    public void initNavigationTree(Customer customer){
        HierarchicalContainer container = ViewUtils.fillDiaryContainer(customer);
        tree.setContainerDataSource(container);
        //////////////////////////////////////////
        tree.setDragMode(Tree.TreeDragMode.NODE);
        tree.setDropHandler(new TreeSortDropHandler(tree, container));



    }

    @Override
    public void setContent(Note note) {

    }

    @Override
    public void updateContent(Note note) {

    }

    @Override
    public void removeContent(Note note) {

    }

    @Override
    public void setViewMediator(ViewMediator viewMediator) {
        this.viewMediator = viewMediator;
    }


    private static class TreeSortDropHandler implements DropHandler {
        private final Tree tree;

        /**
         * Tree must use {@link HierarchicalContainer}.
         *
         * @param tree
         */
        public TreeSortDropHandler(final Tree tree,
                                   final HierarchicalContainer container) {
            this.tree = tree;
        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            // Alternatively, could use the following criteria to eliminate some
            // checks in drop():
            // new And(IsDataBound.get(), new DragSourceIs(tree));
            return AcceptAll.get();
        }

        @Override
        public void drop(final DragAndDropEvent dropEvent) {
            // Called whenever a drop occurs on the component

            // Make sure the drag source is the same tree
            final Transferable t = dropEvent.getTransferable();

            // see the comment in getAcceptCriterion()
            if (t.getSourceComponent() != tree
                    || !(t instanceof DataBoundTransferable)) {
                return;
            }

            final Tree.TreeTargetDetails dropData = ((Tree.TreeTargetDetails) dropEvent
                    .getTargetDetails());

            final Note sourceItemId = (Note)((DataBoundTransferable) t).getItemId();
            // FIXME: Why "over", should be "targetItemId" or just
            // "getItemId"
            final Note targetItemId = (Note)dropData.getItemIdOver();
            System.out.println("Я - " + targetItemId.getClass());
            // Location describes on which part of the node the drop took
            // place
            final VerticalDropLocation location = dropData.getDropLocation();
            System.out.println(sourceItemId);
            System.out.println(targetItemId);
            System.out.println(location);
            moveNode(sourceItemId, targetItemId, location);

        }

        /**
         * Move a node within a tree onto, above or below another node depending
         * on the drop location.
         *
         * @param sourceItemId
         *            id of the item to move
         * @param targetItemId
         *            id of the item onto which the source node should be moved
         * @param location
         *            VerticalDropLocation indicating where the source node was
         *            dropped relative to the target node
         */
        private void moveNode(final Object sourceItemId,
                              final Object targetItemId, final VerticalDropLocation location) {
            final HierarchicalContainer container = (HierarchicalContainer) tree
                    .getContainerDataSource();

            // Sorting goes as
            // - If dropped ON a node, we preppend it as a child
            // - If dropped on the TOP part of a node, we move/add it before
            // the node
            // - If dropped on the BOTTOM part of a node, we move/add it
            // after the node if it has no children, or prepend it as a child if
            // it has children

            if (location == VerticalDropLocation.MIDDLE) {
                container.setChildrenAllowed(targetItemId, true);
                Note parent = (Note)container.getParent(sourceItemId);
                ((Note)targetItemId).addChild((Note) sourceItemId);
                if (container.setParent(sourceItemId, targetItemId)
                        && container.hasChildren(targetItemId)) {
                    // move first in the container
                    System.out.println("TEST!!!");
                    System.out.println(sourceItemId);
                    System.out.println(targetItemId);
                    container.moveAfterSibling(sourceItemId, null);
                }
                container.setChildrenAllowed(parent, container.hasChildren(parent));
            } else if (location == VerticalDropLocation.TOP) {
                final Object parentId = container.getParent(targetItemId);
                if (container.setParent(sourceItemId, parentId)) {
                    // reorder only the two items, moving source above target
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    container.moveAfterSibling(targetItemId, sourceItemId);
                    ((Note)sourceItemId).moveBefore((Note) targetItemId);
                }
            } else if (location == VerticalDropLocation.BOTTOM) {
                if (container.hasChildren(targetItemId)) {
                    moveNode(sourceItemId, targetItemId,
                            VerticalDropLocation.MIDDLE);
                } else {
                    final Object parentId = container.getParent(targetItemId);
                    if (container.setParent(sourceItemId, parentId)) {
                        container.moveAfterSibling(sourceItemId, targetItemId);
                        ((Note)sourceItemId).moveAfter((Note)targetItemId);
                    }
                }
            }
        }
    }


    public void deleteItem(){

    }

    public WebAppUI getUi() {
        return ui;
    }

    public void setUi(WebAppUI ui) {
        this.ui = ui;
    }

    public VerticalLayout getLayout() {
        return layout;
    }

    public void setLayout(VerticalLayout layout) {
        this.layout = layout;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }


    public static HierarchicalContainer createTreeContent() {
        final Object[] inventory = new Object[] {
                "root",
                "+5 Quarterstaff (blessed)",
                "+3 Elven Dagger (blessed)",
                "+5 Helmet (greased)",
                new Object[] {"Sack",
                        "Pick-Axe",
                        "Lock Pick",
                        "Tinning Kit",
                        "Potion of Healing (blessed)",
                },
                new Object[] {"Bag of Holding",
                        "Potion of Invisibility",
                        "Magic Marker",
                        "Can of Grease (blessed)",
                },
                new Object[] {"Chest",
                        "Scroll of Identify",
                        "Scroll of Genocide",
                        "Towel",
                        new Object[] {"Large Box",
                                "Bugle",
                                "Oil Lamp",
                                "Figurine of Vaadin",
                                "Expensive Camera",
                        },
                        "Tin Opener",
                },
        };

        HierarchicalContainer container = new HierarchicalContainer();

        // A property that holds the caption is needed for ITEM_CAPTION_MODE_PROPERTY
        container.addContainerProperty("caption", String.class, "");

        new Object() {
            public void put(Object[] data, Object parent, HierarchicalContainer container) {
                for (int i=1; i<data.length; i++) {
                    if (data[i].getClass() == String.class) {
                        // Support both ITEM_CAPTION_MODE_ID and ITEM_CAPTION_MODE_PROPERTY
                        container.addItem(data[i]);
                        container.getItem(data[i]).getItemProperty("caption").setValue(data[i]);
                        container.setParent(data[i], parent);
                        container.setChildrenAllowed(data[i], false);
                    } else {// It's an Object[]
                        Object[] sub = (Object[]) data[i];
                        String name = (String) sub[0];

                        // Support both ITEM_CAPTION_MODE_ID and ITEM_CAPTION_MODE_PROPERTY
                        container.addItem(name);
                        container.getItem(name).getItemProperty("caption").setValue(name);
                        put(sub, name, container);
                        container.setParent(name, parent);
                    }
                }
            }
        }.put(inventory, null, container);

        return container;
    }


}
