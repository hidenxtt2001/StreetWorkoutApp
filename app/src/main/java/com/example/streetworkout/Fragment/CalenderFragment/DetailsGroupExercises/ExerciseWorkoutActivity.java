package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.streetworkout.Fragment.CalenderFragment.DetailsExercises.GroupExerciseWarmup;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.Exercise;
import com.example.streetworkout.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExerciseWorkoutActivity extends AppCompatActivity {

    private TextView txtNameExercise, txtNameGroup;
    private GroupExercise groupExercise;
    private String getNameExercise;
    private RecyclerView recyclerViewWarmUp, recyclerViewRounded;
    private DatabaseReference mRef;
    private GroupExerciseWarmup groupExerciseWarmup;
    private WarmUpAdapter warmUpAdapter;
    private Exercise exerciseWarmUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_calendar_exercise_workout_activity);

        txtNameGroup = findViewById(R.id.txt_name_group_exercise);
        txtNameExercise = findViewById(R.id.name_exercise);

        groupExercise = (GroupExercise)this.getIntent().getSerializableExtra("getGroupExercise");
        getNameExercise = getIntent().getStringExtra("getNameExercise");

        txtNameGroup.setText(groupExercise.getNameGroupExercise());
        txtNameExercise.setText(getNameExercise);

        recyclerViewWarmUp = findViewById(R.id.recyclerView_warmup);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewWarmUp.setLayoutManager(linearLayoutManager);

        //get data from firebase
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("GroupExercises").child("GroupExerciseWarmup").orderByChild("idGroupExercise").equalTo(groupExercise.getIdGroupExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    groupExerciseWarmup = snap.getValue(GroupExerciseWarmup.class);
                }
                mRef.child("Exercises").child("Exercise").orderByChild("idExercise").equalTo(groupExerciseWarmup.getIdExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
                        for (DataSnapshot snapShot: snapshot.getChildren()) {
                            exerciseWarmUp = snapShot.getValue(Exercise.class);

                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

    }

    public void backActivity(View view) {
        onBackPressed();
    }
}