package com.example.twitterclone.tweet;

import android.app.Dialog;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.twitterclone.processor.RetrieveFleetTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class TweetProcessor {
   // String tweetImgUrl;
    String tweetText,  imageName;
    Uri imageUri;
    Dialog dialog;


    public TweetProcessor( String tweetText, String imageName, Uri imageUri, Dialog dialog) {
        this.tweetText=tweetText;
        this.imageName=imageName;
        this.imageUri=imageUri;
        this.dialog=dialog;

    }

    public void postSimpleTweet() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Tweets");
        Tweet tweet = new Tweet();
        tweet.setUserName("Tresor KL");
        tweet.setTweetText(tweetText);



        String profileUrl ="https://firebasestorage.googleapis.com/v0/b/twiterclone-52eea.appspot.com/o/fleets%2Fgym.jpeg?alt=media&token=109177f3-d070-400d-a4d6-fa91e69a4d96";
        tweet.setUserProfileUrl(profileUrl);


        int id=generateRandomId();
        tweet.setId(id);

        HashMap<String,Object> map=new HashMap<>();

        map.put("id",tweet.getId());
        map.put("parentId",tweet.getTweetParentId());
        map.put("tweetImgUrl","");
        map.put("tweetText",tweet.getTweetText());
        map.put("userProfile",tweet.getUserProfileUrl());
        map.put("username",tweet.getUserName());

        databaseReference.push().updateChildren(map);

        dialog.dismiss();

    }


    public void storeAndFetchImageUrl() {



        if (imageUri != null) {
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("tweetImages").child(imageName);

            ref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = uri.toString();


                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Tweets");
                            Tweet tweet = new Tweet();
                            tweet.setUserName("Dan MK");
                            tweet.setTweetText(tweetText);


                            tweet.setTweetImgUrl(url);

                            String profileUrl ="https://firebasestorage.googleapis.com/v0/b/twiterclone-52eea.appspot.com/o/tweetImages%2Fgoodprof.PNG?alt=media&token=b6e8053b-8724-41fd-abc3-9e2463cd81c3";
                            tweet.setUserProfileUrl(profileUrl);


                            int id=generateRandomId();
                            tweet.setId(id);

                            HashMap<String,Object> map=new HashMap<>();

                            map.put("id",tweet.getId());
                            map.put("parentId",tweet.getTweetParentId());
                            map.put("tweetImgUrl",tweet.getTweetImgUrl());
                            map.put("tweetText",tweet.getTweetText());
                            map.put("userProfile",tweet.getUserProfileUrl());
                            map.put("username",tweet.getUserName());

                            databaseReference.push().updateChildren(map);

                            Log.d("TWEET GONE", url);
                        }
                    });

                }
            });

        }
        dialog.dismiss();

    }

    private int generateRandomId(){
        int num=0;

        int min = 1;
        int max = 100;

        //Generate random int value from 50 to 100
       // System.out.println("Random value in int from "+min+" to "+max+ ":");
        num = (int)Math.floor(Math.random()*(max-min+1)+min);

        return num;

    }

}
