package com.example.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodoTask extends Fragment implements View.OnClickListener{
    private TextInputEditText titleEditText;
    private RadioGroup radioGroup;
    private Button[] category;
    private Button datePicker;
    private Button taskReminder;
    private RadioButton radioButton;
    private Button submitBtn;
    private Repository repository;
    int[] buttonIDs;
    Calendar calendar;
    private Date date;
    View view;
    private String categoryName;
    private int priority;
    public TodoTask() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar=Calendar.getInstance();
        category=new Button[6];
        buttonIDs = new int[] {R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6};

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_todo_task, container, false);
        titleEditText=view.findViewById(R.id.task_title);
        datePicker=view.findViewById(R.id.update_date_picker);
        radioGroup=view.findViewById(R.id.update_priority);
        repository=Repository.getRepository(getActivity().getApplication());
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
        taskReminder=view.findViewById(R.id.task_reminder);
        submitBtn=view.findViewById(R.id.update_btn);
        for(int i=0;i<category.length;i++){
            category[i]=view.findViewById(buttonIDs[i]);
        }
        for(Button button:category){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryName=button.getText().toString();
                }
            });
        }

        submitBtn.setOnClickListener(this::onClick);
        return view;
    }

    void pickDate() {
        Calendar calendar = Calendar.getInstance();
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //datePicker.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                calendar.set(year,month,dayOfMonth);
                datePicker.setText(year + "-" + month + "-" + dayOfMonth);
            }
        },cYear,cMonth,cDay);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        String task_title=titleEditText.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) view.findViewById(selectedId);
        if(radioButton.getText().toString().equals("low"))
            priority=1;
        if(radioButton.getText().toString().equals("medium"))
            priority=2;
        if(radioButton.getText().toString().equals("high"))
            priority=3;

        Date date=new Date();
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(datePicker.getText().toString());
        }catch (ParseException ex){
            ex.printStackTrace();
        }

        Task task=new Task(task_title,categoryName,date,null,priority);
        repository.addTask(task);
        Intent intent=new Intent(getContext(),MainActivity.class);
        startActivity(intent);
    }

}