package com.example.streetworkout.Fragment.NewsFeedFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.streetworkout.Fragment.AccountFragment.AccountFragment;
import com.example.streetworkout.Fragment.AccountFragment.AccountFragmentAdapter;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.example.streetworkout.User.UserInfor;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NewsFeedFragment extends Fragment {

    View root;
    public static UserInfor userInfor;
    RecyclerView recyclerView;
    ArrayList<StatusWorkout> listStatus;
    AccountFragmentAdapter accountFragmentAdapter;

    public NewsFeedFragment() {
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
        root = inflater.inflate(R.layout.main_fragment_newsfeed, container, false);

        SetUpInfor();

        return root;

    }

    private void SetUpInfor() {

        recyclerView = root.findViewById(R.id.recyclerViewNewsFeed);

        listStatus = new ArrayList<>();

        SetStatus();

        accountFragmentAdapter = new AccountFragmentAdapter(NewsFeedFragment.this.getContext(),listStatus);
        recyclerView.setAdapter(accountFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(NewsFeedFragment.this.getContext()));
    }

    private void SetStatus() {

        FirebaseDatabase.getInstance().getReference().child("StatusUserExercise").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StatusWorkout k = snapshot.getValue(StatusWorkout.class);
                FirebaseDatabase.getInstance().getReference().child("UserInfos").child(k.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        k.setUserInfor(snapshot.getValue(UserInfor.class));
                        listStatus.add(k);
                        accountFragmentAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}