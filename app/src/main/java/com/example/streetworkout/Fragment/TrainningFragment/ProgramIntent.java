package com.example.streetworkout.Fragment.TrainningFragment;

import android.os.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.appcompat.app.*;
import androidx.recyclerview.widget.*;

import com.example.streetworkout.*;
import com.example.streetworkout.Fragment.TrainningFragment.Program.*;
import com.example.streetworkout.Fragment.TrainningFragment.WeekExercise.*;
import com.example.streetworkout.R;
import com.google.firebase.database.*;

import java.util.*;

public class ProgramIntent extends AppCompatActivity {

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

    }

     void InitializeElement() {
        loading = findViewById(R.id.program_progressBar);
        recyclerView = findViewById(R.id.program_recyclerView);
        name = findViewById(R.id.program_exercisename);
        /*loading.setVisibility(View.VISIBLE);*/

         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
         linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         recyclerView.setLayoutManager(linearLayoutManager);
         weeklyAdapter = new WeeklyAdapter(this,weekExercises);



    }

    ArrayList<WeekExercise> weekExercises;
    ArrayList<ProgramExerciseWeekly> programExerciseWeeklyArrayList ;


    void setupforFirebase()
    {
        programExerciseWeeklyArrayList = new ArrayList<>();
        ref.getReference("ProgramExercises").child("ProgramExerciseWeekly").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()
                     ) {

                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


    }

}
