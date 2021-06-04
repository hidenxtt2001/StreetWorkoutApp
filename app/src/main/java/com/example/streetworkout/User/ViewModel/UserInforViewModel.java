package com.example.streetworkout.User.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExercise;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseDaily;
import com.example.streetworkout.Fragment.CalenderFragment.WeekExercise.WeekExerciseUser;
import com.example.streetworkout.Fragment.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserInforViewModel extends ViewModel {
    private MutableLiveData<WeekExerciseUser> weekExerciseUser;
    public LiveData<WeekExerciseUser> getWeekExerciseUser() { return weekExerciseUser;};
    private WeekExerciseUser mweekExerciseUser;

    private MutableLiveData<ArrayList<WeekExerciseDaily>> weekExerciseDaily;
    public LiveData<ArrayList<WeekExerciseDaily>> getWeekExerciseDaily() { return weekExerciseDaily;};
    private ArrayList<WeekExerciseDaily> mweekExerciseDaily;

    public UserInforViewModel(){
        weekExerciseUser = new MutableLiveData<WeekExerciseUser>();
        weekExerciseUser.setValue(mweekExerciseUser);

        weekExerciseDaily = new MutableLiveData<>();
        mweekExerciseDaily = new ArrayList<>();
        weekExerciseDaily.setValue(mweekExerciseDaily);

        if(mweekExerciseUser != null) return;
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        // find in WeekExercises in Firebase
        mRef.child("WeekExercises").child("WeekExerciseUser").child(MainActivity.userInfor.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if the data already exists
                if(snapshot.exists()){
                    mweekExerciseUser = snapshot.getValue(WeekExerciseUser.class);

                    mRef.child("WeekExercises").child("WeekExerciseDaily").orderByChild("idWeekExercise").equalTo(mweekExerciseUser.getIdWeekExercise()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            ArrayList<WeekExerciseDaily> temp = new ArrayList<>();
                            for (DataSnapshot snapshotChild : snapshot.getChildren())
                            {
                                temp.add(snapshotChild.getValue(WeekExerciseDaily.class));
                            }

                            mweekExerciseDaily.clear();
                            mweekExerciseDaily.addAll(temp);
                            weekExerciseUser.postValue(mweekExerciseUser);
                            weekExerciseDaily.postValue(mweekExerciseDaily);
                        }

                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {

                        }
                    });


                }
                // Create new Week Exercise for User
                else {
                    int checkLevel = (MainActivity.userInfor.getExperienceLevel() * 5) + 1;
                    mRef.child("WeekExercises").child("WeekExercise").limitToFirst(checkLevel).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            WeekExercise weekExercise = null;
                            if(snapshot.exists()){
                                for (DataSnapshot snap: snapshot.getChildren()
                                ) {
                                    weekExercise = snap.getValue(WeekExercise.class);
                                    break;
                                }
                                mweekExerciseUser = new WeekExerciseUser(weekExercise.getIdWeekExercise());
                                mRef.child("WeekExercises").child("WeekExerciseDaily").orderByChild("idWeekExercise").equalTo(mweekExerciseUser.getIdWeekExercise()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                        ArrayList<WeekExerciseDaily> temp = new ArrayList<>();
                                        for (DataSnapshot snapshotChild : snapshot.getChildren())
                                        {
                                            temp.add(snapshotChild.getValue(WeekExerciseDaily.class));
                                        }

                                        mweekExerciseDaily.clear();
                                        mweekExerciseDaily.addAll(temp);
                                        weekExerciseUser.postValue(mweekExerciseUser);
                                        weekExerciseDaily.postValue(mweekExerciseDaily);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull  DatabaseError error) {

                                    }
                                });
                                mRef.child("WeekExercises").child("WeekExerciseUser").child(MainActivity.userInfor.getUid()).setValue(weekExerciseUser);
                            }
                            else{}

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }
}
