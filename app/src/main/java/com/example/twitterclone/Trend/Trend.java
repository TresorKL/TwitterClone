package com.example.twitterclone.Trend;

public class Trend {

    private String trendingField;
    private String trendName;
    private String tweets;

    public Trend(String trendingField, String trendName, String tweets) {
        this.trendingField = trendingField;
        this.trendName = trendName;
        this.tweets = tweets;
    }

    public Trend() {
    }


    public String getTrendingField() {
        return trendingField;
    }

    public void setTrendingField(String trendingField) {
        this.trendingField = trendingField;
    }

    public String getTrendName() {
        return trendName;
    }

    public void setTrendName(String trendName) {
        this.trendName = trendName;
    }

    public String getTweets() {
        return tweets;
    }

    public void setTweets(String tweets) {
        this.tweets = tweets;
    }
}
