package com.lungesoft.architecture.core.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Calendar creatingDate;

    public Project() {
    }

    public Project(String title) {
        this.title = title;
        this.creatingDate = Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Calendar creatingDate) {
        this.creatingDate = creatingDate;
    }

}