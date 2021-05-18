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

import com.example.streetworkout.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;


public class CalenderFragment extends Fragment {


    public CalenderFragment() {
        // Required empty public constructor
    }

    private  View root;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerViewExercise;
    private ExercisesEachDayAdapter exercisesEachDayAdapter;
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
        /*appBarLayout = root.findViewById(R.id.appBar);
        collapsingToolbarLayout = root.findViewById(R.id.collapsingToolbarLayout);
        toolbar = root.findViewById(R.id.toolbar);*/

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewExercise.setLayoutManager(linearLayoutManager);

        exercisesEachDayAdapter = new ExercisesEachDayAdapter();
        exercisesEachDayAdapter.setData(getListExercises());
        recyclerViewExercise.setAdapter(exercisesEachDayAdapter);

        /*initToolbarAnimation();*/


        return root;
    }



    private List<ExercisesEachDay> getListExercises(){
        List<ExercisesEachDay> list = new ArrayList<>();
        list.add(new ExercisesEachDay(R.drawable.calendar_image_dayone, "Workouts", "Chest"));
        list.add(new ExercisesEachDay(R.drawable.calendar_image_dayone, "Workouts", "Chest"));
        list.add(new ExercisesEachDay(R.drawable.calendar_image_dayone, "Workouts", "Chest"));
        list.add(new ExercisesEachDay(R.drawable.calendar_image_dayone, "Workouts", "Chest"));
        list.add(new ExercisesEachDay(R.drawable.calendar_image_dayone, "Workouts", "Chest"));
        list.add(new ExercisesEachDay(R.drawable.calendar_image_dayone, "Workouts", "Chest"));
        list.add(new ExercisesEachDay(R.drawable.calendar_image_dayone, "Workouts", "Chest"));
        list.add(new ExercisesEachDay(R.drawable.calendar_image_dayone, "Workouts", "Chest"));
        return list;
    }

    private void initToolbarAnimation(){
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.calendar_background);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor = palette.getVibrantColor(getResources().getColor(R.color.colorMain_1));
                collapsingToolbarLayout.setContentScrimColor(myColor);
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorMain));
            }
        });

    }

}