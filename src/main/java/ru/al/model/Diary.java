package ru.al.model;

import ru.al.controller.Observer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;

/**
 * Created by Lora amd Alex on 04.11.2015.
 */
@XmlRootElement
@Entity
public class Diary extends Note implements Comparable{
    @Id
    @GeneratedValue
    private long id;

    public Diary() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @XmlTransient
    @ManyToOne(cascade=CascadeType.ALL)
//    @JoinColumn()
    private Customer owner;
    private LocalDateTime creationDate;
//    private ArrayList<Note> notes;
    private boolean isFavourite;

    public Diary(String title, Customer owner) {
        super(title, owner);
        this.creationDate = LocalDateTime.now();
        this.owner = owner;
//        notes = new ArrayList <>();
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

//    public ArrayList<Note> getNotes() {
//        return notes;
//    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Customer getOwner() {
        return owner;
    }

    @Override
    public void remove() {
        removeAll();
    }

    @Override
    public void removeAll() {
        owner.getDiaries().remove(this);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
