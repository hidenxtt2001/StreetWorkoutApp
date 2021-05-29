package com.example.streetworkout.Fragment.CalenderFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExercise;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseDaily;
import com.example.streetworkout.R;

import java.util.List;

public class ExercisesEachDayAdapter extends RecyclerView.Adapter<ExercisesEachDayAdapter.ExercisesViewHolder> {
    Context context;
    private List<WeekExerciseDaily> mListWeek;

    public void setData(List<WeekExerciseDaily> list) {
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
        WeekExerciseDaily weekExerciseDaily = mListWeek.get(position);
        if(weekExerciseDaily == null) {
            return;
        }

/*        holder.txtNameDay.setText(weekExerciseDaily.getNameWeekExercise());*/
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
