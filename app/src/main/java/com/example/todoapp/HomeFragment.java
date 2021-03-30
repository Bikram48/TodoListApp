package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class HomeFragment extends Fragment implements TaskAdapter.ItemClicked{
    private RecyclerView recyclerView;

    private Repository repository;
    private List<Task> taskList;
    private TaskAdapter adapter;
    private FloatingActionButton fab;
    private MainViewModel viewModel;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(HomeFragment.this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        adapter = new TaskAdapter(HomeFragment.this);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repository = Repository.getRepository(getActivity().getApplication());
        viewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    taskList=tasks;
                    adapter.setData(tasks);
                }
            }
        });
        return view;
    }

    @Override
    public void onItemClicked(int index, String btnStatus) {
        if(btnStatus.equals("delete")){
            repository.delete(taskList.get(index));
        }
        if(btnStatus.equals("edit")){
            repository = Repository.getRepository(getActivity().getApplication());

            Intent intent=new Intent(getContext(),EditTaskActivity.class);
            intent.putExtra("Arraylist", (Serializable) taskList);
            intent.putExtra("position",index);
            startActivity(intent);
        }
    }
}