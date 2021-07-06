package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streetworkout.Fragment.TrainningFragment.Exercises.Exercise;
import com.example.streetworkout.Login.IntroAdapter;
import com.example.streetworkout.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StartDetailExerciseAdapter extends RecyclerView.Adapter<StartDetailExerciseAdapter.ViewHolder>  {

    Context context;
    ArrayList<Exercise> exercises;

    public StartDetailExerciseAdapter(Context context , ArrayList<Exercise> exercises){
        this.context = context;
        this.exercises = exercises;
    }

    @NonNull
    @NotNull
    @Override
    public StartDetailExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_calendar_excercise_start_detail_video_item,parent,false);
        return new StartDetailExerciseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StartDetailExerciseAdapter.ViewHolder holder, int position) {
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        holder.videoView.setVideoURI(Uri.parse(exercises.get(position).getLinkVideo().toString()));
        holder.videoView.start();
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);

        }
    }
}
