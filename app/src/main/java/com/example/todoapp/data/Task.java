package com.example.todoapp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todos")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String title;
    private String description;
    @ColumnInfo(name = "created_date")
    private Date createdDate;
    @ColumnInfo(name = "updated_date")
    private Date updatedDate;
    private int priority;


    @Ignore
    public Task(Long id, String title, String description, Date createdDate, Date updatedDate, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.priority = priority;
    }

    public Task(String title, String description, Date createdDate, Date updatedDate, int priority) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
