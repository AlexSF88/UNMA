package ru.al.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex and Lora on 04.11.2015.
 */
@Entity
public class Task extends Note implements Comparable{
    @Id
    @GeneratedValue
    private long id;
    private boolean isAccomplished;
    private boolean isOpen;
//    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
//    private List<Note> notes;
    private LocalDateTime doBefore;
    private LocalDateTime closeDate;
    private int priority;

    public Task() {
    }

    public boolean isAccomplished() {
        return isAccomplished;
    }

    public void setIsAccomplished(boolean isAccomplished) {
        this.isAccomplished = isAccomplished;
    }

    public boolean isOpen() {
        return isOpen;
    }


    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

//    public void setNotes(ArrayList<Note> notes) {
//        this.notes = notes;
//    }

    public LocalDateTime getDoBefore() {
        return doBefore;
    }

    public void setDoBefore(LocalDateTime doBefore) {
        this.doBefore = doBefore;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Task(String title, Customer author) {
        super(title, author);
//        this.notes = new ArrayList<>();
        this.setStoryDate(LocalDate.now());
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
