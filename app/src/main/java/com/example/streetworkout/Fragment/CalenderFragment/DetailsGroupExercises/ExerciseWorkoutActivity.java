package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.streetworkout.Fragment.CalenderFragment.DetailsExercises.GroupExerciseRound;
import com.example.streetworkout.Fragment.CalenderFragment.DetailsExercises.GroupExerciseWarmup;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.Exercise;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ExerciseWorkoutActivity extends AppCompatActivity {

    private TextView txtNameGroup;
    private GroupExercise groupExercise;
    private String getNameExercise;
    private RecyclerView recyclerViewWarmUp, recyclerViewRound;
    private DatabaseReference mRef;
    private WarmUpAdapter warmUpAdapter;
    private RoundAdapter roundAdapter;
    private ArrayList<Exercise> listExerciseWarmUp, listExerciseRound;
    private String checkDayExercise;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_calendar_exercise_workout_activity);

        toolbar = findViewById(R.id.customToolbar);





        groupExercise = (GroupExercise)this.getIntent().getSerializableExtra("getGroupExercise");
        getNameExercise = getIntent().getStringExtra("getNameExercise");
        toolbar.setTitle(groupExercise.getNameGroupExercise());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //set up recycler view Warm-up
        listExerciseWarmUp = new ArrayList<Exercise>();
        recyclerViewWarmUp = findViewById(R.id.recyclerView_warmup);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewWarmUp.setLayoutManager(linearLayoutManager);
        warmUpAdapter = new WarmUpAdapter(this, listExerciseWarmUp);
        recyclerViewWarmUp.setAdapter(warmUpAdapter);

        //set up recycler view Round
        listExerciseRound = new ArrayList<Exercise>();
        recyclerViewRound = findViewById(R.id.recyclerView_round);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recyclerViewRound.setLayoutManager(linearLayoutManager1);
        roundAdapter = new RoundAdapter(this, listExerciseRound);
        recyclerViewRound.setAdapter(roundAdapter);

        getDataWarmUp();
        getDataRound();

    }

    //get data from firebase for warm-up exercise
    public void getDataWarmUp(){
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("GroupExercises").child("GroupExerciseWarmup").orderByChild("idGroupExercise").equalTo(groupExercise.getIdGroupExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    GroupExerciseWarmup groupExerciseWarmup = snap.getValue(GroupExerciseWarmup.class);
                    mRef.child("Exercises").child("Exercise").orderByChild("idExercise").equalTo(groupExerciseWarmup.getIdExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot snapShot : snapshot.getChildren()) {
                                Exercise exerciseWarmUp = snapShot.getValue(Exercise.class);
                                listExerciseWarmUp.add(exerciseWarmUp);
                                warmUpAdapter.notifyDataSetChanged();
                            }
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

    //get data from firebase for round exercise
    public void getDataRound(){
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("GroupExercises").child("GroupExerciseRound").orderByChild("idGroupExercise").equalTo(groupExercise.getIdGroupExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    GroupExerciseRound groupExerciseRound = snap.getValue(GroupExerciseRound.class);
                    mRef.child("Exercises").child("Exercise").orderByChild("idExercise").equalTo(groupExerciseRound.getIdExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot snapShot : snapshot.getChildren()) {
                                Exercise exerciseRound = snapShot.getValue(Exercise.class);
                                listExerciseRound.add(exerciseRound);
                                roundAdapter.notifyDataSetChanged();
                            }
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void completeExercise(View view) {
        checkDayExercise = getIntent().getStringExtra("checkDayExercise");
        String day = "day" + checkDayExercise;
        WeekExerciseUser k =MainActivity.userInforViewModel.getWeekExerciseUser().getValue();
        switch (Integer.parseInt(checkDayExercise) ){
            case 1:
                k.setDay1(true);
                break;
            case 2:
                k.setDay2(true);
                break;
            case 3:
                k.setDay3(true);
                break;
            case 4:
                k.setDay4(true);
                break;
            case 5:
                k.setDay5(true);
                break;
            case 6:
                k.setDay6(true);
                break;
            case 7:
                k.setDay7(true);
                break;
        }
        mRef.child("WeekExercises").child("WeekExerciseUser").child(MainActivity.userInfor.getUid()).setValue(k);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        StatusWorkout z = new StatusWorkout(MainActivity.userInfor.getUid(),groupExercise.getIdGroupExercise(),dateFormat.format(date));
        mRef.child("StatusUserExercise").push().setValue(z);
        setResult(RESULT_OK);
        finish();
    }
}