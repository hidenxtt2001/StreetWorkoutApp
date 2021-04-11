package com.example.streetworkout.Login.UserInformation.FragmentInput;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.streetworkout.Login.UserInformation.InputUserProfile;
import com.example.streetworkout.R;

import java.util.ArrayList;
import java.util.Objects;


public class InputUserLevel extends Fragment {


    View root;
    LevelSkillAdapter levelSkillAdapter;


    public InputUserLevel() {
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
        root = inflater.inflate(R.layout.login_input_user_fragment_level, container, false);

        root.findViewById(R.id.succesProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputUserProfile.userInfor.setExperienceLevel(LevelSkillAdapter.chooseItem);
                ((InputUserProfile) requireActivity()).PutDataUser();
            }
        });

        SetupLevelSkill();
        return root;
    }

    private void SetupLevelSkill() {
        ArrayList<LevelSkillItem> levelSkillItems = new ArrayList<>();
        levelSkillItems.add(new LevelSkillItem(R.drawable.login_create_account_level_beginer, "Beginer"));
        levelSkillItems.add(new LevelSkillItem(R.drawable.login_create_account_level_intermediate, "Intermediate"));
        levelSkillItems.add(new LevelSkillItem(R.drawable.login_create_account_level_advanced, "Advance"));
        RecyclerView recyclerView = root.findViewById(R.id.listLevel);
        LinearLayoutManager llm = new LinearLayoutManager(root.getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        levelSkillAdapter = new LevelSkillAdapter(root.getContext(), levelSkillItems);
        recyclerView.setAdapter(levelSkillAdapter);
    }
}