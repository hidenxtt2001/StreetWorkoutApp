package com.example.streetworkout.Login.UserInformation.FragmentInput;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streetworkout.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class LevelSkillAdapter extends RecyclerView.Adapter<LevelSkillAdapter.ViewHolder> {

    private final ArrayList<LevelSkillItem> levelSkillItems;
    public static int chooseItem = 0;
    Context context;
    public LevelSkillAdapter(Context context, ArrayList<LevelSkillItem> levelSkillItems) {
        this.levelSkillItems = levelSkillItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_input_user_fragment_level_item,parent,false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LevelSkillItem item = levelSkillItems.get(position);
        holder.imageLevel.setImageResource(item.getImageLevel());
        holder.nameLevel.setText(item.getNameLevel());
        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseItem = position;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();

            }
        });
        if(position == chooseItem){
            holder.imageSelect.setImageResource(R.drawable.login_create_account_mark_check);
        }
        else{
            holder.imageSelect.setImageResource(R.drawable.login_create_account_mark_uncheck);
        }
    }

    @Override
    public int getItemCount() {
        return levelSkillItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageLevel;
        ImageView imageSelect;
        TextView nameLevel;
        RelativeLayout itemContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLevel = itemView.findViewById(R.id.imgLevel);
            imageSelect = itemView.findViewById(R.id.checkSelect);
            nameLevel = itemView.findViewById(R.id.nameLevel);
            itemContainer = itemView.findViewById(R.id.itemLevelContainer);
        }
    }
}
