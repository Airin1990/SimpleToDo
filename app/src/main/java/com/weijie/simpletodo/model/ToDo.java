package com.weijie.simpletodo.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by weiji_000 on 2/7/2017.
 */

public class ToDo implements Serializable {
    private String title;
    private Date dueDate;
    private String priority;
    private String notes;
    private String status;

    public ToDo(String title, Date dueDate, String priority, String notes, String status) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.notes = notes;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
