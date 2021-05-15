package com.example.streetworkout.Fragment.TrainningFragment.Exercises;

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
import com.example.streetworkout.Fragment.TrainningFragment.getExLibValue;
import com.example.streetworkout.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;
import java.util.ArrayList;

public class exerciseAdapter extends RecyclerView.Adapter<exerciseAdapter.ViewHolder> {


    Context context;
    ArrayList<Exercise> exercises = new ArrayList<>();
    FirebaseDatabase reference;

    getExLibValue bodyPart= new getExLibValue();
    getExLibValue Lvl= new getExLibValue();
    private BottomSheetDialog bottomSheetDialog;
    public exerciseAdapter(Context context) {
        this.context = context;
        bottomSheetDialog =new BottomSheetDialog(context,R.style.BottomSheetDialogTheme );
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_training_exercise_items, parent, false);
        return new ViewHolder(view);
    }

    void clicked(View input, int pos) {
        if(bottomSheetDialog.isShowing()) return;
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.main_fragment_training_exerciselibrary_bottomsheetdialog, null, false);

        //init linearlayout
        FlexboxLayout levels;
        levels = bottomSheetView.findViewById(R.id.add_lvl);


        FlexboxLayout muscles;
        muscles = bottomSheetView.findViewById(R.id.add_muscle);

        //set item
        TextView setnameExercise = bottomSheetView.findViewById(R.id.title);
        setnameExercise.setText(exercises.get(pos).getNameExercise());

        ScalableVideoView setvideoExercise = bottomSheetView.findViewById(R.id.vid);
        try {

            setvideoExercise.setDataSource(exercises.get(pos).getLinkVideo().toString());
            setvideoExercise.setLooping(true);
            setvideoExercise.prepare(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    setvideoExercise.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        //add lvl
        reference = FirebaseDatabase.getInstance();
        Query query = reference.getReference("Exercises").child("ExerciseLevelSkill").orderByChild("idExercise").equalTo(exercises.get(pos).getIdExercise());
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

        Query query1 = reference.getReference("Exercises").child("ExerciseBodyPart").orderByChild("idExercise").equalTo(exercises.get(pos).getIdExercise());
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


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*Picasso.get().load(exercises.get(position).getLinkImage()).placeholder(R.color.black).into(holder.disImage);*/
        Glide.with(context).load(exercises.get(position).getLinkImage()).placeholder(R.color.colorMain_2).into(holder.disImage);
        holder.nameExercise.setText(exercises.get(position).getNameExercise());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void addExerciseItem(Exercise exercise) {
        exercises.add(exercise);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameExercise;
        ImageView disImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameExercise = itemView.findViewById(R.id.nameExercise);
            disImage = itemView.findViewById(R.id.disImage);

        }
    }


}
