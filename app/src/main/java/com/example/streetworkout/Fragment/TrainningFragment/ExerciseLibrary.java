package com.example.streetworkout.Fragment.TrainningFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.streetworkout.Fragment.TrainningFragment.Exercises.Exercise;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.exerciseAdapter;
import com.example.streetworkout.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExerciseLibrary extends AppCompatActivity {

    RecyclerView recyclerView;
    com.example.streetworkout.Fragment.TrainningFragment.Exercises.exerciseAdapter exerciseAdapter;
    FirebaseDatabase reference;
    String lastnode;
    ProgressBar loading;
    boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().getBackground().setDither(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_fragment_traning_exercise_library);


        recyclerView = findViewById(R.id.excercise_library);
        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.VISIBLE);

        exerciseAdapter = new exerciseAdapter(this);
        recyclerView.setAdapter(exerciseAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        reference = FirebaseDatabase.getInstance();
        //change to 10
        reference.getReference("Exercises").child("Exercise").limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()
                ) {
                    Exercise exercise = data.getValue(Exercise.class);
                    if (exercise.getLinkImage() != null) {
                        exerciseAdapter.addExerciseItem(exercise);
                        lastnode = exercise.getIdExercise();
                    }
                    loading.setVisibility(View.GONE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isLoaded && (lastnode!=null)) {
                    isLoaded = true;
                    reference.getReference("Exercises").child("Exercise").orderByKey().startAfter(lastnode).limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()
                            ) {
                                Exercise exercise = data.getValue(Exercise.class);
                                if (exercise.getLinkImage() != null) {
                                    exerciseAdapter.addExerciseItem(exercise);
                                    lastnode = exercise.getIdExercise();
                                    isLoaded = false;
                                }
                                loading.setVisibility(View.GONE);


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });




    }

}