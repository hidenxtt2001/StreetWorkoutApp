package com.example.streetworkout.Fragment.AccountFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises.GroupExercise;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatusExerciseAdapter extends RecyclerView.Adapter<StatusExerciseAdapter.MyViewHolder>{

    Context context;
    ArrayList<StatusWorkout> listStatus;


    public StatusExerciseAdapter(Context context, ArrayList<StatusWorkout> listStatus) {
        this.context = context;
        this.listStatus = listStatus;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_fragment_account_status_exercise, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  StatusExerciseAdapter.MyViewHolder holder, int position) {
        final boolean[] loadFinish = {false};
        StatusWorkout statusWorkout = listStatus.get(position);
        holder.name.setText(statusWorkout.getUserInfor().getUserName());
        Glide.with(context).load(Uri.parse(statusWorkout.getUserInfor().getUrlAvatar())).into((holder.avt));
        FirebaseDatabase.getInstance().getReference().child("GroupExercises").child("GroupExercise").child(statusWorkout.getIdGroupExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                GroupExercise k = snapshot.getValue(GroupExercise.class);
                Glide.with(context).load(Uri.parse(k.getLinkImageGroup())).into((holder.imageGroup));
                holder.myText1.setText(k.getNameGroupExercise());
                holder.textStatus.setText(statusWorkout.getDateComplate());
                loadFinish[0] = true;
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        holder.cardStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!loadFinish[0]) return;
                Intent status = new Intent(context,StatusComment.class);
                status.putExtra("status",listStatus.get(position));
                ((Activity)context).startActivity(status);
                ((Activity)context).overridePendingTransition(R.anim.from_bottom_up_light,R.anim.to_top_light);
            }
        });





    }

    @Override
    public int getItemCount() {
        return listStatus.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1, myText2, name, textStatus;
        ImageView avt,imageGroup;
        LinearLayout cardStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.txt1);
            myText2 = itemView.findViewById(R.id.txt2);
            name = itemView.findViewById(R.id.uidProfile);
            textStatus = itemView.findViewById(R.id.status);
            avt = itemView.findViewById(R.id.avatarProfile2);
            imageGroup = itemView.findViewById(R.id.imageGroup);
            cardStatus = itemView.findViewById(R.id.cardStatus);
        }
    }
}