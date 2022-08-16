package com.example.twitterclone.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.twitterclone.FetchTweet;
import com.example.twitterclone.fleet.Fleet;
import com.example.twitterclone.R;
import com.example.twitterclone.tweet.Tweet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Processor {

    Context context;
    List<Drawable> userImages;



    public Processor(Context context, List<Drawable> userImages) {
        this.context = context;
        this.userImages = userImages;

    }


    public void retrieveImage() {

        new RetrieveFleetTask(context, userImages).execute();
    }


    public void uploadImage(String imageName, Uri imageUri) {

        if (imageUri != null) {
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("fleets").child(imageName);

            ref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    new RetrieveFleetTask(context, userImages).execute();

                }
            });

        }
    }


    public List<Fleet> getStaticFleets() {

        List<Fleet> fleets = new ArrayList<>();

        Drawable image = context.getResources().getDrawable(R.drawable.story);
        Drawable image2 = context.getResources().getDrawable(R.drawable.trezor);
        Drawable image3 = context.getResources().getDrawable(R.drawable.ronaldo);
        Drawable image4 = context.getResources().getDrawable(R.drawable.ktl);

        List<Drawable> fleeImages = new ArrayList<>();
        fleeImages.add(image);
        fleeImages.add(image2);
        fleeImages.add(image4);

        Fleet fleetOne = new Fleet();
        fleetOne.setUserProfile(image2);
        fleetOne.setUserName("TresorKL");
        fleetOne.setFleetImages(fleeImages);


        Fleet fleetTwo = new Fleet();
        fleetTwo.setUserProfile(image);
        fleetTwo.setUserName("Enock");
        List<Drawable> fleeImages2 = new ArrayList<>();
        fleeImages2.add(image2);
        fleeImages2.add(image3);
        fleetTwo.setFleetImages(fleeImages2);


        Fleet fleetThree = new Fleet();
        fleetThree.setUserProfile(image4);
        fleetThree.setUserName("David");
        List<Drawable> fleeImages3 = new ArrayList<>();
        fleeImages3.add(image4);
        fleetThree.setFleetImages(fleeImages3);


        Fleet fleetFour = new Fleet();
        fleetFour.setUserProfile(image3);
        fleetFour.setUserName("Norbert");
        List<Drawable> fleeImages4 = new ArrayList<>();
        fleeImages4.add(image2);
        fleeImages4.add(image4);
        fleetFour.setFleetImages(fleeImages4);


        fleets.add(fleetOne);
        fleets.add(fleetTwo);
        fleets.add(fleetFour);
        fleets.add(fleetThree);

        return fleets;
    }

    public List<Tweet> getTweets( List<Tweet>tweetList) {


        // new FetchTweet(tweetList).execute();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tweets");

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Get map of users in datasnapshot
                       collectTweets((Map<String, Object>) dataSnapshot.getValue(),tweetList);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        return tweetList;
    }


    private   void collectTweets(Map<String, Object> value, List<Tweet>tweetList) {

        tweetList.clear();
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

        }



    }

}
