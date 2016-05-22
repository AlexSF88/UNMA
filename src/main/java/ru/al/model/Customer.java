package ru.al.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Alex and Lora on 04.11.2015.
 */
@XmlRootElement
@Entity
public class Customer {

//    @NotNull
//    @Min(1)

    @GeneratedValue
    @Id
    private int id;

    public int getId() {
        return id;
    }

    private String firstName;
    private String lastName;
    private String middleName;
    private boolean isFemale;
//    @NotNull
//    @Size(min=1)
//    @Id
//    @Column(length = 20, nullable = false, unique = false)
    private String login;
//    @NotNull
//    @Size(min=5)
    private String password;
    @XmlTransient
    @Transient
    private String avatar;
//    @Transient
    @OneToMany(mappedBy = "owner", cascade=CascadeType.ALL)
    private List<Diary> diaries;
    @XmlTransient
    @Transient
    private Diary selectedNB;
    @XmlTransient
    @Transient
    private Task selectedTask;

    private LocalDateTime registrationDate;

    private LocalDateTime lastVisit;

    private long visitCount;

    private String phoneNumber;
//    @Email

    private String email;

    public Customer() {
    }

//    public Customer(String login, String password, String email) {
////        this.id = id;
//        this.password = password;
//        this.login = login;
//        this.email = email;
//        diaries = new ArrayList <Diary>();
//        registrationDate = LocalDateTime.now();
//    }

    public Customer(String login, String password, String email) {
        this.password = password;
        this.login = login;
        this.email = email;
        diaries = new ArrayList <Diary>();
        registrationDate = LocalDateTime.now();
    }

    public long getUID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setIsFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Diary> getDiaries() {
        return diaries;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = LocalDateTime.now();;
    }

    public long getVisitCount() {
        return visitCount;
    }

    public void incVisitCount(long visitCount) {
        this.visitCount++;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Diary getSelectedNB() {
        return selectedNB;
    }

    public void setSelectedNB(Diary selectedNB) {
        this.selectedNB = selectedNB;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public void setDiaries(ArrayList<Diary> diaries) {
        this.diaries = diaries;
    }

    public void setVisitCount(long visitCount) {
        this.visitCount = visitCount + 1;
    }

    @Override
    public String toString() {
        return login;
    }

    private class CustomerBuilder {
        private String firstName;
        private String lastName;
        private String middleName;
        private boolean isFemale;
        private String login;
        private String password;
        private String avatar;
        private List<Diary> diaries;

        private LocalDateTime registrationDate;
        private String phoneNumber;
        private String email;

        public CustomerBuilder(String login, String password, String email){
            this.password = password;
            this.login = login;
            this.email = email;
            diaries = new ArrayList <Diary>();
            registrationDate = LocalDateTime.now();
        }

        public CustomerBuilder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder middleName(String middleName){
            this.middleName = middleName;
            return this;
        }

        public CustomerBuilder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Customer build(){
            return new Customer(this);
        }
    }

    public Customer (CustomerBuilder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.isFemale = builder.isFemale;
        this.login = builder.login;
        this.password = builder.password;
        this.avatar = builder.avatar;
        this.diaries = builder.diaries;

        this.registrationDate = builder.registrationDate;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
    }

    public enum CUSTOMER_ENUM {
        CUSTOMER;
    }

}
