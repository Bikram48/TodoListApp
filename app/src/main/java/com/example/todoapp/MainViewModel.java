package com.example.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.example.todoapp.data.TodoDao;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;
    LiveData<List<Task>> taskList;
    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
        taskList = repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskList;
    }
}
