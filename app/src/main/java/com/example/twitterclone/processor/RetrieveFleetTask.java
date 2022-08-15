package com.example.twitterclone.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.twitterclone.tweet.Tweet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Result;

public class RetrieveFleetTask extends AsyncTask<String,String,String> {

    Context context;
    List<Drawable> userImages;

    RetrieveFleetTask(Context context,List<Drawable> userImages){
        this.context=context;
        this.userImages=userImages;
    }

    @Override
    protected void onPostExecute(String s) {
       // super.onPostExecute(s);
        Toast.makeText(context,"Fleets uploaded",Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... strings) {

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


        return null;
    }



}
