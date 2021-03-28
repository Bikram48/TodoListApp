package com.example.todoapp.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Models.TodoModel;
import com.example.todoapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<TodoModel> taskList;
    public RecyclerViewAdapter(){
    }

    public void setData(List<TodoModel> data){
        taskList=data;
        Log.d("MainActivity", "setData: "+taskList.get(0).toString());
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todoitems,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       TodoModel task=taskList.get(position);
       holder.onBind(task);
    }

    @Override
    public int getItemCount() {
        if(taskList != null)
            return taskList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView taskName,taskDuration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName=itemView.findViewById(R.id.taskName);
        }

        public void onBind(TodoModel task) {
            taskName.setText(task.getTaskTitle());
        }
    }
}
