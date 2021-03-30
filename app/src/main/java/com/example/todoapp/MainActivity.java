package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.data.AppDatabase;
import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.example.todoapp.data.TodoDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.addTaskBtn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet= BottomSheet.newInstance();
                bottomSheet.show(getSupportFragmentManager(), BottomSheet.TAG);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, HomeFragment.class, null)
                .commit();
    }
    /*
    @Override
    public void onItemClicked(int index, String btnStatus) {
        if(btnStatus.equals("delete")){
            repository.delete(taskList.get(index));
        }
        if(btnStatus.equals("edit")){
            Intent intent=new Intent(MainActivity.this,EditTaskActivity.class);
            intent.putExtra("Arraylist", (Serializable) taskList);
            intent.putExtra("position",index);
            startActivity(intent);
        }
    }

     */
}

