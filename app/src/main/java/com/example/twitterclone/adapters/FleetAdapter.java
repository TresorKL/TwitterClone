package com.example.twitterclone.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.Fleet.Fleet;
import com.example.twitterclone.R;
import com.example.twitterclone.timer.MyCountDown;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class FleetAdapter extends RecyclerView.Adapter<FleetAdapter.ViewHolder> {


    List<Fleet> fleets;
    Context context;
    WindowManager windowManager;

    public FleetAdapter(List<Fleet> fleets, Context context, WindowManager windowManager) {
        this.fleets = fleets;
        this.context = context;
        this.windowManager = windowManager;
    }

    @NonNull
    @Override
    public FleetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fleet_row, null);
        return new FleetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FleetAdapter.ViewHolder holder, int position) {
        holder.userImage.setImageDrawable(fleets.get(position).userProfile);
        holder.userName.setText(fleets.get(position).getUserName());


        holder.fleetContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fleet_dialog);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.TOP);

                //   ProgressBar mProgressBar;

                // user name
                TextView currentUser = dialog.findViewById(R.id.fleetUserName);
                currentUser.setText(fleets.get(holder.getAdapterPosition()).getUserName());

                // set image prole
                CircleImageView profile = dialog.findViewById(R.id.fleetUserProfile);
                profile.setImageDrawable(fleets.get(holder.getAdapterPosition()).getUserProfile());


                //Drawable fleetPost = fleets.get(holder.getAdapterPosition()).getFleetImages().get(0);
                ImageView fleetImage = dialog.findViewById(R.id.post);
                //fleetImage.setImageDrawable(fleetPost);

                List<Drawable> fleetPosts = fleets.get(holder.getAdapterPosition()).getFleetImages();
                ProgressBar progressBar = dialog.findViewById(R.id.progress);


                MyCountDown countDown = new MyCountDown(5000, 1000, progressBar);
                if (!fleets.get(holder.getAdapterPosition()).getFleetImages().isEmpty()) {
                    fleetImage.setImageDrawable(fleets.get(holder.getAdapterPosition()).getFleetImages().get(0));
                    countDown.start();
                }else {
                    dialog.dismiss();
                }



                //dialog.dismiss();
                //int post=0;
                fleetImage.setOnClickListener(new View.OnClickListener() {
                    int post = 1;

                    @Override
                    public void onClick(View v) {

                        if (post != fleetPosts.size()) {
                            // countDown.cancel();
                            MyCountDown countDown = new MyCountDown(5000, 1000, progressBar);
                            fleetImage.setImageDrawable(fleets.get(holder.getAdapterPosition()).getFleetImages().get(post++));
                            countDown.cancel();
                            countDown.start();
                            // post++;
                        } else {
                            dialog.dismiss();
                        }

                    }
                });


                ImageButton dismiss = (ImageButton) dialog.findViewById(R.id.cancel);
                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return fleets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout fleetContent;
        CircleImageView userImage;
        TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fleetContent = itemView.findViewById(R.id.fleetBackground);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.fleetUserName);
        }
    }
}
