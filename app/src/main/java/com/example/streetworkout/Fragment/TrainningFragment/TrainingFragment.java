package com.example.streetworkout.Fragment.TrainningFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises.DetailsGroupExercises;
import com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises.GroupExercise;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseDaily;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.Fragment.TrainningFragment.Knowledge.Knowledge;
import com.example.streetworkout.Fragment.TrainningFragment.Knowledge.knowledgeAdapter;
import com.example.streetworkout.Fragment.TrainningFragment.Program.ProgramExercise;
import com.example.streetworkout.Fragment.TrainningFragment.Program.ProgramAdapter;
import com.example.streetworkout.R;
import com.google.firebase.database.*;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class TrainingFragment extends Fragment {
    ProgramAdapter programAdapter;
    View mainEx;
    LinearLayout sss;

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
        View view = inflater.inflate(R.layout.main_fragment_training, container, false);
        RecyclerView programVP;
        programVP = view.findViewById(R.id.programViewpager);

        ArrayList<ProgramExercise> list = new ArrayList<>();

        programAdapter = new ProgramAdapter(this.getActivity(), list);

        programVP.setAdapter(programAdapter);


        FirebaseDatabase.getInstance().getReference("ProgramExercises").child("ProgramExercise").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()
                ) {
                    list.add(snap.getValue(ProgramExercise.class));
                    programAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        programVP.setLayoutManager(linearLayoutManager);


        RecyclerView View;
        ArrayList<Knowledge> arrayList = new ArrayList<>();
        knowledgeAdapter adapter;

        View = view.findViewById(R.id.Listknowledge);


        arrayList.add(new Knowledge(R.drawable.training_vector_bodybuilder, "Excercise Library"));

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        View.setLayoutManager(llm);

        adapter = new knowledgeAdapter(this.getActivity(), arrayList);
        View.setAdapter(adapter);

        mainEx = getLayoutInflater().inflate(R.layout.main_fragment_training_main_excercise, null);
        sss = view.findViewById(R.id.mainExcercise);
        sss.addView(mainEx);




        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestroy = true;
    }

    boolean isDestroy =false;
    @Override
    public void onStart() {
        super.onStart();
        MainActivity.userInforViewModel.getWeekExerciseDaily().observe(this.getActivity(), new Observer<ArrayList<WeekExerciseDaily>>() {
            @Override
            public void onChanged(ArrayList<WeekExerciseDaily> weekExerciseDailies) {

                WeekExerciseUser u = MainActivity.userInforViewModel.getWeekExerciseUser().getValue();
                if (u == null) return;
                if (weekExerciseDailies.size() != 7) return;
                int i = !u.isDay1() ? 0 : !u.isDay2() ? 1 : !u.isDay3() ? 2 : !u.isDay4() ? 3 : !u.isDay5() ? 4 : !u.isDay6() ? 5 : 6;

                WeekExerciseDaily k = weekExerciseDailies.get(i);


                ImageView image = mainEx.findViewById(R.id.imageGroup);
                TextView name = mainEx.findViewById(R.id.name);

                FirebaseDatabase.getInstance().getReference().child("GroupExercises").child("GroupExercise").child(k.getIdGroupExercise()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        GroupExercise z = snapshot.getValue(GroupExercise.class);

                        name.setText(z.getNameGroupExercise());
                        mainEx.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(android.view.View v) {
                                Intent intent = new Intent(v.getContext(), DetailsGroupExercises.class);
                                intent.putExtra("getInfoIdGroup", z.getIdGroupExercise());
                                intent.putExtra("getNameExercise", z.getNameGroupExercise());
                                intent.putExtra("checkDayExercise", String.valueOf(i + 1));
                                v.getContext().startActivity(intent);
                            }
                        });
                        if(!isDestroy)
                        Glide.with(getActivity().getApplicationContext()).load(z.getLinkImageGroup()).placeholder(R.color.black).into(image);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


            }
        });
    }
}