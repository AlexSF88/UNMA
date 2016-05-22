package ru.al.model;


import ru.al.controller.ServerConnector;

import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alex on 08.11.2015.
 */
public class ServiceManager implements ServerConnector {
    private HashMap<Integer, Customer> users;
    private Customer customer;
    EntityManagerFactory factory;
    EntityManager entityManager;
    private static final Logger LOGGER = Logger.getLogger("JPA");

    public ServiceManager() {
        this.users = new HashMap<Integer, Customer>();
    }

    @Override
    public Customer loadUser(String login, String password) {
        if (customer == null || login.equals(customer.getLogin())) {
            System.out.println(login + " " + password);
            Set<Map.Entry<Integer, Customer>> entrySet = users.entrySet();
            System.out.println("1 "+entrySet);
            for (Map.Entry<Integer, Customer> entry : entrySet) {
                Customer tmpCustomer = entry.getValue();
                System.out.println("2 "+ tmpCustomer);
                if (tmpCustomer.getLogin().equals(login)) {
                    customer = tmpCustomer;
                    if (customer.getPassword().equals(password)){
                        return customer;
                    }
                }
            }
        }
        return null;
    }

    public Customer createCustomer(String login, String password,  String email){
       return new Customer(login, password, email);
    }

    @Override
    public Customer register(String login, String password,  String email) {
        try {
           factory = Persistence.createEntityManagerFactory("UNMA");
            entityManager = factory.createEntityManager();
            customer = createCustomer(login, password, email);
            Diary diary = new Diary("Мои проекты", customer);
            diary.addChild(new Note("Добро пожаловать!", customer));
            customer.getDiaries().add(diary);
            System.out.println("Service manager created: "+customer);
            persistCustomer(customer);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
        return customer;
    }

    private void persistCustomer(Customer newCustomer) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(newCustomer);
            System.out.println("Persisted: " + newCustomer + " " + newCustomer.getId());
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    @Override
    public Customer loadCustomer(String login, String password) {
        if(factory == null)
        factory = Persistence.createEntityManagerFactory("UNMA");
        if(entityManager == null)
        entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        customer = null;
//        TypedQuery<Customer> query = entityManager.createQuery("from Customer", Customer.class);
        try {
            transaction.begin();
            customer = entityManager.find(Customer.class, 1);
            System.out.println("Loaded customer: " + customer);
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
            Marshaller m = jaxbContext.createMarshaller();
            m.marshal(customer, writer);
            System.out.println("WRITER: \n" + writer);
            jaxbContext = JAXBContext.newInstance(Note.class);
            m = jaxbContext.createMarshaller();
            m.marshal(customer.getDiaries().get(0), writer);
            System.out.println("WRITER: \n" + writer);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return customer;
    }

    @Override
    public Customer createUser(String login, String password,  String email) {
        Random random = new Random();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNMA");

            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

        customer = em.find(Customer.class, login);
        tx.commit();
        if (!Objects.equals(customer.getPassword(), password)){
            return null;
        }
        int id = random.nextInt() * random.nextInt();
        if (customer == null) {

            while (users.containsKey(id)) {
                id = random.nextInt() * random.nextInt();
            }
            customer = new Customer(login, password, email);
            tx.begin();
            em.persist(customer);
            tx.commit();
        } else {
            id= customer.getId();
            customer.setDiaries(new ArrayList<>());
        }


        Diary nb = new Diary("Проект 1", customer);
        Diary nb2 = new Diary("Проект 2", customer);
        Diary nb3 = new Diary("Проект 3", customer);
        nb3.addChild(new Note("Задача 1", customer));
        customer.getDiaries().add(nb2);
        customer.getDiaries().add(nb3);
        nb2.addChild(new Note("Задача 1!", customer));
        nb.addChild(new Note("Задача 1!", customer));
        nb.addChild(new Note("Задача 2!", customer));
        nb.addChild(new Note("Задача 3", customer));
        nb.addChild(new Note("Задача 4", customer));
        nb.getNotes().get(0).addChild(new Note("Задача 1.1", customer));
        nb.getNotes().get(0).addChild(new Note("Задача 1.2", customer));
        nb.getNotes().get(0).getNotes().get(1).addChild(new Note("Задача 1.2.1", customer));
        nb.getNotes().get(0).getNotes().get(1).addChild(new Note("Задача 1.2.2", customer));
        nb.getNotes().get(0).getNotes().get(1).getNotes().get(1).addChild(new Note("Задача 1.2.2.1", customer));
        nb.getNotes().get(0).getNotes().get(1).getNotes().get(1).addChild(new Note("Задача 1.2.2.2", customer));
        nb.getNotes().get(0).getNotes().get(1).getNotes().get(1).getNotes().get(1).addChild(new Note("Задача 1.2.2.2.1", customer));
        nb.getNotes().get(0).addChild(new Note("Задача 1.3", customer));
        customer.getDiaries().add(nb);

        customer.setSelectedNB(nb);
        System.out.println(customer);
        users.put(id, customer);


// Обеспечивает постоянство в базе данных

//        System.out.println(em.contains(customer));
//        System.out.println(em.contains(customer.getId()));

//        tx.commit();
// Закрывает менеджер сущностей и фабрику
//        System.out.println(em.find(Customer.class, customer));
//tx.begin();
//        System.out.println("Cust=" + em.find(Customer.class, customer.getId()));System.out.println(em.contains(customer));
//        System.out.println(customer.getId());
//        tx.commit();
        em.close();
        emf.close();

        return customer;
    }

    @Override
    public void saveData() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNMA");
//        EntityManager em = emf.createEntityManager();
//// Обеспечивает постоянство в базе данных
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        em.persist(customer);
//        tx.commit();
//// Закрывает менеджер сущностей и фабрику
//        em.close();
//        emf.close();
    }
}
