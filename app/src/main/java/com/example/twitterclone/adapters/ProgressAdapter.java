package com.example.twitterclone.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;

import java.util.ArrayList;
import java.util.List;


public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {
    Context context;
    ContentView contentView;

    WindowManager windowManager;
    List<Drawable> fleetImages;

    public ProgressAdapter(List<Drawable> fleetImages, WindowManager windowManager, Context context) {
        this.fleetImages = fleetImages;
        this.windowManager = windowManager;
        this.context = context;
    }

    @NonNull
    @Override
    public ProgressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_row, null);
        return new ProgressAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull ProgressAdapter.ViewHolder holder, int position) {
        //holder.progressBar
        CountDownTimer mCountDownTimer;
        // int i = 0;

        List<ProgressBar> progressBars = new ArrayList<>();
        for (int i = 0; i < fleetImages.size(); i++) {
            progressBars.add(holder.progressBar);
        }


        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int progressWidth = width / fleetImages.size();
        holder.progressBar.setScaleX(progressWidth);
//        for (int i = 0; i < fleetImages.size(); i++) {
//            progressBars.get(i).setScaleX(progressWidth);
//        }

      //  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(progressWidth, 10);

        ProgressBar progressBar= holder.progressBar;

        mCountDownTimer = new CountDownTimer(5000, 1000) {

            int i = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                // for (int j= 0; j < fleetImages.size(); j++) {
                //progressBars.get(1).setProgress(i);
                //Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                progressBar.setProgress((int) i * 100 / (5000 / 1000));
              // }
            }

            @Override
            public void onFinish() {
                //Do what you want
              //  for (int j= 0; j < fleetImages.size(); j++) {
                    i++;
                   // progressBars.get(1).setProgress(100);
                    // dialog.dismiss();
               // }

            }
        };
        mCountDownTimer.start();


    }

    @Override
    public int getItemCount() {
        return fleetImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}
