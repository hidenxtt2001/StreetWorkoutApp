package com.example.streetworkout.Fragment.TrainningFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.streetworkout.Fragment.TrainningFragment.Knowledge.Knowledge;
import com.example.streetworkout.Fragment.TrainningFragment.Knowledge.knowledgeAdapter;
import com.example.streetworkout.Fragment.TrainningFragment.Program.ProgramExercise;
import com.example.streetworkout.Fragment.TrainningFragment.Program.ProgramAdapter;
import com.example.streetworkout.R;
import com.google.firebase.database.*;

import java.util.ArrayList;


public class TrainingFragment extends Fragment {
    ProgramAdapter programAdapter ;

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
        RecyclerView programVP;
        programVP= view.findViewById(R.id.programViewpager);

        ArrayList<ProgramExercise> list = new ArrayList<>();

        programAdapter =new ProgramAdapter(this.getActivity(),list);

        programVP.setAdapter(programAdapter);


        FirebaseDatabase.getInstance().getReference("ProgramExercises").child("ProgramExercise").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()
                     ) {
                    list.add(snap.getValue(ProgramExercise.class));
                    programAdapter.notifyDataSetChanged();

                }

            }
            @Override
            public void onCancelled( DatabaseError error) {

            }
        });






        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        programVP.setLayoutManager(linearLayoutManager);


        RecyclerView View;
        ArrayList<Knowledge> arrayList = new ArrayList<>();
        knowledgeAdapter adapter;

        View=view.findViewById(R.id.Listknowledge);



        arrayList.add(new Knowledge(R.drawable.training_vector_bodybuilder,"Excercise Library"));

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        View.setLayoutManager(llm);

        adapter = new knowledgeAdapter(this.getActivity(),arrayList);
        View.setAdapter(adapter);


        LayoutInflater layout=getLayoutInflater();
        View mainEx = layout.inflate(R.layout.main_fragment_training_main_excercise,null);
        FrameLayout formain;
        formain = view.findViewById(R.id.mainExcercise);
        formain.addView(mainEx);


       return  view;
    }
}