package com.example.todoapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.todoapp.data.Repository;
import com.example.todoapp.Models.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodoTask extends Fragment{
    private TextInputEditText titleEditText;
    private RadioGroup radioGroup;
    private Button[] category;
    private Button datePicker;
    private Button taskReminder;
    private RadioButton radioButton;
    private Button submitBtn;
    private String time;
    private Repository repository;
    private Date date;
    private View view;
    private String categoryName;
    private int priority;
    private CoordinatorLayout layout;
    int[] buttonIDs;
    boolean buttonSelected;
    public TodoTask() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = new Button[6];
        buttonIDs = new int[]{R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo_task, container, false);
        titleEditText = view.findViewById(R.id.task_title);
        datePicker = view.findViewById(R.id.update_date_picker);
        radioGroup = view.findViewById(R.id.update_priority);
        layout=view.findViewById(R.id.addFragment);
        repository = Repository.getRepository(getActivity().getApplication());
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
        taskReminder = view.findViewById(R.id.task_reminder);
        taskReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });
        submitBtn = view.findViewById(R.id.update_btn);
        for (int i = 0; i < category.length; i++) {
            category[i] = view.findViewById(buttonIDs[i]);
        }
        for (Button button : category) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryName = button.getText().toString();
                    buttonSelected=true;
                }
            });
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        return view;
    }

    void pickTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
                taskReminder.setText(time);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    void pickDate() {
        Calendar calendar = Calendar.getInstance();
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //datePicker.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                if (month > 0)
                    month = month + 1;
                calendar.set(year, month, dayOfMonth);
                datePicker.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, cYear, cMonth, cDay);
        datePickerDialog.show();
    }

    public void saveData(){
       if(titleEditText.getText().toString().equals("")){
           Snackbar snackBar = Snackbar.make(layout,
                   "Please input task!!", Snackbar.LENGTH_LONG);
           snackBar.show();
       }
       else if(radioGroup.getCheckedRadioButtonId()==-1){
           Snackbar snackBar = Snackbar.make(layout,
                   "Please set the priority!!", Snackbar.LENGTH_LONG);
           snackBar.show();
       }
       else if(datePicker.getText().toString().isEmpty()){
           Snackbar snackBar = Snackbar.make(layout,
                   "Please pick the date!!", Snackbar.LENGTH_LONG);
           snackBar.show();
       }
       else if(taskReminder.getText().toString().isEmpty()){
           Snackbar snackBar = Snackbar.make(layout,
                   "Please set the task reminder!!", Snackbar.LENGTH_LONG);
           snackBar.show();
       }
       else if(buttonSelected==false){
           Snackbar snackBar = Snackbar.make(layout,
                   "Please set the category!!", Snackbar.LENGTH_LONG);
           snackBar.show();
       }
        else {
           String task_title = titleEditText.getText().toString();
           int selectedId = radioGroup.getCheckedRadioButtonId();
           radioButton = (RadioButton) view.findViewById(selectedId);
           if (radioButton.getText().toString().equals(getString(R.string.low_priority)))
               priority = 1;
           if (radioButton.getText().toString().equals(getString(R.string.medium_priority)))
               priority = 2;
           if (radioButton.getText().toString().equals(getString(R.string.high_priority)))
               priority = 3;

           Date date = new Date();
           try {
               DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               date = format.parse(datePicker.getText().toString());
           } catch (ParseException ex) {
               ex.printStackTrace();
           }

           Task task = new Task(task_title, categoryName, date, null, time, priority);
           repository.addTask(task);
           if (task.getTaskReminder() != null) {
               String datefor_reminder = datePicker.getText().toString();
               String time = task.getTaskReminder();
               String title = task.getTitle();
               setAlaram(title, datefor_reminder, time);
           }
           Intent intent = new Intent(getContext(), MainActivity.class);
           intent.putExtra("insertmessage","A new task has been added");
           startActivity(intent);
       }
    }

    public void setAlaram(String task, String date, String time) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity().getApplicationContext(), AlaramBroadcastReceiver.class);
        intent.putExtra("task_name", task);
        intent.putExtra("time", time);
        intent.putExtra("data", date);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, intent, 0);
        String dateandtime = date + " " + time;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            alarmManager.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}