package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.Exercise;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class StartDetailExerciseActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView timer;
    TextView nameExercise, second;
    ViewPager2 videoView;
    DotsIndicator dotsIndicator;
    StartDetailExerciseAdapter startDetailExerciseAdapter;
    ArrayList<Exercise> exercises;
    GroupExercise groupExercise;
    private DatabaseReference mRef;

    private String checkDayExercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_calendar_exercise_start_detail_exercise);
        mRef = FirebaseDatabase.getInstance().getReference();
        toolbar = findViewById(R.id.customToolbar);
        timer = findViewById(R.id.timer);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        exercises =(ArrayList<Exercise>) this.getIntent().getSerializableExtra("listExercise");
        groupExercise = (GroupExercise) this.getIntent().getSerializableExtra("groupExercise");

        second = findViewById(R.id.second);
        nameExercise = findViewById(R.id.nameExercise);

        second.setText("20 seconds");
        nameExercise.setText(exercises.get(0).getNameExercise());
        // Timer counter
        SetTimer();

        // Setup video play
        videoView = findViewById(R.id.videoPlay);
        videoView.setOffscreenPageLimit(5);
        dotsIndicator = findViewById(R.id.dotChangeIntro);
        startDetailExerciseAdapter = new StartDetailExerciseAdapter(this,exercises);
        videoView.setAdapter(startDetailExerciseAdapter);
        dotsIndicator.setViewPager2(videoView);

        videoView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                switch (position){
                    case 0:
                    case 1:
                        second.setText("20 seconds");
                        break;
                    case 2:
                    case 3:
                    case 4:
                        second.setText("30 seconds");

                }
                nameExercise.setText(exercises.get(position).getNameExercise());

            }
        });

    }

    final int[] millis = {0};
    private void SetTimer(){

        Handler handler = new Handler(Looper.getMainLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){

                    TimeZone tz = TimeZone.getTimeZone("UTC");
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                    df.setTimeZone(tz);
                    String time = df.format(new Date(millis[0]));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText(time);
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    millis[0] += 1000;
                }
            }
        };
        new Thread(runnable).start();



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.to_down_light,R.anim.from_bottom_down_light);
    }

    public void completeExercise(View view) {
        checkDayExercise = getIntent().getStringExtra("checkDayExercise");
        String day = "day" + checkDayExercise;
        WeekExerciseUser k = MainActivity.userInforViewModel.getWeekExerciseUser().getValue();
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
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date(millis[0]));
        z.setTimeComplete(time);
        mRef.child("StatusExercise").child("StatusUserExercise").push().setValue(z);
        setResult(RESULT_OK);
        finish();
    }
}