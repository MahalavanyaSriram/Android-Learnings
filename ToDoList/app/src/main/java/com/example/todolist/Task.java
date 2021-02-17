package com.example.todolist;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
* a. Assignment : #HW02.
* b. File Name : Task (com.example.todolist).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class Task implements Serializable {
    String name;
    Date date;
    String priority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(date, task.date) &&
                Objects.equals(priority, task.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, priority);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }



}
