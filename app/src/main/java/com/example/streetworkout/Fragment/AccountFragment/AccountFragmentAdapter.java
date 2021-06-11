package com.example.streetworkout.Fragment.AccountFragment;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.example.streetworkout.User.UserInfor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragmentAdapter extends RecyclerView.Adapter<AccountFragmentAdapter.MyViewHolder>{

    Context context;
    ArrayList<StatusWorkout> listStatus;


    public AccountFragmentAdapter(Context context, ArrayList<StatusWorkout> listStatus) {
        this.context = context;
        this.listStatus = listStatus;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_fragment_account_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AccountFragmentAdapter.MyViewHolder holder, int position) {
        StatusWorkout statusWorkout = listStatus.get(position);

        Glide.with(context).load(Uri.parse(statusWorkout.getUserInfor().getUrlAvatar())).into((holder.avt));


        holder.name.setText(statusWorkout.getUserInfor().getUserName());

        holder.textStatus.setText(statusWorkout.getIdStatus());
    }

    @Override
    public int getItemCount() {
        return listStatus.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1, myText2, name, textStatus;
        ImageView avt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.txt1);
            myText2 = itemView.findViewById(R.id.txt2);
            name = itemView.findViewById(R.id.uidProfile);
            textStatus = itemView.findViewById(R.id.status);
            avt = itemView.findViewById(R.id.avatarProfile2);
        }
    }
}
