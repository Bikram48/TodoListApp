package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.data.AppDatabase;
import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.example.todoapp.data.TodoDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    public static String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
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
        loadFragment(new HomeFragment());
        bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.calendarMenu:
                Toast.makeText(this, "Calendar menu is selected", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onOptionsItemSelected: calendar");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()){
            case R.id.homeMenu:
                fragment=new HomeFragment();
                break;
            case R.id.calendarMenu:
                fragment=new TaskSchedule();
                break;
        }
        return loadFragment(fragment);
    }
}

