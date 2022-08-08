package com.example.twitterclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterclone.R;
import com.example.twitterclone.Trend.Trend;

public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.ViewHolder> {

    Trend[] trends;

    public TrendAdapter(Trend[] trends) {
        this.trends = trends;
    }

    @NonNull
    @Override
    public TrendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_row, null);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TrendAdapter.ViewHolder holder, int position) {

        holder.trendName.setText(trends[position].getTrendName());
        holder.field.setText(trends[position].getTrendingField());
        holder.tweets.setText(trends[position].getTweets());
    }

    @Override
    public int getItemCount() {
        return trends.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout row;
        TextView field,trendName, tweets;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          field= itemView.findViewById(R.id.field);
          trendName=itemView.findViewById(R.id.trendName);
          tweets=itemView.findViewById(R.id.tweets);
        }
    }
}
