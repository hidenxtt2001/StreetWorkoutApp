package com.example.streetworkout.Fragment.CalenderFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises.DetailsGroupExercises;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseDaily;
import com.example.streetworkout.R;

import java.util.ArrayList;

public class ExercisesEachDayAdapter extends RecyclerView.Adapter<ExercisesEachDayAdapter.ExercisesViewHolder> {
    Context context;
    ArrayList<WeekExerciseDaily> mListWeek;


    public ExercisesEachDayAdapter(Context context, ArrayList<WeekExerciseDaily> list) {
        this.mListWeek = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_calendar_item_week_exercise, parent, false);

        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesEachDayAdapter.ExercisesViewHolder holder, int position) {
        WeekExerciseDaily weekExerciseDaily = mListWeek.get(position);
        if (weekExerciseDaily == null) {
            return;
        }
        String nameExercise ="";

        switch (position) {
            case 0:
                nameExercise = "Chest - Triceps";
                break;
            case 1:
                nameExercise = "Legs";
                break;
            case 2:
                nameExercise = "Back - Biceps";
                break;
            case 3:
                nameExercise = "Shoulder";
                break;
            case 4:
                nameExercise = "Whole body";
                break;
            case 5:
                nameExercise = "Whole body";
                break;
            case 6:
                nameExercise = "Abs";
                break;
        }

        holder.txtCountDay.setText(String.valueOf(position + 1));
        holder.txtNameDay.setText(nameExercise);

        final String lastNameExercise = nameExercise;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInfoIdGroup = mListWeek.get(position).getIdGroupExercise();
                Intent intent = new Intent(v.getContext(), DetailsGroupExercises.class);
                intent.putExtra("getInfoIdGroup", getInfoIdGroup);
                intent.putExtra("getNameExercise", lastNameExercise);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListWeek.size();
    }

    public static class ExercisesViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNameDay;
        public TextView txtCountDay;


        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameDay = itemView.findViewById(R.id.name_day);
            txtCountDay = itemView.findViewById(R.id.count_day);
        }
    }

}
