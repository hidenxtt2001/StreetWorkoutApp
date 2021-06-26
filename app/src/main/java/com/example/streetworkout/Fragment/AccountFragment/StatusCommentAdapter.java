package com.example.streetworkout.Fragment.AccountFragment;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streetworkout.Fragment.AccountFragment.Comments.Comment;
import com.example.streetworkout.R;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusCommentAdapter extends RecyclerView.Adapter<StatusCommentAdapter.ViewHolder> {

    Context context;
    ArrayList<Comment> comments;

    public StatusCommentAdapter(Context context, ArrayList<Comment> comments){
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @NotNull
    @Override
    public StatusCommentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_fragment_account_status_comments_item, parent, false);
        return new StatusCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StatusCommentAdapter.ViewHolder holder, int position) {
        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Comment comment = comments.get(position);
        Date now = Calendar.getInstance().getTime();
        try {
            holder.durationAgo.setText(toDuration((now.getTime() - parser.parse(comment.getDateCreate()).getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context).load(Uri.parse(comment.userInfor.getUrlAvatar())).into((holder.avatar));
        holder.uidProfile.setText(comment.userInfor.getUserName());
        holder.comment.setText(comment.getContent());
    }

    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1) );
    public static final List<String> timesString = Arrays.asList("year","month","day","hour","minute","second");

    public static String toDuration(long duration) {

        StringBuffer res = new StringBuffer();
        for(int i=0;i< times.size(); i++) {
            Long current = times.get(i);
            long temp = duration/current;
            if(temp>0) {
                res.append(temp).append(" ").append( timesString.get(i) ).append(temp > 1 ? "s" : "").append(" ago");
                break;
            }
        }
        if("".equals(res.toString()))
            return "0 second ago";
        else
            return res.toString();
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avatar;
        TextView uidProfile;
        TextView comment;
        TextView durationAgo;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatarProfile);
            uidProfile = itemView.findViewById(R.id.uidProfile);
            comment = itemView.findViewById(R.id.statusComment);
            durationAgo = itemView.findViewById(R.id.durationAgo);
        }
    }
}
