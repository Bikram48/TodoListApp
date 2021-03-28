package com.example.todoapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todolist")
public class TodoModel {
    @PrimaryKey
    private int id;
    private String taskTitle;
    private String description;
    private String category;
    @ColumnInfo(name = "created_date")
    private Date createdDate;
    @ColumnInfo(name = "updated_date")
    private Date updatedDate;
    private int priority;

    public TodoModel(String taskTitle, String description, String category, Date createdDate, Date updatedDate, int priority) {
        this.taskTitle = taskTitle;
        this.description = description;
        this.category=category;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.priority = priority;
    }
    @Ignore
    public TodoModel(int id, String taskTitle, String description, String category, Date createdDate, Date updatedDate, int priority) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.description = description;
        this.category=category;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getTaskDate() {
        return createdDate;
    }

    public void setTaskDate(Date taskDate) {
        this.createdDate = taskDate;
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
}
