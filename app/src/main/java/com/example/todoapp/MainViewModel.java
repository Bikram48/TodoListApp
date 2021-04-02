package com.example.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapp.data.Repository;
import com.example.todoapp.Models.Task;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;
    LiveData<List<Task>> taskList;
    LiveData<List<Task>> priorityFromHigh;
    LiveData<List<Task>> priorityFromLow;
    LiveData<List<Task>> dateSortedList;
    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
        taskList = repository.getAllTasks();
        priorityFromHigh=repository.getPriorityByHigh();
        priorityFromLow=repository.getPriorityByLow();
        dateSortedList=repository.getTaskByDate();
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskList;
    }
    public LiveData<List<Task>> getPriorityByHigh(){return priorityFromHigh;}
    public LiveData<List<Task>> getPriorityByLow(){return priorityFromLow;}
    public LiveData<List<Task>> getTaskByDate(){return dateSortedList;}
}
