package com.example.todoapp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "todos")
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String title;
    private String category;
    @ColumnInfo(name = "created_date")
    private Date createdDate;
    private String taskReminder;
    @ColumnInfo(name = "updated_date")
    private Date updatedDate;
    private int priority;
    @Ignore
    public Task(){

    }
/*
    @Ignore
    public Task(Long id, String title, String category, Date createdDate, Date updatedDate,String taskReminder, int priority) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.taskReminder=taskReminder;
        this.priority = priority;
    }
*/



    public Task(String title, String category, Date createdDate, Date updatedDate, String taskReminder, int priority) {
        this.title = title;
        this.category = category;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.taskReminder=taskReminder;
        this.priority = priority;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public String getTaskReminder() {
        return taskReminder;
    }
    public void setTaskReminder(String taskReminder) {
        this.taskReminder = taskReminder;
    }

}
