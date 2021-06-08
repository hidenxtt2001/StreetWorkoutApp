package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.Exercise;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.ExerciseAdapter;
import com.example.streetworkout.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WarmUpAdapter extends RecyclerView.Adapter<WarmUpAdapter.ViewHolder> {

    Context context;
    ArrayList<Exercise> exercisesWarmUp = new ArrayList<>();

    public WarmUpAdapter(Context context, ArrayList<Exercise> arrayList){
        this.context = context;
        this.exercisesWarmUp = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_training_exercise_items, parent, false);
        return new WarmUpAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( WarmUpAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(exercisesWarmUp.get(position).getLinkImage()).placeholder(R.drawable.wolf_icon_black).into(holder.disImage);
        holder.nameExercise.setText(exercisesWarmUp.get(position).getNameExercise());
    }

    @Override
    public int getItemCount() {
        return exercisesWarmUp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameExercise;
        ImageView disImage;
        public ViewHolder( View itemView) {
            super(itemView);

            nameExercise = itemView.findViewById(R.id.nameExercise);
            disImage = itemView.findViewById(R.id.disImage);
        }
    }

}
