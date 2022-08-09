package com.example.twitterclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.Fleet.Fleet;
import com.example.twitterclone.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FleetAdapter extends RecyclerView.Adapter<FleetAdapter.ViewHolder> {


    List<Fleet>fleets;

    public FleetAdapter(List<Fleet> fleets) {
        this.fleets = fleets;
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
    }

    @Override
    public int getItemCount() {
        return fleets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage=itemView.findViewById(R.id.userImage);
            userName=itemView.findViewById(R.id.fleetUserName);
        }
    }
}
