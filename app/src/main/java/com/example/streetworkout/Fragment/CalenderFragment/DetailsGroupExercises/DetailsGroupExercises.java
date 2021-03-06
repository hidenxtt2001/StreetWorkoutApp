package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.streetworkout.Fragment.CalenderFragment.DetailsExercises.GroupExerciseBodyPart;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailsGroupExercises extends AppCompatActivity {

    private View view;
    private ImageView imgMisFoto, imgAnim, imgAnim2;
    private Handler handlerAnimation;
    private DatabaseReference mRef;
    private ImageView imageBackground;
    private TextView txtNameGroupExercise, txtNameLevel, txtNameExercise;
    private final int RC_DETAILEXERCISE = 4;
    Toolbar toolbar;
    private GroupExercise groupExercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_calendar_details_groups_exercises);

        toolbar = findViewById(R.id.customToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtNameGroupExercise = findViewById(R.id.txt_name_group_exercise);
        txtNameExercise = findViewById(R.id.txt_name_exercise);
        txtNameLevel = findViewById(R.id.txt_name_level);
        imageBackground = findViewById(R.id.image_background_group_exercises);

        // Do animation button Next
        init();
        Glide.with(getBaseContext()).load(R.drawable.main_calendar_fragment_logo_next).apply(new RequestOptions().circleCrop()).into(imgMisFoto);
        startAni();

        //Getting idGroupExercise
        String getInfoIdGroup = getIntent().getStringExtra("getInfoIdGroup");
        String getNameExercise = getIntent().getStringExtra("getNameExercise");

        txtNameExercise.setText(getNameExercise);
        txtNameExercise.setVisibility(View.VISIBLE);

        txtNameLevel.setText(MainActivity.userInfor.getExperienceLevel() == 0 ? "Beginner" :MainActivity.userInfor.getExperienceLevel() == 1 ? "Intermediate" : "Advanced");

        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("GroupExercises").child("GroupExerciseBodyPart").orderByChild("idGroupExercise").equalTo(getInfoIdGroup).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                ArrayList<String> path = new ArrayList<>();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    GroupExerciseBodyPart k = snap.getValue(GroupExerciseBodyPart.class);
                    switch (k.getBodyValue()){
                        case 0:
                            path.add("Back");
                            break;
                        case 1:
                            path.add("Biceps");
                            break;
                        case 2:
                            path.add("Legs");
                            break;
                        case 3:
                            path.add("Chest");
                            break;
                        case 4:
                            path.add("Triceps");
                            break;
                        case 5:
                            path.add("Abs");
                            break;
                        case 6:
                            path.add("Shoulder");
                            break;
                        case 7:
                            path.add("Whole Body");
                            break;
                    }
                }
                txtNameExercise.setText(String.join(", ",path));

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        //Find id Group Exercise in Firebase
        mRef.child("GroupExercises").child("GroupExercise").orderByChild("idGroupExercise").equalTo(getInfoIdGroup).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    groupExercise = snap.getValue(GroupExercise.class);
                    break;
                }
                txtNameGroupExercise.setText(groupExercise.getNameGroupExercise());
                txtNameGroupExercise.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(Uri.parse(groupExercise.getLinkImageGroup())).into(imageBackground);
                imgMisFoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        passDataInNextActivity();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



    }

    private void init(){
        this.handlerAnimation = new Handler();
        this.view = findViewById(R.id.layout_per);
        this.imgAnim = findViewById(R.id.imgAnim);
        this.imgAnim2 = findViewById(R.id.imgAnim2);
        this.imgMisFoto = findViewById(R.id.imgMiFoto);
    }

    private void startAni(){
        this.runnableAnim.run();
    }

    private final Runnable runnableAnim = new Runnable() {
        @Override
        public void run() {
            imgAnim.animate().scaleX(2f).scaleY(2f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                @Override
                public void run() {
                    imgAnim.setScaleX(1f);
                    imgAnim.setScaleY(1f);
                    imgAnim.setAlpha(1f);
                }
            });

            imgAnim2.animate().scaleX(2f).scaleY(2f).alpha(0f).setDuration(700).withEndAction(new Runnable() {
                @Override
                public void run() {
                    imgAnim2.setScaleX(1f);
                    imgAnim2.setScaleY(1f);
                    imgAnim2.setAlpha(1f);
                }
            });
            handlerAnimation.postDelayed(runnableAnim, 1000);
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void backCalendarFragment(View view) {
        onBackPressed();
    }

    public void passDataInNextActivity() {

        String getNameExercise = getIntent().getStringExtra("getNameExercise");
        String getDayExercise = getIntent().getStringExtra("checkDayExercise");
        Intent intent = new Intent(this, ExerciseWorkoutActivity.class);
        intent.putExtra("getGroupExercise", groupExercise);
        intent.putExtra("getNameExercise", getNameExercise);
        intent.putExtra("checkDayExercise", getDayExercise);
        startActivityForResult(intent,RC_DETAILEXERCISE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RC_DETAILEXERCISE:
                if(resultCode == RESULT_OK) finish();
                break;
        }
    }
}