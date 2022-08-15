package com.example.twitterclone;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.adapters.TweetAdapter;
import com.example.twitterclone.tweet.Tweet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchTweet extends AsyncTask<String, String, String> {

    List<Tweet> tweetList = new ArrayList<>();


    public FetchTweet(List<Tweet> tweetList) {

        this.tweetList = tweetList;


    }

    @Override
    protected void onPostExecute(String s) {


    }

    @Override
    protected String doInBackground(String... strings) {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tweets");

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Get map of users in datasnapshot
                        collectTweets((Map<String, Object>) dataSnapshot.getValue(), tweetList);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


        return null;
    }


    private void collectTweets(Map<String, Object> value, List<Tweet> tweetList) {

        // value.get("id");
        for (Map.Entry<String, Object> tweetEntry : value.entrySet()) {
            Tweet tweet = new Tweet();
            // Log.d("DATA FOUND: ",tweetEntry.getValue().toString());
            Map<String, String> tweetData = (Map<String, String>) tweetEntry.getValue();

            for (Map.Entry<String, String> dataValue : tweetData.entrySet()) {


                if (dataValue.getKey().equals("id")) {
                    String strValue = String.valueOf(dataValue.getValue());
                    if (!strValue.toString().equals("")) {
                        int id = Integer.parseInt(strValue);
                        tweet.setId(id);
                    }

                } else if (dataValue.getKey().equals("parentId")) {
                    String strValue = String.valueOf(dataValue.getValue());
                    if (!strValue.equals("")) {
                        int parentId = Integer.parseInt(strValue);
                        tweet.setTweetParentId(parentId);
                    }


                } else if (dataValue.getKey().equals("tweetImgUrl")) {
                    tweet.setTweetImgUrl(dataValue.getValue());
                } else if (dataValue.getKey().equals("tweetText")) {
                    tweet.setTweetText(dataValue.getValue());
                } else if (dataValue.getKey().equals("userProfile")) {
                    tweet.setUserProfileUrl(dataValue.getValue());
                } else if (dataValue.getKey().equals("username")) {

                    tweet.setUserName(dataValue.getValue());
                }


            }

            tweetList.add(tweet);
            // Log.d("TWEET LIST: ", "TWEETS AVAILABLE NOW!!!!!!!!!!!!!!!!!!!!!");
        }

        for (int i = 0; i < tweetList.size(); i++) {
            Log.d("SIZE LIST: ", tweetList.get(i).getUserName() + "!!!!!!!!!!!!!!!!!!!!!");
        }
    }
}
