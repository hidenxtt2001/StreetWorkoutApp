package com.example.streetworkout.Fragment.CalenderFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises.DetailsGroupExercises;
import com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises.GroupExercise;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseDaily;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ExercisesEachDayAdapter extends RecyclerView.Adapter<ExercisesEachDayAdapter.ExercisesViewHolder> {
    Context context;
    ArrayList<WeekExerciseDaily> mListWeek;
    WeekExerciseUser weekExerciseUsers;

    public ExercisesEachDayAdapter(Context context, ArrayList<WeekExerciseDaily> list, WeekExerciseUser weekExerciseUsers) {
        this.mListWeek = list;
        this.context = context;
        this.weekExerciseUsers = weekExerciseUsers;
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
        boolean ischeck = false;
        WeekExerciseDaily weekExerciseDaily = mListWeek.get(position);
        if (weekExerciseDaily == null) {
            return;
        }
        switch (position) {
            case 0:

                if (weekExerciseUsers != null)
                    ischeck = weekExerciseUsers.isDay1();
                break;
            case 1:

                if (weekExerciseUsers != null)
                    ischeck = weekExerciseUsers.isDay2();
                break;
            case 2:

                if (weekExerciseUsers != null)
                    ischeck = weekExerciseUsers.isDay3();
                break;
            case 3:

                if (weekExerciseUsers != null)
                    ischeck = weekExerciseUsers.isDay4();
                break;
            case 4:

                if (weekExerciseUsers != null)
                    ischeck = weekExerciseUsers.isDay5();
                break;
            case 5:

                if (weekExerciseUsers != null)
                    ischeck = weekExerciseUsers.isDay6();
                break;
            case 6:

                if (weekExerciseUsers != null)
                    ischeck = weekExerciseUsers.isDay7();
                break;
        }

        holder.txtCountDay.setText(String.valueOf(position + 1));

        holder.isFinish.setVisibility(ischeck ? View.VISIBLE : View.INVISIBLE);
        FirebaseDatabase.getInstance().getReference().child("GroupExercises").child("GroupExercise").child(mListWeek.get(position).getIdGroupExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                GroupExercise z = snapshot.getValue(GroupExercise.class);
                holder.txtNameDay.setText(z.getNameGroupExercise());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        Intent intent = new Intent(v.getContext(), DetailsGroupExercises.class);
                        intent.putExtra("getInfoIdGroup", z.getIdGroupExercise());
                        intent.putExtra("getNameExercise", z.getNameGroupExercise());
                        intent.putExtra("checkDayExercise", String.valueOf(position + 1));
                        v.getContext().startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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
        public ImageView isFinish;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameDay = itemView.findViewById(R.id.name_day);
            txtCountDay = itemView.findViewById(R.id.count_day);
            isFinish = itemView.findViewById(R.id.isFinish);
        }
    }

}
