package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Models.TodoModel;
import com.example.todoapp.data.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {


    private List<Task> taskList;

    public TaskAdapter() {
    }

    public void setData(List<Task> data){
        taskList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.onBind(task);
    }

    @Override
    public int getItemCount() {
        if(taskList != null)
            return taskList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private  TextView taskName;
        private  TextView descTextView;
        private Button categoryBtn;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.todoitems_swipe, parent, false));
            taskName=itemView.findViewById(R.id.taskName);
            categoryBtn=itemView.findViewById(R.id.category_btn);
        }

        public void onBind(Task task) {
            taskName.setText(task.getTitle());
            categoryBtn.setText(task.getCategory());
        }
    }
}
