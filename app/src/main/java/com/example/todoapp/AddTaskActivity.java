package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descEditText;
    private Repository repository;
    private Button submitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        titleEditText = findViewById(R.id.title_et);
        descEditText = findViewById(R.id.desc_dt);
        submitButton = findViewById(R.id.submit_btn);
        repository = Repository.getRepository(this.getApplication());


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String desc = descEditText.getText().toString();

                Task task = new Task(title, desc,  new Date(), new Date(), 1);
                repository.addTask(task);
                finish();
            }
        });






    }
}