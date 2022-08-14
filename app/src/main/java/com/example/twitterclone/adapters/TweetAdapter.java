package com.example.twitterclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;
import com.example.twitterclone.fetchimage.FetchImage;
import com.example.twitterclone.tweet.Tweet;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    List<Tweet> tweetList;
    Context context;

    public TweetAdapter(List<Tweet> tweetList,Context context) {
        this.tweetList=tweetList;
        this.context=context;
    }

    @NonNull
    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Adapt different views

        if(viewType==1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_with_img, null);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_row, null);
            return new  ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TweetAdapter.ViewHolder holder, int position) {
        new FetchImage(tweetList.get(position).getUserProfileUrl(), holder.tweetProfile, context).start();
        holder.userName.setText(tweetList.get(position).getUserName());
        holder.tweetText.setText(tweetList.get(position).getTweetText());
        new FetchImage(tweetList.get(position).getTweetImgUrl(), holder.tweetImage, context);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        // viewType = 1 : with image, 2 : without image

        int viewType=1;
        if(tweetList.get(position).getTweetImgUrl()==null){
            viewType=2;
        }

        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView commentBtn,tweetImage;
        CircleImageView tweetProfile;
        TextView tweetText, userName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentBtn=itemView.findViewById(R.id.commentBtn);
            tweetImage=itemView.findViewById(R.id.tweetImage);
            tweetProfile=itemView.findViewById(R.id.tweetProfile);
            tweetText=itemView.findViewById(R.id.tweetText);
            userName=itemView.findViewById(R.id.tweetUserName);

        }
    }
}
