package com.example.todoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutApplication extends Fragment {
    private TextView textView;
    public AboutApplication() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_about_application, container, false);
        textView=view.findViewById(R.id.about_contents);
        textView.setText(Html.fromHtml(getString(R.string.aboutApplication)));
        return view;
    }
}