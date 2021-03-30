package com.example.todoapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {
    private TextInputLayout category_dropdown;
    private AutoCompleteTextView category_items;
    private TextInputEditText taskTitle;
    private Button datePicker;
    private Button update_task_btn;
    private int priority;
    private RadioGroup update_priority;
    private RadioButton radioButton;
    private Repository repository;
    Date todoDate;
    List<Task> taskList;
    ArrayList<String> categoryList;
    ArrayAdapter<String> adapterCategoryList;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#6E7568"));
        actionBar.setBackgroundDrawable(colorDrawable);
        category_dropdown=findViewById(R.id.category_dropdown);
        category_items=findViewById(R.id.category_items);
        datePicker=findViewById(R.id.update_date_picker);
        update_priority=findViewById(R.id.update_priority);
        int selectedId = update_priority.getCheckedRadioButtonId();
        radioButton=findViewById(selectedId);
        categoryList=new ArrayList<>();
        taskTitle=findViewById(R.id.task_title);
        update_task_btn=findViewById(R.id.update_btn);
        update_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTasks();
            }
        });
        repository = Repository.getRepository(this.getApplication());
        addCategory();
    }
    public void updateTasks(){
        Task task=taskList.get(position);
        String task_title=taskTitle.getText().toString();
        Date updated_date=new Date();
        /*
        if(radioButton.getText().toString().equals("low"))
            priority=1;
        if(radioButton.getText().toString().equals("medium"))
            priority=2;
        if(radioButton.getText().toString().equals("high"))
            priority=3;

         */
        priority=1;

        task.setTitle(task_title);
        task.setUpdatedDate(updated_date);
        task.setPriority(priority);
        repository.update(task);
    }
    private void setItems() {
        taskTitle.setText(taskList.get(position).getTitle());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        datePicker.setText(format.format(taskList.get(position).getCreatedDate()));
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
        switch (taskList.get(position).getPriority()){
            case 1:
                update_priority.check(R.id.low_priority);
                break;
            case 2:
                update_priority.check(R.id.medium_priority);
                break;
            case 3:
                update_priority.check(R.id.high_priority);
                break;
        }
    }
    void pickDate() {
        Calendar calendar = Calendar.getInstance();
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //datePicker.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                calendar.set(year,month,dayOfMonth);
                datePicker.setText(year + "-" + month + "-" + dayOfMonth);
            }
        },cYear,cMonth,cDay);
        datePickerDialog.show();
    }

    private void addCategory(){
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
    private void checkCategoryExistence(String category){
       for(int i=0;i<categoryList.size();i++){
           if(categoryList.get(i).equals(category)){
               categoryList.remove(i);
           }
       }

    }
}