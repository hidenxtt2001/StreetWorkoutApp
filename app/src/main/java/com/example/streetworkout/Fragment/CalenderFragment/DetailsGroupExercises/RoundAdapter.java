package com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.Exercise;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.ExerciseBodyPart;
import com.example.streetworkout.Fragment.TrainningFragment.Exercises.ExerciseLevelSkill;
import com.example.streetworkout.Fragment.TrainningFragment.getExLibValue;
import com.example.streetworkout.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Exercise> exercisesRound = new ArrayList<Exercise>();
    private BottomSheetDialog bottomSheetDialog;
    private getExLibValue bodyPart= new getExLibValue();
    private getExLibValue Lvl= new getExLibValue();
    private FirebaseDatabase reference;

    public RoundAdapter(Context context, ArrayList<Exercise>exercisesRound){
        this.context = context;
        this.exercisesRound = exercisesRound;
        bottomSheetDialog =new BottomSheetDialog(context,R.style.BottomSheetDialogTheme );
    }

    @Override
    public RoundAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_training_exercise_items, parent, false);
        return new RoundAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( RoundAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(exercisesRound.get(position).getLinkImage()).placeholder(R.drawable.wolf_icon_black).into(holder.disImage);
        holder.nameExercise.setText(exercisesRound.get(position).getNameExercise());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercisesRound.size();
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
    void clicked(View input, int pos) {
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.main_fragment_training_exerciselibrary_bottomsheetdialog, null, false);

        //init linearlayout
        FlexboxLayout levels;
        levels = bottomSheetView.findViewById(R.id.add_lvl);


        FlexboxLayout muscles;
        muscles = bottomSheetView.findViewById(R.id.add_muscle);

        //set item
        TextView setnameExercise = bottomSheetView.findViewById(R.id.title);
        setnameExercise.setText(exercisesRound.get(pos).getNameExercise());

        VideoView setvideoExercise = bottomSheetView.findViewById(R.id.vid);

        setvideoExercise.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        setvideoExercise.setVideoURI(Uri.parse(exercisesRound.get(pos).getLinkVideo().toString()));
        setvideoExercise.start();


        //add lvl
        reference = FirebaseDatabase.getInstance();
        Query query = reference.getReference("Exercises").child("ExerciseLevelSkill").orderByChild("idExercise").equalTo(exercisesRound.get(pos).getIdExercise());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()
                ) {

                    View setText = LayoutInflater.from(context).inflate(R.layout.main_fragment_training_items_layout, null, false);
                    TextView setLevel = setText.findViewById(R.id.exerciseItemLayout);
                    ExerciseLevelSkill exerciseLevelSkill = data.getValue(ExerciseLevelSkill.class);
                    if (exerciseLevelSkill != null) {

                        setLevel.setText(Lvl.Level(exerciseLevelSkill.getLevelValue()));
                        levels.addView(setText);
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query1 = reference.getReference("Exercises").child("ExerciseBodyPart").orderByChild("idExercise").equalTo(exercisesRound.get(pos).getIdExercise());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()
                ) {
                    //rename body_muscle_part
                    View setText = LayoutInflater.from(context).inflate(R.layout.main_fragment_training_items_layout, null, false);
                    //rename cho nay lun--------------------------------\/
                    TextView setbodypart = setText.findViewById(R.id.exerciseItemLayout);
                    ExerciseBodyPart exerciseBodyPart = data.getValue(ExerciseBodyPart.class);
                    if (exerciseBodyPart != null) {
                        setbodypart.setText(bodyPart.bodyPart(exerciseBodyPart.getBodyValue()));
                        muscles.addView(setText);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}
