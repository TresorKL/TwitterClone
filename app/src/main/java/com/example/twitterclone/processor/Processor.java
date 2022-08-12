package com.example.twitterclone.processor;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Processor {

    Context context;
    public Processor(Context context) {
        this.context=context;
    }


    public void uploadImage(String imageName, Uri imageUri) {

        if(imageUri!=null){
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("fleets").child(imageName);

            ref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(context, "Image successfully uploaded", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
