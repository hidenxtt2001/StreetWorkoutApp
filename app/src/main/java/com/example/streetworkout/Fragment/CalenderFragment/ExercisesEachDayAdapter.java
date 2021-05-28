package com.example.streetworkout.Fragment.CalenderFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streetworkout.R;

import java.util.List;

public class ExercisesEachDayAdapter extends RecyclerView.Adapter<ExercisesEachDayAdapter.ExercisesViewHolder> {

    private List<ExercisesEachDay> mListExercises;

    public void setData(List<ExercisesEachDay> list) {
        this.mListExercises = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item_day_exercise, parent, false);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesEachDayAdapter.ExercisesViewHolder holder, int position) {
        ExercisesEachDay exercisesEachDay = mListExercises.get(position);
        if(exercisesEachDay == null){
            return;
        }
        holder.imageDay.setImageResource(exercisesEachDay.getResourceImg());
        holder.txtDescribe.setText(exercisesEachDay.getNameDescribe());
        holder.txtExercise.setText(exercisesEachDay.getNameExercise());

    }

    @Override
    public int getItemCount() {
        if(mListExercises != null){
            return mListExercises.size();
        }
        return 0;
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageDay;
        private TextView txtDescribe;
        private TextView txtExercise;

        public ExercisesViewHolder(@NonNull View itemView){
            super(itemView);
            imageDay = itemView.findViewById(R.id.img_day);
            txtDescribe = itemView.findViewById(R.id.name_describe);
            txtExercise = itemView.findViewById(R.id.name_exercise);
        }
    }
}
