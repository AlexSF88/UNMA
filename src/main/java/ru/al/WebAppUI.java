package ru.al;


import com.google.gwt.thirdparty.json.JSONArray;
import com.google.gwt.thirdparty.json.JSONException;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import elemental.json.JsonArray;
import ru.al.controller.Observer;
import ru.al.controller.ServerConnector;
import ru.al.controller.ViewMediator;
import ru.al.model.ServiceManager;
import ru.al.model.Customer;
import ru.al.view.components.NavigationTree;
import ru.al.view.views.layouts.*;
import ru.al.view.views.DiaryView;
import ru.al.view.wrappers.DiaryWrapper;
import ru.al.view.wrappers.ViewMediatorImpl;
import ru.al.view.wrappers.ViewUtils;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 07.11.2015.
 */
//@Theme("mytheme")
@Widgetset("ru.al.MyAppWidgetset")
public class WebAppUI extends UI {
    private VerticalLayout windowLayout;
    private HeaderMenu menuBar;
    private BodyLayout bodyLayout;
    private TabSheet sideLayout;
    private NavigationTree navigationTree;
    private ServerConnector serverConnector;
    private Customer customer;

    @Override
    public void init(VaadinRequest request) {
        serverConnector = new ServiceManager();
        initAuthorization();
    }

