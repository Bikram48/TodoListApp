package com.example.todoapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapp.Models.Task;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("select * from todos")
    LiveData<List<Task>> getAllTasks();
    @Query("select * from todos order by priority desc")
    LiveData<List<Task>> getPriorityByHigh();
    @Query("select * from todos order by priority")
    LiveData<List<Task>> getPriorityByLow();
    @Query("select * from todos order by created_date")
    LiveData<List<Task>> getTaskByDate();
    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Query("delete from todos")
    void deleteAll();

}
