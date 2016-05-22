package ru.al.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Alex on 12.12.2015.
 */
@Entity
public class Project {
    @Id
    @GeneratedValue
    private long id;
    @ManyToMany
    private List<Task> tasks;
    @ManyToMany
    private List<Customer> customers;
}