    public void initAuthorization () {
        GridLayout gridLayout = new GridLayout(3, 3);
        VerticalLayout authorizationLayout = new VerticalLayout();
        TextField loginField = new TextField("Введите логин:");
        loginField.setValue("Admin");
        PasswordField passwordField = new PasswordField("Введите пароль:");
        passwordField.setValue("123");
        Button confirmButton = new Button("Войти");
        confirmButton.addClickListener(clickEvent -> {
            autorise(loginField.getValue().trim(), passwordField.getValue().trim());
            loginField.clear();
            passwordField.clear();
        });
        gridLayout.addComponent(authorizationLayout, 1, 1);
        gridLayout.setSizeFull();
        authorizationLayout.addComponent(loginField);
        authorizationLayout.addComponent(passwordField);
        authorizationLayout.addComponent(confirmButton);

        authorizationLayout.setComponentAlignment(loginField, Alignment.TOP_CENTER);
        authorizationLayout.setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);
        authorizationLayout.setComponentAlignment(confirmButton, Alignment.BOTTOM_CENTER);
        authorizationLayout.setSpacing(true);
        setContent(gridLayout);
    }

    public void initRegistration () {
        VerticalLayout vl = new VerticalLayout();
        vl.setCaption("Панель регистрации");
        Button b = new Button("Кнопка");
        vl.addComponent(b); // добавление компонентов на панель
        vl.setComponentAlignment(b, Alignment.MIDDLE_CENTER); // расположение компонентов
//          ...
        setContent(vl);
    }

    public boolean autorise(String login, String password){
            System.out.println(login + " " + password);
        customer = serverConnector.loadCustomer(login, password);
            System.out.println(customer);
        if(customer != null){
            initApplication();
            return true;
        } else {
            customer = serverConnector.register(login, password, "test@mail.ru");
            initApplication();
            Notification.show("Неверный логин или пароль!", Notification.Type.WARNING_MESSAGE);
            return false;
        }
    }

    public void initApplication (){
//        ViewUtils.setUi(this);
//        ViewUtils.getComponentContainer().add(navigationTree);
        windowLayout = new VerticalLayout();
        windowLayout.setSizeFull();
        menuBar = new HeaderMenu(this);
//        menuBar.addMenuItem("Main");
//        menuBar.addMenuItem("Settings");
//        menuBar.addMenuItem("Network");
//        menuBar.addMenuItem("About");
//        menuBar.setSizeFull();
        bodyLayout = new BodyLayout();

        windowLayout.addComponent(menuBar);
        windowLayout.addComponent(bodyLayout);

        SideLayout sideLayout = new SideLayout(this, customer);
        DiaryView diaryView = new DiaryView();
        WorkTable workTable = new WorkTable(this, customer, diaryView);
        customer.getDiaries().get(0).addObserver(diaryView);
        customer.getDiaries().get(0).addObserver(sideLayout.getNavigationTree());

//        tableLayout.setRowExpandRatio(0, 0.1f);
//        tableLayout.setRowExpandRatio(1, 0.9f);
//        tableLayout.setRowExpandRatio(2, 0.1f);
//        tableLayout.setColumnExpandRatio(0, 0.1f);
//        tableLayout.setColumnExpandRatio(1, 1.1f);
//        tableLayout.setColumnExpandRatio(2, 0.1f);

//        DragAndDropWrapper dndDiary = new DragAndDropWrapper(diaryWrapper);
//        dndDiary.setSizeFull();
//        dndDiary.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
//        tableLayout.addComponent(diaryWrapper, 1, 0);
//        tableLayout.setComponentAlignment(diaryWrapper, Alignment.MIDDLE_CENTER);
//        diaryWrapper.setSizeFull();
//        DiaryView view = new DiaryView();
//        view.setSizeFull();

//        rightDraggingField.setSizeFull();
//        DragAndDropWrapper dndTable = new DragAndDropWrapper(tableGrid);
//        dndTable.setSizeFull();

//        dndTable.setDropHandler(new TableDropHandler());
        bodyLayout.setSizeFull();
        bodyLayout.setFirstComponent(sideLayout);
        bodyLayout.setSecondComponent(workTable);

        windowLayout.setExpandRatio(menuBar, 0.0f);
        windowLayout.setExpandRatio(bodyLayout, 0.1f);
        windowLayout.setComponentAlignment(menuBar, Alignment.TOP_CENTER);
        windowLayout.setComponentAlignment(bodyLayout, Alignment.MIDDLE_CENTER);

        ViewMediator viewMediator = new ViewMediatorImpl();
        viewMediator.setDiaryView(diaryView);
        viewMediator.setNavigationPanel(sideLayout.getNavigationTree());
        viewMediator.setHeaderMenu(menuBar);
        setContent(windowLayout);
        JavaScript.getCurrent().addFunction("myFunction",
                new JavaScriptFunction() {
                    @Override
                    public void call(JsonArray arguments) {
                        Notification
                                .show("JavaScript called me, so I show this message.");
                    }
                });


    }

    void expandCollapse() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.tree.expandlistener
        // Have a tree container with some unexpanded root items
        final HierarchicalContainer container =
                new HierarchicalContainer();
        container.addItem("One Node");
        container.addItem("Another Node");
        container.addItem("Third Node");

        // Bind the container to a tree
        final Tree tree = new Tree("My Tree", container);

        // When an item is expanded, add some children to it
        tree.addExpandListener(new Tree.ExpandListener() {
            private static final long serialVersionUID = -7752244479991850484L;

            int childCounter = 0;

            public void nodeExpand(Tree.ExpandEvent event) {
                // No children for the first node
                if ("One Node".equals(event.getItemId())) {
                    // A late modification
                    tree.setChildrenAllowed(event.getItemId(), false);

                    Notification.show("No children");
                } else {
                    // Add a few new child nodes to the expanded node
                    for (int i = 0; i < 3; i++) {
                        String childId = "Child " + (++childCounter);
                        tree.addItem(childId);
                        tree.setParent(childId, event.getItemId());
                    }

                    Notification.show("Added nodes");
                }
            }
        });
    }



    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WebAppUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public class TableDropHandler implements DropHandler{
        @Override
        public void drop(DragAndDropEvent event) {
            TargetDetails target = (TargetDetails)
                    event.getTargetDetails();
            ((Layout)target.getTarget()).removeAllComponents();
            Component drag = event.getTransferable().getSourceComponent();
            ((Layout)target.getTarget()).addComponent(drag);
        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }
    }

    public VerticalLayout getWindowLayout() {
        return windowLayout;
    }

    public void setWindowLayout(VerticalLayout windowLayout) {
        this.windowLayout = windowLayout;
    }

    public HeaderMenu getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(HeaderMenu menuBar) {
        this.menuBar = menuBar;
    }

    public BodyLayout getBodyLayout() {
        return bodyLayout;
    }

    public void setBodyLayout(BodyLayout bodyLayout) {
        this.bodyLayout = bodyLayout;
    }


    public TabSheet getSideLayout() {
        return sideLayout;
    }

    public void setSideLayout(TabSheet sideLayout) {
        this.sideLayout = sideLayout;
    }

    public NavigationTree getNavigationTree() {
        return navigationTree;
    }

    public void setNavigationTree(NavigationTree navigationTree) {
        this.navigationTree = navigationTree;
    }

    public ServerConnector getServerConnector() {
        return serverConnector;
    }

    public void setServerConnector(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

}
