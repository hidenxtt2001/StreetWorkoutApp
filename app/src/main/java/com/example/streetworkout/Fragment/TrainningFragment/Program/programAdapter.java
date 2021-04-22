package com.example.streetworkout.Fragment.TrainningFragment.Program;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.example.streetworkout.R;


import java.util.List;
import java.util.zip.Inflater;

public class programAdapter extends RecyclerView.Adapter<programAdapter.SliderViewHolder> {

    List<Program> items;
    ViewPager2 viewPager2;

    public programAdapter(List<Program> items, ViewPager2 viewPager2) {
        this.items = items;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.training_program_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImageView(items.get(position));
        if (position == items.size() - 2) {
            viewPager2.post(runnable);

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.programs_viewpager2_imageView);
        }

        void setImageView(Program program) {
            imageView.setBackgroundResource((program.getImage()));


        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            items.addAll(items);
            notifyDataSetChanged();
        }
    };


}
