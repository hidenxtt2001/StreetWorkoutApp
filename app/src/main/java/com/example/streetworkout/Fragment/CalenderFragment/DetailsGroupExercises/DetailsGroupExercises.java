package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.streetworkout.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsGroupExercises extends AppCompatActivity {

    private View view;
    private ImageView imgMisFoto, imgAnim, imgAnim2;
    private Handler handlerAnimation;
    private DatabaseReference mRef;
    private ImageView imageBackground;
    private TextView txtNameGroupExercise, txtNameLevel, txtNameExercise;

    private GroupExercise groupExercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_calendar_details_groups_exercises);

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

        mRef = FirebaseDatabase.getInstance().getReference();
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

    public void backCalendarFragment(View view) {
        onBackPressed();
    }
}