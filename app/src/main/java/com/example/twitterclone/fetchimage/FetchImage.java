package com.example.twitterclone.fetchimage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class FetchImage extends Thread {

    String url;
    ImageView imageView;
    ProgressDialog progressDialog;
    Handler handler= new Handler();
    Context context;


    public FetchImage(String url, ImageView imageView,Context context) {
        this.url = url;
        this.imageView = imageView;
        this.context=context;

    }

    @Override
    public void run() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog=new ProgressDialog(context);
                progressDialog.setMessage("Message loading");
                progressDialog.setCancelable(false);
                progressDialog.show();
                // load image from url
                Picasso.with(context).load(url).into(imageView);
            }
        });


        handler.post(new Runnable() {
            @Override
            public void run() {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });



    }
}
