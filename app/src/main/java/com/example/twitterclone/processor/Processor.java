package com.example.twitterclone.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.twitterclone.fleet.Fleet;
import com.example.twitterclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class Processor {

    Context context;


    public Processor(Context context) {
        this.context = context;

    }


    public void retrieveImage(List<Drawable> userImages) {
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child("fleets/");


        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        if (listResult != null) {
                            for (StorageReference prefix : listResult.getPrefixes()) {
                                // All the prefixes under listRef.
                                // You may call listAll() recursively on them.
                            }
                            int i = 0;

                            for (StorageReference item : listResult.getItems()) {

                                Date currentTime = Calendar.getInstance().getTime();

                                try {//currentTime.toString()
                                    File fileTemp = File.createTempFile(currentTime.toString(), ".jpg");
                                    item.getFile(fileTemp).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                                            Bitmap bitmapImage = BitmapFactory.decodeFile(fileTemp.getAbsolutePath());
                                            Drawable image = new BitmapDrawable(context.getResources(), bitmapImage);
                                            userImages.add(image);

                                            // Drawable image= fileTemp;


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.println(Log.ASSERT, "", "Process failed");
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                i++;

                                // All the items under listRef.
                            }
                            Log.println(Log.ASSERT, "", i + "ITEM FOUND");
                        } else {
                            Log.println(Log.ASSERT, "", "LIST NOT FOUND");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });


    }


    public void uploadImage(String imageName, Uri imageUri) {

        if (imageUri != null) {
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("fleets").child(imageName);

            ref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                }
            });

        }
    }



    public List<Fleet> getStaticFleets(){

        List<Fleet>fleets = new ArrayList<>();

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


}
