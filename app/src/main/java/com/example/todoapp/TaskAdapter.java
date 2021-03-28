package com.example.todoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.todoapp.Models.TodoModel;
import com.example.todoapp.data.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {


    private List<Task> taskList;
    Context context;
    ItemClicked activity;
    public interface ItemClicked{
        void onItemClicked(int index,String btnStatus);
    }
    private final ViewBinderHelper viewBinderHelper=new ViewBinderHelper();

    public TaskAdapter(Context context) {
        this.context=context;
        activity=(ItemClicked)context;
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
        holder.itemView.setTag(taskList.get(position));
        Task task = taskList.get(position);
        holder.onBind(task);
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(taskList.get(position).getId()));
        viewBinderHelper.closeLayout(String.valueOf(taskList.get(position).getId()));
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
        private TextView txtEdit;
        private TextView txtDelete;
        private Button categoryBtn;
        private SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.todoitems_swipe, parent, false));
            taskName=itemView.findViewById(R.id.taskName);
            categoryBtn=itemView.findViewById(R.id.category_btn);
            txtEdit=itemView.findViewById(R.id.txtEdit);
            txtDelete=itemView.findViewById(R.id.txtDelete);
            swipeRevealLayout=itemView.findViewById(R.id.swipeLayout);
            txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(taskList.indexOf((Task) itemView.getTag()),"delete");
                }
            });

            txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(taskList.indexOf((Task) itemView.getTag()),"edit");
                }
            });
        }

        public void onBind(Task task) {
            taskName.setText(task.getTitle());
            categoryBtn.setText(task.getCategory());
        }
    }
}
