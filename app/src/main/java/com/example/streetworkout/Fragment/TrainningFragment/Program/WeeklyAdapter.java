package com.example.streetworkout.Fragment.TrainningFragment.Program;

import android.content.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.recyclerview.widget.*;

import com.example.streetworkout.*;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.*;

import java.util.*;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ViewHolder> {

    Context context;
    ArrayList<WeekExercise> weeklyList ;

    public WeeklyAdapter(Context context, ArrayList<WeekExercise> weeklyList) {
        this.context = context;
        this.weeklyList = weeklyList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_training_program_weeks, parent, false);
        return new WeeklyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( WeeklyAdapter.ViewHolder holder, int position) {
        holder.bigWeek.setText(weeklyList.get(position).getNameWeekExercise());
        holder.smallWeek.setText(weeklyList.get(position).getNameWeekExercise());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return weeklyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView smallWeek;
        TextView bigWeek;
        public ViewHolder( View itemView) {
            super(itemView);
            smallWeek = itemView.findViewById(R.id.small_week);
           bigWeek= itemView.findViewById(R.id.big_week);

        }
    }
}
