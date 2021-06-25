package com.example.streetworkout.Fragment.NewsFeedFragment;

import android.graphics.Path;
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
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.example.streetworkout.User.UserInfor;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


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

        FirebaseDatabase.getInstance().getReference().child("StatusUserExercise").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listStatus.clear();
                for ( DataSnapshot i : snapshot.getChildren()) {

                    StatusWorkout k = i.getValue(StatusWorkout.class);
                    if(k.getUid().equals(MainActivity.userInfor.getUid())) continue;
                    FirebaseDatabase.getInstance().getReference().child("UserInfos").child(k.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyy");
                            k.setUserInfor(snapshot.getValue(UserInfor.class));
                            listStatus.add(k);
                            Collections.sort(listStatus, new Comparator<StatusWorkout>() {
                                public int compare(StatusWorkout o1, StatusWorkout o2) {
                                    try {
                                        return parser.parse(o2.getDateComplate()).compareTo(parser.parse(o1.getDateComplate()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    return 0;
                                }
                            });
                            accountFragmentAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}