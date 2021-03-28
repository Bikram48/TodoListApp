package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.todoapp.data.AppDatabase;
import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.example.todoapp.data.TodoDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;


    //private List<Task> taskList;
    private TaskAdapter adapter;
    private FloatingActionButton fab;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.addTaskBtn);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //repository = Repository.getRepository(this.getApplication());
        viewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    adapter.setData(tasks);

                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet= BottomSheet.newInstance();
                bottomSheet.show(getSupportFragmentManager(), BottomSheet.TAG);
                /*
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);

                 */
            }
        });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        taskList = viewModel.getAllTasks();
//        adapter.setDate(taskList);
//
//    }
}