package ru.al.view.wrappers;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import ru.al.WebAppUI;
import ru.al.model.Customer;
import ru.al.model.Diary;
import ru.al.model.Note;
import ru.al.view.views.layouts.TaskWindow;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alex on 21.11.2015.
 */
public class ViewUtils {
    private static WebAppUI ui;
    private static ArrayList<Component> componentContainer = new ArrayList<Component>();
    public static ArrayList<Component> getComponentContainer() {
        return componentContainer;
    }

    public static void setComponentContainer(ArrayList<Component> componentContainer) {
        ViewUtils.componentContainer = componentContainer;
    }

    public static WebAppUI getUi() {
        return ui;
    }


    public static HierarchicalContainer fillDiaryContainer(Customer customer){
        return fillDiaryContainer(customer, new HierarchicalContainer());
    }

    public static HierarchicalContainer fillDiaryContainer(Customer customer, HierarchicalContainer navigationContainer){
        System.out.println("customer="+customer);
        System.out.println(customer.getDiaries());
        for (Diary diary : customer.getDiaries()) {
            navigationContainer.addItem(diary);
            System.out.println("Found diary: "+diary);
            if(diary.getNotes().size() == 0) {
                navigationContainer.setChildrenAllowed(diary, false);
            } else {
                for (Note note : diary.getNotes()) {
                    navigationContainer.addItem(note);
                    navigationContainer.setParent(note, diary);
                    System.out.println("diary " + diary + " note " + note + " note parent " + note.getParent());
                    fillDiariesData(navigationContainer, note);
                }
            }
        }
        return navigationContainer;
    }

    public static void fillDiariesData(HierarchicalContainer navigationContainer, Note note){
        System.out.println("Parent note: " + note);
        if(note.getNotes().size() == 0) {
            navigationContainer.setChildrenAllowed(note, false);
        } else {
            for (Note subNote : note.getNotes()) {
                navigationContainer.addItem(subNote);
                navigationContainer.setParent(subNote, note);
                System.out.println("note " + note + " subNote " + subNote);
                System.out.println("subNote.getNotes() " + subNote.getNotes());
                if(subNote.getNotes().size() == 0) {
                    navigationContainer.setChildrenAllowed(subNote, false);
                } else {
                    for (Note subNote2 : subNote.getNotes()) {
                        navigationContainer.addItem(subNote2);
                        navigationContainer.setParent(subNote2, subNote);
                        fillDiariesData(navigationContainer, subNote2);
                    }
                }
            }
        }
    }

    public static void removeTreeItem(Tree tree, Object target){
        ArrayList childrens = new ArrayList();
        collectChildrens(childrens, tree, target);
        Collections.reverse(childrens);
        System.out.println("del " + childrens);
        for (Object child : childrens) {
            tree.removeItem(child);
        }
        tree.removeItem(target);
    }

    public static ArrayList collectChildrens(ArrayList childrens, Tree tree, Object target){
        if(tree.getChildren(target) != null) {
            System.out.println(tree.getChildren(target));
            ArrayList tmp = new ArrayList(tree.getChildren(target));
            childrens.addAll(tmp);
            for (Object child : tmp) {
                collectChildrens(childrens, tree, child);
            }
        }
        return childrens;
    }

    public static void removeTreeItem(TreeTable tree, Object target){
        ArrayList childrens = new ArrayList();
        collectTreeTableChildrens(childrens, tree, target);
        Collections.reverse(childrens);
        System.out.println("del " + childrens);
        for (Object child : childrens) {
            tree.removeItem(child);
        }
        ((Note)tree.getItem(target).getItemProperty("Title").getValue()).removeAll();
        tree.removeItem(target);
    }

    public static ArrayList collectTreeTableChildrens(ArrayList childrens, TreeTable tree, Object target){
        if(tree.getChildren(target) != null) {
            System.out.println(tree.getChildren(target));
            ArrayList tmp = new ArrayList(tree.getChildren(target));
            childrens.addAll(tmp);
            for (Object child : tmp) {
                collectTreeTableChildrens(childrens, tree, child);
            }
        }
        return childrens;
    }

    public static void editWindow(Note note){
        UI.getCurrent().addWindow(new TaskWindow(note));
    }

    public static void setUi(WebAppUI ui) {
        ViewUtils.ui = ui;
    }
}
