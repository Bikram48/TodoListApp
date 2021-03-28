package com.example.todoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.todoapp.Adapters.ViewPagerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;


public class BottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "NewTaskDialog";
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet,container,false);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.AppTheme);
        viewPager=(ViewPager) view.findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout=view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    public static BottomSheet newInstance() {
        return new BottomSheet();
    }

   @Override public int getTheme() {
        return R.style.CustomBottomSheetDialog;
    }
}


