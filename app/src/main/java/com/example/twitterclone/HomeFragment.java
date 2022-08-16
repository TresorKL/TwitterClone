package com.example.twitterclone;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twitterclone.adapters.TweetAdapter;
import com.example.twitterclone.fleet.Fleet;
import com.example.twitterclone.adapters.FleetAdapter;
import com.example.twitterclone.processor.Processor;
import com.example.twitterclone.processor.RetrieveTweetTask;
import com.example.twitterclone.tweet.Tweet;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {


    CircleImageView menu;
    CircleImageView addStoryBtn, postTweet;
    Fleet dynamicUser = new Fleet();
    List<Drawable> userImages = new ArrayList<>();
    List<Fleet> fleets = new ArrayList<>();
    List<Tweet> tweetList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // list of fleetsImage of the current user
        List<Drawable> userImages = new ArrayList<>();

        // clear every everything when this fragment is refreshed
        fleets.clear();
        userImages.clear();


        DrawerLayout drawerLayout = view.findViewById(R.id.homeDrawer);
        menu = view.findViewById(R.id.user);
        //----------------------------------
        // Open sidebar
        //----------------------------------
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = view.findViewById(R.id.myMenu);

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });


        // set current user manually
        Drawable userProfile = getResources().getDrawable(R.drawable.dynamic);
        Processor processor = new Processor(getContext(), userImages);
        dynamicUser.setUserProfile(userProfile);
        dynamicUser.setUserName("Your Story");
        // retrieve fleetsImage
        processor.retrieveImage();
        //set images
        dynamicUser.setFleetImages(userImages);

        // get static fleets from processor class
        fleets = processor.getStaticFleets();

        // add current user fleets
        fleets.add(0, dynamicUser);

        // filter valid fleets
        List<Fleet> validFleets = new ArrayList<>();
        for (int i = 0; i < fleets.size(); i++) {
            if (fleets.get(i).fleetImages != null) {
                //if(!fleets.get(i).fleetImages.isEmpty()) {
                validFleets.add(fleets.get(i));
                // }
            }
        }

        // optimize recycler view with adapter
        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.fleetRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        FleetAdapter adapter = new FleetAdapter(validFleets, getContext(), getActivity().getWindowManager());
        myRecyclerView.setLayoutManager(layoutManager);

        myRecyclerView.setAdapter(adapter);


        //tweetList.clear();

        // create static tweet
        Tweet tweet = new Tweet();
        tweet.setId(1);
        tweet.setUserName("Tresor");
        tweet.setTweetText("HELLO TWITTER");
        tweet.setUserProfileUrl("https://firebasestorage.googleapis.com/v0/b/twiterclone-52eea.appspot.com/o/fleets%2FIMG_1855.JPG?alt=media&token=5f01393c-22c9-4f77-a26a-3cc9c7113408");

        tweet.setTweetImgUrl("https://firebasestorage.googleapis.com/v0/b/twiterclone-52eea.appspot.com/o/fleets%2FIMG_1855.JPG?alt=media&token=5f01393c-22c9-4f77-a26a-3cc9c7113408");


        // get dynamic fleets from firebase
        tweetList = processor.getTweets(tweetList);

        // tweetList.add(tweet);

        // optimize flexible recycler view with adapter
        RecyclerView tweetRecycler = (RecyclerView) view.findViewById(R.id.tweetRecycler);


        TweetAdapter tweetAdapter = new TweetAdapter(tweetList, getContext());

        tweetRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        tweetRecycler.setAdapter(tweetAdapter);


        tweetRecycler.getRecycledViewPool().clear();
        // tweetAdapter.notifyDataSetChanged();

        //Log.d("TWEET LIST: ", "TWEETS AVAILABLE NOW!!!!!!!!!!!!!!!!!!!!!");

        //tweetAdapter.setTweetList(tweetList);

        // tweetAdapter.notifyDataSetChanged();
        //POST TWEET
        postTweet = view.findViewById(R.id.postTweet);
        postTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.post_tweet_dialog);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.TOP);

                TextView cancelTweet = dialog.findViewById(R.id.cancelTweet);
                cancelTweet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                ImageButton pickPicture = dialog.findViewById(R.id.pickPicture);
                pickPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 2);
                    }
                });


            }
        });


        // AddStoryRESULT_LOAD_IMG
        addStoryBtn = view.findViewById(R.id.AddStory);
        addStoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);

                // getActivity().finish();
                startActivity(getActivity().getIntent());


            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                // Drawable imageD = new BitmapDrawable(getContext().getResources(), selectedImage);

                ///-------------------------------------------------------
                ///---------------EXTRACT IMAGE NAME -------------------
                ///-------------------------------------------------------
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContext().getContentResolver().query(imageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                File f = new File(picturePath);
                String imageName = f.getName();


                Processor processor = new Processor(getContext(), userImages);
                ///-------------------------------------------------------
                ///---------------UPLOAD FLEET IMAGES  -------------------
                ///-------------------------------------------------------
                List<Drawable> userImages = new ArrayList<>();
                processor.uploadImage(imageName, imageUri);


                List<Fleet> validFleets = new ArrayList<>();
                for (int i = 0; i < fleets.size(); i++) {
                    if (fleets.get(i).fleetImages != null) {
                        if (!fleets.get(i).fleetImages.isEmpty()) {
                            validFleets.add(fleets.get(i));
                        }
                    }
                }

                RecyclerView myRecyclerView = (RecyclerView) getView().findViewById(R.id.fleetRecycler);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


                FleetAdapter adapter = new FleetAdapter(validFleets, getContext(), getActivity().getWindowManager());
                myRecyclerView.setLayoutManager(layoutManager);

                myRecyclerView.setAdapter(adapter);


                //Toast.makeText(getContext(), "Successfully added: " + imageName, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }


}