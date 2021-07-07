package com.example.streetworkout.Fragment.AccountFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.CalenderFragment.DetailsGroupExercises.GroupExercise;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.example.streetworkout.User.UserInfor;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AccountWatchOtherUser extends AppCompatActivity {

    RecyclerView recyclerViewOtherUser;
    ArrayList<StatusWorkout> listOtherUserStatus;
    StatusExerciseAdapter statusExerciseAdapter;
    UserInfor otherUserInfo;
    Toolbar toolbarOtherUser;
    TextView nameDisplay, userName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_account_watch_other_user);
        toolbarOtherUser = findViewById(R.id.toolBarOtherUser);

        nameDisplay = findViewById(R.id.nameOtherUserDislay);
        userName = findViewById(R.id.otherUserName);

        // set tittle toolbar = null
        toolbarOtherUser.setTitle("");
        // set button call back activity before
        setSupportActionBar(toolbarOtherUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpInfoDataOtherUser();



    }

    private void setUpInfoDataOtherUser() {
        String getInfoOtherUser = getIntent().getStringExtra("infoUser");
        FirebaseDatabase.getInstance().getReference().child("UserInfos").child(getInfoOtherUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                otherUserInfo = snapshot.getValue(UserInfor.class);
                userName.setText(otherUserInfo.getUserName());
                nameDisplay.setText(otherUserInfo.getDisplayName());

                switch (otherUserInfo.getLoginTypes()){
                    case 1:
                        Glide.with(getApplicationContext()).load(Uri.parse(otherUserInfo.getUrlAvatar())).into((ImageView)findViewById(R.id.avatarOtherUserProfile));
                        break;
                    case 2:
                        GraphRequest request = GraphRequest.newGraphPathRequest(
                                AccessToken.getCurrentAccessToken(),
                                "/" + otherUserInfo.getUrlAvatar().replaceAll("\\D+","") + "/picture?redirect=0&type=large",
                                new GraphRequest.Callback() {
                                    @Override
                                    public void onCompleted(GraphResponse response) {
                                        JSONObject result = response.getJSONObject();
                                        try {
                                            String link = result.getJSONObject("data").getString("url").toString();
                                            Glide.with(getApplicationContext()).load(Uri.parse(link)).into((ImageView)findViewById(R.id.avatarOtherUserProfile));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        request.executeAsync();
                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        recyclerViewOtherUser = findViewById(R.id.recyclerOtherUserView);
        listOtherUserStatus = new ArrayList<>();

        setStatus();

        statusExerciseAdapter = new StatusExerciseAdapter(this,listOtherUserStatus);
        recyclerViewOtherUser.setAdapter(statusExerciseAdapter);
        recyclerViewOtherUser.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));


    }


    private void setStatus() {
        String getInfoOtherUser = getIntent().getStringExtra("infoUser");
        FirebaseDatabase.getInstance().getReference().child("StatusExercise").child("StatusUserExercise").orderByChild("uid").equalTo(getInfoOtherUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listOtherUserStatus.clear();
                for ( DataSnapshot i : snapshot.getChildren()) {
                    StatusWorkout k = i.getValue(StatusWorkout.class);
                    k.setIdStatus(i.getKey());
                    FirebaseDatabase.getInstance().getReference().child("UserInfos").child(k.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
                            k.setUserInfor(snapshot.getValue(UserInfor.class));

                            listOtherUserStatus.add(k);
                            Collections.sort(listOtherUserStatus, new Comparator<StatusWorkout>() {
                                public int compare(StatusWorkout o1, StatusWorkout o2) {
                                    try {
                                        return parser.parse(o2.getDateComplate()).compareTo(parser.parse(o1.getDateComplate()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    return 0;
                                }
                            });
                            statusExerciseAdapter.notifyDataSetChanged();
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



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}