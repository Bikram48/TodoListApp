package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    public static String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>HAMRO TO-DO</font>"));
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
        Intent intent=getIntent();
        String message=intent.getStringExtra("insertmessage");
        if(message!=null) {
            Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
            snackBar.show();
        }
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
                fragment=new AboutApplication();
                break;
        }
        return loadFragment(fragment);
    }
}

