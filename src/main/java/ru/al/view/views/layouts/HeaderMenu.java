package ru.al.view.views.layouts;

import com.vaadin.data.Item;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.HorizontalLayout;

import com.vaadin.ui.JavaScript;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import ru.al.WebAppUI;
import ru.al.controller.IHeaderMenu;
import ru.al.controller.ViewMediator;
import ru.al.model.Customer;
import ru.al.model.Note;
import sun.java2d.loops.CustomComponent;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Objects;

/**
 * Created by Alex on 07.11.2015.
 */
public class HeaderMenu extends HorizontalLayout implements IHeaderMenu{

    private WebAppUI ui;
    private ViewMediator viewMediator;

    private final MenuBar.Command mycommand = new MenuBar.Command() {
        @Override
        public void menuSelected(final MenuBar.MenuItem selectedItem) {
            System.out.println("Saving...");
            System.out.println(selectedItem.toString());
            System.out.println(selectedItem.getText());
            if(Objects.equals(selectedItem.getText(), "Save data")) {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNMA");
            EntityManager em = emf.createEntityManager();
// Обеспечивает постоянство в базе данных
            EntityTransaction tx = em.getTransaction();
            tx.begin();
//            ui.getCustomer().getDiaries().add(new Diary("TestMain", ui.getCustomer()));
            em.merge(ui.getCustomer());
            Customer c = em.find(Customer.class, 1);
//            em.persist(ui.getCustomer());
            tx.commit();
            try {


                StringWriter writer = new StringWriter();
                JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
                Marshaller m = jaxbContext.createMarshaller();
                m.marshal(c, writer);
                System.out.println("WRITER: \n" + writer);
                jaxbContext = JAXBContext.newInstance(Note.class);
                m = jaxbContext.createMarshaller();
                m.marshal(c.getDiaries().get(0).getNotes().get(0), writer);
                m.marshal(c, writer);
                System.out.println("WRITER: \n" + writer);
            } catch (Exception e){
                e.printStackTrace();
            }


//            System.out.println(em.find(Customer.class, ui.getCustomer().getId()));
// Закрывает менеджер сущностей и фабрику

                System.out.println("Данные успешно сохранены!");
                ui.getServerConnector().saveData();
                Notification.show("Данные успешно сохранены: " + c
                                + "\n" + c.getClass()
                                + "\n" + c.getEmail()
                                + "\n" + c.getPhoneNumber()
                                + "\n" + c.getId()
                                + "\n" + c.getPassword(),
                        Notification.Type.TRAY_NOTIFICATION);
            em.close();
            emf.close();

            }

            if(Objects.equals(selectedItem.getText(), "Community")) {
                try {
                    Context context = new InitialContext();

                } catch (NamingException e) {
                    e.printStackTrace();
                }
                JavaScript.getCurrent().execute("alert('JavaScript Welcome U from the server side!')");

            } else if (Objects.equals(selectedItem.getText(), "Hot")) {
                JavaScript.getCurrent().execute("window.myFunction()");

            } else if (Objects.equals(selectedItem.getText(), "Create")) {
                JavaScript.getCurrent().execute("function close_window() {\n" +
                        "                if (confirm(\"Работа программы завершена. Закрыть окно?\")) {\n" +
                        "                    window.close();\n" +
                        "                }\n" +
                        "            }\n" +
                        "            var a = prompt (\"Введите значение от 0 до 10:\");\n" +
                        "            while (i<10) {\n" +
                        "                alert (a++);\n" +
                        "            }\n" +
                        "            close_window();");

            } else if (Objects.equals(selectedItem.getText(), "Connection")) {
                JavaScript.getCurrent().execute("function close_window() {\n" +
                        "                if (confirm(\"Работа программы завершена. Закрыть окно?\")) {\n" +
                        "                    window.close();\n" +
                        "                }\n" +
                        "            }\n" +
                        "            var a = prompt (\"Введите значение от 0 до 10:\");\n" +
                        "            while (i<10) {\n" +
                        "                alert (a++);\n" +
                        "            }\n" +
                        "            ;");

            }


        }
    };


    public HeaderMenu(WebAppUI ui) {
        this.ui = ui;
        ui.getServerConnector();
        setWidth(100, Unit.PERCENTAGE);
        MenuBar barmenu = new MenuBar();
        barmenu.setWidth(100, Unit.PERCENTAGE);
        addComponent(barmenu);

        // A feedback component

        // A top-level menu item that opens a submenu
        MenuBar.MenuItem drinks = barmenu.addItem("Menu", null, null);

        // Submenu item with a sub-submenu
        MenuBar.MenuItem hots = drinks.addItem("Hot", null, mycommand);
        hots.addItem("Tea",
                new ThemeResource("icons/tea-16px.png"), mycommand);
        hots.addItem("Coffee",
                new ThemeResource("icons/coffee-16px.png"), mycommand);

        // Another submenu item with a sub-submenu
        MenuBar.MenuItem create = drinks.addItem("Create", null, null);
        create.addItem("Test button 1", null, mycommand);
        create.addItem("Test button 2", null, mycommand);
        create.setDescription("Ich mag es");

// A sub-menu item after a separator
        drinks.addSeparator();
        MenuBar.MenuItem save = drinks.addItem("Save data", null, mycommand);

        // Another top-level item
        MenuBar.MenuItem network = barmenu.addItem("Network", null, null);
        network.addItem("Community", null, mycommand);
        network.addItem("Connection", null, mycommand);

        // Yet another top-level item
        MenuBar.MenuItem help = barmenu.addItem("Help", null, null);
        help.addItem("Service desk", null, mycommand);
        help.addItem("About", null, mycommand);
    }

    @Override
    public void setViewMediator(ViewMediator viewMediator) {
        this.viewMediator = viewMediator;
    }
}