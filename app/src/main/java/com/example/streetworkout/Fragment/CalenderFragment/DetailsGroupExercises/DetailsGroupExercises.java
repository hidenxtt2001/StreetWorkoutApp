package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.streetworkout.R;

public class DetailsGroupExercises extends AppCompatActivity {

    private View view;
    private ImageView imgMisFoto, imgAnim, imgAnim2;
    private Handler handlerAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_calendar_details_groups_exercises);

        init();

        Glide.with(getBaseContext()).load(R.drawable.main_calendar_fragment_logo_next).apply(new RequestOptions().circleCrop()).into(imgMisFoto);

        startAni();
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

    private Runnable runnableAnim = new Runnable() {
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

            imgAnim2.animate().scaleX(2f).scaleY(2f).alpha(0f).setDuration(500).withEndAction(new Runnable() {
                @Override
                public void run() {
                    imgAnim2.setScaleX(1f);
                    imgAnim2.setScaleY(1f);
                    imgAnim2.setAlpha(1f);
                }
            });

            handlerAnimation.postDelayed(runnableAnim, 500);
        }
    };
}