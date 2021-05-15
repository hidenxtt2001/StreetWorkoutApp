package com.example.streetworkout.Fragment.TrainningFragment.Knowledge;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.streetworkout.Fragment.TrainningFragment.ExerciseLibrary;
import com.example.streetworkout.R;

import java.util.List;

public class knowledgeAdapter extends RecyclerView.Adapter<knowledgeAdapter.ViewHolder> {

    Context context;
    List<Knowledge> arraylist;

    public knowledgeAdapter(Context context, List<Knowledge> arraylist) {
        this.arraylist = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public knowledgeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.main_fragment_training_knowledge_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull knowledgeAdapter.ViewHolder holder, int position) {
        holder.setImageView(arraylist.get(position));
        holder.setTextView(arraylist.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
clicked();
            }
        });
    }

    void clicked()
    {
        Intent exerciseLibrary= new Intent(context, ExerciseLibrary.class);
        context.startActivity(exerciseLibrary);

    }
    @Override
    public int getItemCount() {
        return arraylist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagelist;
        TextView titlelist;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagelist = itemView.findViewById(R.id.imageKnowledge);
            titlelist= itemView.findViewById(R.id.textTitle);
        }


        void setImageView(Knowledge knowledge) {
            imagelist.setBackgroundResource((knowledge.getImage()));

        }
        void setTextView(Knowledge knowledge)
        {
            titlelist.setText(knowledge.getName());

        }
    }

}
