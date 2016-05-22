package ru.al.view.views.layouts;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.*;
import ru.al.WebAppUI;
import ru.al.controller.INavigationPanel;
import ru.al.model.Customer;
import ru.al.model.Note;
import ru.al.view.components.EditableLabel;
import ru.al.view.components.NavigationTree;

/**
 * Created by Alex on 06.01.2016.
 */
public class SideLayout extends TabSheet {
    private NavigationTree navigationTree;

    public SideLayout(WebAppUI ui, Customer customer) {
        setSizeFull();
        navigationTree = new NavigationTree(ui, customer);

        DragAndDropWrapper navigationTabWrapper = new DragAndDropWrapper(navigationTree);
        navigationTabWrapper.setSizeFull();
        navigationTabWrapper.setDropHandler(new DropHandler() {
            @Override
            public void drop(DragAndDropEvent event) {
                navigationTree.removeAllComponents();
                navigationTree.addComponent(event.getTransferable().getSourceComponent());
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        });

        addTab(navigationTabWrapper, "Navigation");

        LegacySideLayout configurationTab = new LegacySideLayout(ui);
        configurationTab.getNoteBookField().setImmediate(true);
//        configurationTab.getNoteBookField().setPropertyDataSource(property);
        configurationTab.setCaption("Configuration");
//        configurationTab.setSizeFull();
        addTab(configurationTab, "Configuration");

        VerticalLayout informationTab = new VerticalLayout();
        informationTab.setCaption("Information");
        informationTab.setSizeFull();
        addTab(informationTab, "Information");

        VerticalLayout componentsCollection = new VerticalLayout();
        componentsCollection.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        componentsCollection.setSizeFull();
        DragAndDropWrapper ccWrapper = new DragAndDropWrapper(componentsCollection);
        ccWrapper.setSizeFull();
        ccWrapper.setDropHandler(new DropHandler() {
            @Override
            public void drop(DragAndDropEvent event) {
//                componentsCollection.removeAllComponents();
                componentsCollection.addComponent(event.getTransferable().getSourceComponent());
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        });
        EditableLabel label = new EditableLabel("I'M YOUR NOTE LABEL!");
        addTab(ccWrapper, "Components");
        DragAndDropWrapper labelWrapper = new DragAndDropWrapper(label);
        labelWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
        componentsCollection.addComponent(labelWrapper);


        Button button = new Button("Press Me!");
        button.addClickListener(event -> {
            Notification.show(label.getValue());
        });
        DragAndDropWrapper buttonWrapper = new DragAndDropWrapper(button);
        buttonWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
        componentsCollection.addComponent(buttonWrapper);
    }

    public NavigationTree getNavigationTree() {
        return navigationTree;
    }
}
