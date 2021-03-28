package com.example.todoapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("select * from todos order by priority")
    LiveData<List<Task>> getAllTasks();

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Query("delete from todos")
    void deleteAll();


}
