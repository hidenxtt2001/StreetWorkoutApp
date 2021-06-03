package com.example.streetworkout.Fragment.CalenderFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExercise;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseDaily;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.User.ViewModel.UserInforViewModel;
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
    private UserInforViewModel userInforViewModel;
    private ProgressBar progressBar;

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


        userInforViewModel = MainActivity.userInforViewModel;


        exercisesEachDayAdapter = new ExercisesEachDayAdapter(this.getActivity(),userInforViewModel.getWeekExerciseDaily().getValue());
        recyclerViewExercise.setAdapter(exercisesEachDayAdapter);

        userInforViewModel.getWeekExerciseUser().observe(this.getActivity(), new Observer<WeekExerciseUser>() {
            @Override
            public void onChanged(WeekExerciseUser weekExerciseUser) {
                exercisesEachDayAdapter.notifyDataSetChanged();

            }
        });

        userInforViewModel.getWeekExerciseDaily().observe(this.getActivity(), new Observer<ArrayList<WeekExerciseDaily>>() {
            @Override
            public void onChanged(ArrayList<WeekExerciseDaily> weekExerciseDailies) {
                if(exercisesEachDayAdapter.getItemCount() > 0){
                    progressBar = root.findViewById(R.id.main_calendar_frag_progressbar);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        /*exercisesEachDayAdapter.setData(getListExercises());
        recyclerViewExercise.setAdapter(exercisesEachDayAdapter);*/


        return root;
    }










}