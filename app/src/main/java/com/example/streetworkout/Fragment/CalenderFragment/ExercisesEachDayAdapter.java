package com.example.streetworkout.Fragment.CalenderFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExercise;
import com.example.streetworkout.R;

import java.util.List;

public class ExercisesEachDayAdapter extends RecyclerView.Adapter<ExercisesEachDayAdapter.ExercisesViewHolder> {

    private List<WeekExercise> mListWeek;

    public void setData(List<WeekExercise> list) {
        this.mListWeek = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item_week_exercise, parent, false);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesEachDayAdapter.ExercisesViewHolder holder, int position) {
        WeekExercise weekExercise = mListWeek.get(position);
        if(weekExercise == null) {
            return;
        }

        holder.txtNameDay.setText(weekExercise.getNameWeekExercise());
        holder.txtCountDay.setText(String.valueOf(position+1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListWeek != null){
            return mListWeek.size();
        }
        return 0;
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNameDay;
        public TextView txtCountDay;


        public ExercisesViewHolder(@NonNull View itemView){
            super(itemView);
            txtNameDay = itemView.findViewById(R.id.name_day);
            txtCountDay = itemView.findViewById(R.id.count_day);
        }
    }
}
