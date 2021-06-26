package com.example.streetworkout.Fragment.AccountFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.streetworkout.Fragment.AccountFragment.Comments.Comment;
import com.example.streetworkout.Fragment.MainActivity;
import com.example.streetworkout.R;
import com.example.streetworkout.StatusWorkout.StatusWorkout;
import com.example.streetworkout.User.UserInfor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class StatusComment extends AppCompatActivity {

    Toolbar toolbar;
    EditText commentText;
    ImageView sendComment;
    RecyclerView recyclerView;
    StatusCommentAdapter statusCommentAdapter;
    ArrayList<Comment> comments;
    StatusWorkout statusWorkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_account_status_comments);
        toolbar = findViewById(R.id.toolbarCustom);
        toolbar.setTitle("");
        statusWorkout = (StatusWorkout)this.getIntent().getSerializableExtra("status");
        commentText = findViewById(R.id.commentText);
        sendComment = findViewById(R.id.sender);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkValidComment()) return;
                SendComment();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        comments = new ArrayList<>();
        statusCommentAdapter = new StatusCommentAdapter(this,comments);
        recyclerView = findViewById(R.id.loadComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(statusCommentAdapter);
        ListenCommentChange();
    }

    private void SendComment(){
        String commentId = UUID.randomUUID().toString();
        String content = commentText.getText().toString();
        commentText.setText("");
        FirebaseDatabase.getInstance().getReference().child("StatusUserExerciseComments").child(statusWorkout.getIdStatus()).child(commentId).setValue(new Comment(commentId, MainActivity.userInfor.getUid(),content,  Calendar.getInstance().getTime().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    private void ListenCommentChange(){
        FirebaseDatabase.getInstance().getReference().child("StatusUserExerciseComments").child(statusWorkout.getIdStatus()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(!snapshot.exists()) return;
                SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Comment comment = snapshot.getValue(Comment.class);
                FirebaseDatabase.getInstance().getReference().child("UserInfos").child(comment.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        if(!snapshot.exists()) return;
                        comment.userInfor = snapshot.getValue(UserInfor.class);
                        comments.add(comment);
                        Collections.sort(comments, new Comparator<Comment>() {
                            public int compare(Comment o1, Comment o2) {
                                try {
                                    return parser.parse(o2.getDateCreate()).compareTo(parser.parse(o1.getDateCreate()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return 0;
                            }
                        });
                        statusCommentAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private boolean checkValidComment(){
        if(commentText.getText().toString().trim().length() == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.to_down_light,R.anim.from_bottom_down_light);
    }
}