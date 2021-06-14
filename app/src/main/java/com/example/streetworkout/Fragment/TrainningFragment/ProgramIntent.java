package com.example.streetworkout.Fragment.TrainningFragment;

import android.os.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.appcompat.app.*;
import androidx.recyclerview.widget.*;

import com.example.streetworkout.*;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.*;
import com.example.streetworkout.Fragment.TrainningFragment.Program.*;
import com.example.streetworkout.R;
import com.google.firebase.database.*;

import java.util.*;



public class ProgramIntent extends AppCompatActivity {


    ProgramExercise programExercise;
    FirebaseDatabase ref;
    ProgressBar loading;
    TextView name;
    RecyclerView recyclerView;
    WeeklyAdapter weeklyAdapter;



    boolean isLoaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_training_program_inside_holder);
        programExercise = (ProgramExercise)this.getIntent().getSerializableExtra("data");
        String nameProgram = getIntent().getStringExtra("nameProgram");
        InitializeElement();
        name.setText(nameProgram);
        setupforFirebase();

    }

    public void backActivity(View view) {
        onBackPressed();
    }

     void InitializeElement() {
        loading = findViewById(R.id.program_progressBar);
        recyclerView = findViewById(R.id.program_recyclerView);
        name = findViewById(R.id.program_exercisename);
        loading.setVisibility(View.VISIBLE);

         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
         linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         recyclerView.setLayoutManager(linearLayoutManager);
         weekExercises = new ArrayList<>();
         weeklyAdapter = new WeeklyAdapter(this,weekExercises);
         recyclerView.setAdapter(weeklyAdapter);




    }

    ArrayList<WeekExercise> weekExercises;


    void setupforFirebase()
    {
        ref = FirebaseDatabase.getInstance();
        ref.getReference("ProgramExercises").child("ProgramExerciseWeekly").orderByChild("idProgramExercise").equalTo(programExercise.getIdProgramExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()
                     ) {

                         ref.getReference("WeekExercises").child("WeekExercise").orderByKey().equalTo(snap.getValue(ProgramExerciseWeekly.class).getIdWeekExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot snapshot) {
                                 for (DataSnapshot snap: snapshot.getChildren()) {
                                     WeekExercise weekExercise = snap.getValue(WeekExercise.class);
                                     weekExercises.add(weekExercise);
                                     weeklyAdapter.notifyDataSetChanged();
                                     break;
                                 }

                                 loading.setVisibility(View.GONE);
                             }

                             @Override
                             public void onCancelled(DatabaseError error) {

                             }
                         });


                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });



    }

}
