package com.example.streetworkout.Fragment.TrainningFragment.Program;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.streetworkout.Fragment.CalenderFragment.ExercisesEachDayAdapter;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExercise;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseDaily;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailWeekly extends AppCompatActivity {

    private RecyclerView recyclerViewDetailWeekly;
    private ExercisesEachDayAdapter exercisesEachDayAdapter;
    private ProgressBar progressBar;
    private WeekExercise weekExercise;
    private DatabaseReference mRef;
    private ArrayList <WeekExerciseDaily> weekExerciseDailies;
    private WeekExerciseUser weekExerciseUser;
    private TextView textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_trainning_detail_weekly);

        recyclerViewDetailWeekly = findViewById(R.id.recylerView_name_weekly);
        textName = findViewById(R.id.name_weekly);
        mRef = FirebaseDatabase.getInstance().getReference();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewDetailWeekly.setLayoutManager(linearLayoutManager);


        weekExercise = (WeekExercise)this.getIntent().getSerializableExtra("getWeekExercise");
        textName.setText(weekExercise.getNameWeekExercise());
        weekExerciseDailies = new ArrayList<WeekExerciseDaily>();
        exercisesEachDayAdapter = new ExercisesEachDayAdapter(this.getApplicationContext(), weekExerciseDailies, weekExerciseUser);
        recyclerViewDetailWeekly.setAdapter(exercisesEachDayAdapter);

        mRef.child("WeekExercises").child("WeekExerciseDaily").orderByChild("idWeekExercise").equalTo(weekExercise.getIdWeekExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshotChild : snapshot.getChildren())
                {
                    weekExerciseDailies.add(snapshotChild.getValue(WeekExerciseDaily.class));
                    progressBar = findViewById(R.id.name_weekly_progressbar);
                    progressBar.setVisibility(View.GONE);
                    exercisesEachDayAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void backActivity(View view) {
        onBackPressed();
    }
}