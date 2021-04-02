package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.example.todoapp.data.Repository;
import com.example.todoapp.Models.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity {
    public static final int HIGH_PRIORITY = 3;
    public static final int MEDIUM_PRIORITY = 2;
    public static final int LOW_PRIORITY = 1;
    private TextInputLayout category_dropdown;
    private AutoCompleteTextView category_items;
    private TextInputEditText taskTitle;
    private Button datePicker;
    private Button update_task_btn;
    private Button cancelBtn;
    private int priority;
    private RadioGroup update_priority;
    private Repository repository;
    private String selected_category;
    Task task;
    View view;
    ArrayList<String> categoryList;
    ArrayAdapter<String> adapterCategoryList;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        category_dropdown=findViewById(R.id.category_dropdown);
        category_items=findViewById(R.id.category_items);
        datePicker=findViewById(R.id.update_date_picker);
        update_priority=findViewById(R.id.update_priority);
        categoryList=new ArrayList<>();
        taskTitle=findViewById(R.id.task_title);
        view=findViewById(android.R.id.content);
        update_task_btn=findViewById(R.id.update_btn);
        update_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTasks();
            }
        });
        cancelBtn=findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCancelMessage();
            }
        });
        repository = Repository.getRepository(this.getApplication());
        addCategory();
    }

    private void displayCancelMessage() {
        AlertDialog.Builder mAlterDialog = new AlertDialog.Builder(this);
        mAlterDialog.setMessage(getString(R.string.edit_cancel_promt))
                .setCancelable(false)
                .setTitle(getString(R.string.app_name))
                .setIcon(R.drawable.cancel);
        mAlterDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });
        mAlterDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mAlterDialog.show();
    }

    private void addCategory(){
        categoryList.add(getString(R.string.study_category));
        categoryList.add(getString(R.string.sport_category));
        categoryList.add(getString(R.string.work_category));
        categoryList.add(getString(R.string.friends_category));
        categoryList.add(getString(R.string.movies_category));
        categoryList.add(getString(R.string.coding_category));
        Intent intent=getIntent();
        task= (Task) intent.getSerializableExtra(HomeFragment.intent_data);
        position=intent.getIntExtra("position",0);
        checkCategoryExistence(task.getCategory().toLowerCase());
        adapterCategoryList=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item,categoryList);
        category_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_category=adapterCategoryList.getItem(position).toString();
            }
        });
        category_items.setAdapter(adapterCategoryList);
        category_items.setText(task.getCategory().toString(),false);
        setItems();
    }
    private void checkCategoryExistence(String category){
        for(int i=0;i<categoryList.size();i++){
            if(categoryList.get(i).equals(category)){
                categoryList.remove(i);
            }
        }
    }
    public void updateTasks(){
        if(taskTitle.getText().toString().equals("")){
            Snackbar snackBar = Snackbar.make(view,
                    "Please input task!!", Snackbar.LENGTH_LONG);
            snackBar.show();
        }
        else if(update_priority.getCheckedRadioButtonId()==-1){
            Snackbar snackBar = Snackbar.make(view,
                    "Please set the priority!!", Snackbar.LENGTH_LONG);
            snackBar.show();
        }
        else if(datePicker.getText().toString().isEmpty()){
            Snackbar snackBar = Snackbar.make(view,
                    "Please pick the date!!", Snackbar.LENGTH_LONG);
            snackBar.show();
        }
        else {
            String task_title = taskTitle.getText().toString();
            int selectedId = -1;
            selectedId = update_priority.getCheckedRadioButtonId();
            Date updated_date = new Date();

            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                updated_date = format.parse(datePicker.getText().toString());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            switch (selectedId) {
                case R.id.low_priority:
                    priority = LOW_PRIORITY;
                    break;
                case R.id.medium_priority:
                    priority = MEDIUM_PRIORITY;
                    break;
                case R.id.high_priority:
                    priority = HIGH_PRIORITY;
                    break;
            }

            task.setTitle(task_title);
            task.setUpdatedDate(updated_date);
            task.setPriority(priority);
            if (selected_category != null) {
                task.setCategory(selected_category);
            }
            repository.update(task);
            Intent intent=new Intent();
            intent.putExtra("message","Task is updated successfully");
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    private void setItems() {
        taskTitle.setText(task.getTitle());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        datePicker.setText(format.format(task.getCreatedDate()));
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        switch (task.getPriority()){
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
                if(month>0){
                    month=month+1;
                }
                calendar.set(year,month,dayOfMonth);
                datePicker.setText(year + "-" + month + "-" + dayOfMonth);
            }
        },cYear,cMonth,cDay);
        datePickerDialog.show();
    }

    public static Intent getIntent(Context context,Task task){
        Intent intent=new Intent(context,EditTaskActivity.class);
        intent.putExtra(HomeFragment.intent_data, (Serializable)task);
        return intent;
    }
}