package com.example.todoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;

public class HomeFragment extends Fragment implements TaskAdapter.ItemClicked{
    private static final int LENGTH_LONG = 10;
    private RecyclerView recyclerView;
    private Repository repository;
    private List<Task> taskList;
    private List<Task> undoList;
    private TaskAdapter adapter;
    private FloatingActionButton fab;
    private MainViewModel viewModel;
    public static final String intent_data="com.example.todoapp.task";
    private boolean sort;
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
        recyclerView.setHasFixedSize(true);
        repository = Repository.getRepository(getActivity().getApplication());
        if(sort){
            viewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
                @Override
                public void onChanged(List<Task> tasks) {
                    if(tasks != null){
                        taskList=tasks;
                        undoList=tasks;
                        adapter.setData(tasks);
                    }
                }
            });
        }
        viewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    taskList=tasks;
                    undoList=tasks;
                    adapter.setData(tasks);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort:
                sort=true;
                break;
            case R.id.deleteAll:
                AlertDialog.Builder mAlterDialog = new AlertDialog.Builder(getContext());
                mAlterDialog.setMessage("Are you sure want to delete all??")
                        .setCancelable(false)
                        .setTitle(getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher);
                mAlterDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.deleteAll();
                    }
                });
                mAlterDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mAlterDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClicked(int index, String btnStatus) {
        Task task=undoList.get(index);
        if(btnStatus.equals("delete")){
            repository.delete(taskList.get(index));
        }
        if(btnStatus.equals("edit")){
            Intent intent=EditTaskActivity.getIntent(getContext(),taskList.get(index));
            startActivity(intent);
        }
        if(btnStatus.equals("finished")){
            taskList.remove(index);
            adapter.notifyItemRemoved(index);

            Snackbar.make(getView(), "Completed. ", Snackbar.LENGTH_LONG)
                    .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            if (event != DISMISS_EVENT_ACTION) {
                                repository.delete(task);
                            }
                        }
                    })
                    .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    taskList.add(task);
                                    adapter.notifyItemInserted(index);
                                }
                            }
                        )
                    .show();
        }
    }
}