package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.todoapp.data.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {
    private TextInputLayout category_dropdown;
    private AutoCompleteTextView category_items;
    private TextInputEditText taskTitle;
    List<Task> taskList;
    ArrayList<String> categoryList;
    ArrayAdapter<String> adapterCategoryList;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        category_dropdown=findViewById(R.id.category_dropdown);
        category_items=findViewById(R.id.category_items);
        categoryList=new ArrayList<>();
        taskTitle=findViewById(R.id.task_title);
        categoryList.add("study");
        categoryList.add("sport");
        categoryList.add("work");
        categoryList.add("friends");
        categoryList.add("movies");
        categoryList.add("coding");
        Intent intent=getIntent();
        taskList= (List<Task>) intent.getSerializableExtra("Arraylist");
        position=intent.getIntExtra("position",0);
        checkCategoryExistence(taskList.get(position).getCategory().toLowerCase());
        adapterCategoryList=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item,categoryList);
        category_items.setAdapter(adapterCategoryList);
        category_items.setText(taskList.get(position).getCategory().toString(),false);
        setItems();
    }

    private void setItems() {
        taskTitle.setText(taskList.get(position).getTitle());
    }

    private void checkCategoryExistence(String category){
       for(int i=0;i<categoryList.size();i++){
           if(categoryList.get(i).equals(category)){
               categoryList.remove(i);
           }
       }

    }
}