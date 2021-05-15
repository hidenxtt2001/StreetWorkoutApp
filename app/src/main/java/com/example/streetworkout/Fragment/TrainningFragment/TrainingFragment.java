package com.example.streetworkout.Fragment.TrainningFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.Fragment.TrainningFragment.Knowledge.Knowledge;
import com.example.streetworkout.Fragment.TrainningFragment.Knowledge.knowledgeAdapter;
import com.example.streetworkout.Fragment.TrainningFragment.Program.Program;
import com.example.streetworkout.Fragment.TrainningFragment.Program.programAdapter;
import com.example.streetworkout.R;

import java.util.ArrayList;
import java.util.List;


public class TrainingFragment extends Fragment {


    public TrainingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.main_fragment_training, container, false);
        ViewPager2 programVP;
        programVP= view.findViewById(R.id.programViewpager);

        List<Program> list = new ArrayList<>();


        list.add(new Program(R.drawable.abs));
        list.add(new Program(R.drawable.back));
        list.add(new Program(R.drawable.abs));






        programVP.setClipToPadding(false);
        programVP.setClipChildren(false);
        programVP.setOffscreenPageLimit(3);
        programVP.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        programVP.setAdapter(new programAdapter(list,programVP));

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(30));
        programVP.setPageTransformer(transformer);



        RecyclerView View;
        ArrayList<Knowledge> arrayList = new ArrayList<>();
        knowledgeAdapter adapter;

        View=view.findViewById(R.id.Listknowledge);



        arrayList.add(new Knowledge(R.drawable.training_vector_bodybuilder,"Excercise Library"));

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        View.setLayoutManager(llm);

        adapter = new knowledgeAdapter(this.getContext(),arrayList);
        View.setAdapter(adapter);


        LayoutInflater layout=getLayoutInflater();
        View mainEx = layout.inflate(R.layout.training_main_excercise,null);
        FrameLayout formain;
        formain = view.findViewById(R.id.mainExcercise);
        formain.addView(mainEx);


       return  view;
    }
}