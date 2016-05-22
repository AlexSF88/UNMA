package ru.al.model;

import ru.al.controller.*;
import ru.al.controller.Observable;
import ru.al.controller.Observer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by Alex and Lora on 04.11.2015.
 */
@XmlRootElement
@Entity
public class Note implements Observable {
//    @PropertyId("Title")
    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String title = "Title";
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdate;
    private LocalDate storyDate;
    @XmlTransient
    @ManyToOne (cascade=CascadeType.ALL)
    private Customer author;
//    @PropertyId("Text")
    private String text = "";
    @XmlTransient
    @Transient
    private Files attachment;
    @OneToMany(mappedBy = "parent", cascade=CascadeType.ALL)
    private List<Note> notes;
    private Boolean active = true;
    private int accomplishedSubTask;
    private int activeSubTask;
    @XmlTransient
    @ManyToOne(cascade=CascadeType.ALL)
    private Note parent;
    private int progress = 0;
    @XmlTransient
    @Transient
    private HashSet<Observer> observers = new HashSet<>();


    public Note() {
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Note(String title, Customer author) {
        this.title = title;
        this.author = author;
        this.creationDate = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
        this.notes = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        setLastUpdate();
        update();
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
        setLastUpdate();
        update();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        setLastUpdate();
        update();
    }

    public Files getAttachment() {
        return attachment;
    }

    public void setAttachment(Files attachment) {
        this.attachment = attachment;
        setLastUpdate();
    }

    public LocalDate getStoryDate() {
        return storyDate;
    }

    public void setStoryDate(LocalDate storyDate) {
        this.storyDate = storyDate;
        setLastUpdate();
        update();
    }

    public void setStoryDate(Date storyDate) {
        Instant instant = Instant.ofEpochMilli(storyDate.getTime());
        this.storyDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        setLastUpdate();
        update();
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getActiveSubTask() {
        return activeSubTask;
    }

    public void setActiveSubTask(int activeSubTask) {
        this.activeSubTask = activeSubTask;
    }

    public int getAccomplishedSubTask() {
        return accomplishedSubTask;
    }

    public void setAccomplishedSubTask(int accomplishedSubTask) {
        this.accomplishedSubTask = accomplishedSubTask;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void updateStatus() {
        activeSubTask = 0;
        accomplishedSubTask = 0;
        if(notes == null || notes.size()==0) {
            return;
        }
        for (Note note : notes) {
            note.updateStatus();
            if(note.active == null){
                return;
            } else if(note.active){
                activeSubTask++;
            } else {
                accomplishedSubTask++;
            }
        }
    }

    public int getStatus() {
        updateStatus();
        return (accomplishedSubTask*activeSubTask/100);
    }

    @Override
    public String toString() {
        return title;
    }
    @XmlTransient
    public Note getParent() {
        return parent;
    }

    public void setParent(Note parent) {
        if(this.parent != null) {
            this.parent.getNotes().remove(this);
        }
        this.parent = parent;
        update();
    }

    public void addChild(Note child) {
        getNotes().add(child);
        System.out.println(this + " " + getNotes());
        child.setParent(this);
        System.out.println("child=" + child + " parent=" + child.getParent());
        child.addAllObservers(observers);
        update();
    }

    public void removeChild(Note child) {
        System.out.println("removing..." + child + " from " + parent);
        getNotes().remove(child);
//        child.setParent(null);
        update();
    }

    public void removeAll() {
        if (parent!=null) {
        System.out.println("parent="+parent);
            parent.removeChild(this);
            update();
        }
    }

    public void remove() {
        ArrayList<Note> notes = new ArrayList<>(getNotes());
        Collections.reverse(notes);
        for (Note note : notes) {
            System.out.println("this=" + this + " this parent=" + parent);
            System.out.println("note=" + note + " parent=" + note.getParent());
            parent.addChild(note);
            System.out.println("note=" + note + " parent=" + note.getParent());
        }
        getParent().removeChild(this);
        update();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if(progress<100){
            this.progress = progress;
        } else {
            this.progress = 100;
            active = false;
        }
        update();
    }

    public void moveAfter(Note note) {
        Note parent = note.getParent();
        LinkedList<Note> list = new LinkedList<>(parent.getNotes());
        System.out.println(parent.getNotes());
        System.out.println(list);
        int newPos = list.indexOf(note);
        System.out.println("Parent = " + parent + " this = " + this + " index = " + list.indexOf(this));
        if(list.indexOf(this) != -1){
            if (list.indexOf(this) > newPos){
                newPos++;
            }
            list.remove(this);

        }
        System.out.println("Parent = " + parent + " this = " + this + " index = " + list.indexOf(this));
        setParent(parent);
        list.add(newPos, this);

        System.out.println(parent.getNotes());
        System.out.println(list);
        parent.getNotes().clear();
        parent.getNotes().addAll(list);
        System.out.println(parent.getNotes());
        System.out.println(list);
//        note.getParent().addChild(this);
        update();
    }

    public void moveBefore(Note note) {
        Note parent = note.getParent();
        LinkedList<Note> list = new LinkedList<>(parent.getNotes());
        System.out.println(parent.getNotes());
        System.out.println(list);
        int newPos = list.indexOf(note);
        System.out.println("Parent = " + parent + " this = " + this + " index = " + list.indexOf(this));
        if(list.indexOf(this) != -1){
            if (list.indexOf(this) < newPos){
                newPos--;
            }
            list.remove(this);

        }
        System.out.println("Parent = " + parent + " this = " + this + " index = " + list.indexOf(this));
        setParent(parent);
        list.add(newPos, this);

        System.out.println(parent.getNotes());
        System.out.println(list);
        parent.getNotes().clear();
        parent.getNotes().addAll(list);
        System.out.println(parent.getNotes());
        System.out.println(list);
//        note.getParent().addChild(this);
        update();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        for (Note note : getNotes()) {
            note.addObserver(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void update() {
        System.out.println("Updating "+observers);
        for (Observer observer : observers) {
            observer.update(parent == null ? this : parent);
            System.out.println("Updating "+observer.toString());
        }
    }

    @Override
    public void addAllObservers(Collection<Observer> observers) {
        this.observers.addAll(observers);
    }

}
