package com.example.streetworkout.Fragment.TrainningFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.streetworkout.Fragment.TrainningFragment.Exercises.Exercise;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.ExerciseAdapter;
import com.example.streetworkout.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ExerciseLibrary extends AppCompatActivity {

    RecyclerView recyclerView;
    ExerciseAdapter exerciseAdapter;
    FirebaseDatabase reference;
    String lastnode;
    ProgressBar loading;
    Toolbar customToolbar;
    EditText searchBar;
    LinearLayout errorLoading;
    boolean isLoaded = false;
    String searchText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_traning_exercise_library);
        InitializeElement();
        SetupRecycleview();
        SetupSearch();

    }

    private void InitializeElement() {

        errorLoading = findViewById(R.id.errorLoading);
        customToolbar = findViewById(R.id.customToolbar);
        customToolbar.setTitle("");
        setSupportActionBar(customToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        searchBar = findViewById(R.id.search_bar_custom);
        recyclerView = findViewById(R.id.excercise_library);
        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.VISIBLE);
    }

    private void SetupSearch() {
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
                    SearchShow(searchBar.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void SearchShow(String searchText) {
        exerciseAdapter.clearExerciseItem();
        errorLoading.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        recyclerView.clearOnScrollListeners();
        reference.getReference("Exercises").child("Exercise").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                for (DataSnapshot data : snapshot.getChildren()) {
                    Exercise exercise = data.getValue(Exercise.class);
                    if (Objects.requireNonNull(exercise).getLinkImage() != null && exercise.getNameExercise().toLowerCase().contains(searchText.toLowerCase())) {
                        exerciseAdapter.addExerciseItem(exercise);
                    }

                }
                if (exerciseAdapter.getItemCount() == 0) {
                    errorLoading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLoading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void SetupRecycleview() {
        exerciseAdapter = new ExerciseAdapter(this);
        errorLoading.setVisibility(View.GONE);
        recyclerView.setAdapter(exerciseAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        reference = FirebaseDatabase.getInstance();

        //change to 10
        final boolean[] checkLoaded = {false};
        final boolean[] checkTimeout = {false};
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(checkLoaded[0]) return;
                checkTimeout[0] = true;
                loading.setVisibility(View.GONE);
                errorLoading.setVisibility(View.VISIBLE);
            }
        };
        handler.postDelayed(runnable, 10000);
        reference.getReference("Exercises").child("Exercise").limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(checkTimeout[0]) return;
                checkLoaded[0] = true;
                loading.setVisibility(View.GONE);
                for (DataSnapshot data : snapshot.getChildren()) {
                    Exercise exercise = data.getValue(Exercise.class);
                    if (exercise.getLinkImage() != null) {
                        exerciseAdapter.addExerciseItem(exercise);
                        lastnode = exercise.getIdExercise();
                    }
                }
                if (exerciseAdapter.getItemCount() == 0) {
                    errorLoading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                checkLoaded[0] = true;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isLoaded && (lastnode != null) && !checkTimeout[0]) {
                    isLoaded = true;
                    reference.getReference("Exercises").child("Exercise").orderByKey().startAfter(lastnode).limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Exercise exercise = data.getValue(Exercise.class);
                                if (Objects.requireNonNull(exercise).getLinkImage() != null) {
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}