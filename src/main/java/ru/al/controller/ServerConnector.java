package ru.al.controller;


import ru.al.model.Customer;

/**
 * Created by Alex on 08.11.2015.
 */
public interface ServerConnector {
    public Customer loadUser(String login, String password);

    Customer register(String login, String password, String email);

    Customer loadCustomer(String login, String password);

    public Customer createUser(String login, String password, String email);
    public void saveData();
}
