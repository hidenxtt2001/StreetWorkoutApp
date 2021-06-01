package com.example.streetworkout.Fragment.CalenderFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExercise;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


public class CalenderFragment extends Fragment {


    public CalenderFragment() {
        // Required empty public constructor
    }

    private  View root;
    private RecyclerView recyclerViewExercise;
    private ExercisesEachDayAdapter exercisesEachDayAdapter;

    public static WeekExerciseUser weekExerciseUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.main_fragment_calender, container, false);
        recyclerViewExercise = root.findViewById(R.id.recycler_exercises);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewExercise.setLayoutManager(linearLayoutManager);

        exercisesEachDayAdapter = new ExercisesEachDayAdapter();
        /*exercisesEachDayAdapter.setData(getListExercises());
        recyclerViewExercise.setAdapter(exercisesEachDayAdapter);*/



        return root;
    }








}