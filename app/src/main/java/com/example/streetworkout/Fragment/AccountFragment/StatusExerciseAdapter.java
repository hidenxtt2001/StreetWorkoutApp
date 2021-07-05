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
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        final boolean[] isLoadLove = {false};
        final boolean[] isLove = {false};
        final String[] idLove = {null};
        FirebaseDatabase.getInstance().getReference().child("StatusExercise").child("StatusUserExerciseLove").child(statusWorkout.getIdStatus()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                isLoadLove[0] = true;

                if(!snapshot.exists()){
                    holder.textCountLove.setText("0");
                    return;
                }

                String count = String.valueOf(snapshot.getChildrenCount());
                holder.textCountLove.setText(count);

                for(DataSnapshot ds : snapshot.getChildren()) {
                    String uid = ds.getValue(String.class);
                    if(uid == MainActivity.userInfor.getUid()){
                        isLove[0] = true;
                        holder.loveStatus.setImageResource(R.drawable.status_exercise_love_enable);
                        idLove[0] = ds.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        final boolean[] isUpdateLove = {false};
        holder.loveStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLoadLove[0])
                    return;
                if(isUpdateLove[0]) return;
                if(isLove[0]){

                    isLove[0] = !isLove[0];
                    holder.loveStatus.setImageResource(R.drawable.status_exercise_love_disable);
                    FirebaseDatabase.getInstance().getReference().child("StatusExercise").child("StatusUserExerciseLove").child(statusWorkout.getIdStatus()).child(idLove[0]).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            isUpdateLove[0] = false;

                        }
                    });
                }
                else{
                    isLove[0] = !isLove[0];
                    holder.loveStatus.setImageResource(R.drawable.status_exercise_love_enable);
                    String key = FirebaseDatabase.getInstance().getReference().child("StatusExercise").child("StatusUserExerciseLove").child(statusWorkout.getIdStatus()).push().getKey();
                    Map<String, Object> map = new HashMap<>();
                    map.put(key, MainActivity.userInfor.getUid());
                    FirebaseDatabase.getInstance().getReference().child("StatusExercise").child("StatusUserExerciseLove").child(statusWorkout.getIdStatus()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            isUpdateLove[0] = false;

                        }
                    });
                }




            }
        });







    }

    @Override
    public int getItemCount() {
        return listStatus.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1, myText2, name, textStatus, textCountLove;
        ImageView avt,imageGroup, loveStatus;
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
            loveStatus = itemView.findViewById(R.id.loveStatus);
            textCountLove = itemView.findViewById(R.id.countLoveStatus);
        }
    }
}
