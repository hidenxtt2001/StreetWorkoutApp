package com.example.streetworkout.Fragment.TrainningFragment.Program;



import android.content.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.*;
import com.example.streetworkout.Fragment.TrainningFragment.*;
import com.example.streetworkout.R;



import java.util.*;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    Context context;
    ArrayList<ProgramExercise> programList = new ArrayList<>();



    public ProgramAdapter(Context context, ArrayList<ProgramExercise> programList) {
        this.context = context;
        this.programList = programList;

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_training_program_layout, parent, false);
        return new ProgramAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgramAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(programList.get(position).getLinkImageProgramExercise()).placeholder(R.color.colorTitle_1).into(holder.programImage);
        holder.programName.setText(programList.get(position).getNameProgramExercise());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked();
            }
        });

    }

    void clicked()
    {
        Intent programIntent= new Intent(context, ProgramIntent.class);
        context.startActivity(programIntent);

    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView programImage;
        TextView programName;


        public ViewHolder( View itemView) {
            super(itemView);
            programImage = itemView.findViewById(R.id.programs_viewpager2_imageView);
            programName = itemView.findViewById(R.id.program_name);



        }
    }
}
