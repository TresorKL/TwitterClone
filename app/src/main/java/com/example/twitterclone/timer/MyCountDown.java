package com.example.twitterclone.timer;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

public class MyCountDown extends CountDownTimer {

    public ProgressBar progressBar;
    public boolean isRunning;
    ImageView fleetImage;
    int counter;


    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public MyCountDown(long millisInFuture, long countDownInterval, ProgressBar progressBar) {
        super(millisInFuture, countDownInterval);
        this.progressBar = progressBar;

    }

    int i = 0;

    @Override
    public void onTick(long millisUntilFinished) {

        i++;
        progressBar.setProgress((int) i * 100 / (5000 / 1000));

        setRunning(true);

    }

    @Override
    public void onFinish() {
        // i++;


        // onFinish();
        setCounter(getCounter() + 1);
        progressBar.setProgress(100);
        setRunning(false);
        //  cancel();
    }


    public int countPost() {
        return getCounter();
    }
}
