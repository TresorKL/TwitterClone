package com.example.twitterclone.tweet;

import java.sql.Date;

public class Tweet {

    private String userName;
    private String tweetText;
    private String userProfileUrl;
    private String tweetImgUrl;
    private int Id;
    private String tweetParentId;
    private Date dateOfCreation;

    public Tweet() {
    }

    public Tweet(String userName, String tweetText, String userProfileUrl, String tweetImgUrl, int id, String tweetParentId, Date dateOfCreation) {
        this.userName = userName;
        this.tweetText = tweetText;
        this.userProfileUrl = userProfileUrl;
        this.tweetImgUrl = tweetImgUrl;
        Id = id;
        this.tweetParentId = tweetParentId;
        this.dateOfCreation = dateOfCreation;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getTweetImgUrl() {
        return tweetImgUrl;
    }

    public void setTweetImgUrl(String tweetImgUrl) {
        this.tweetImgUrl = tweetImgUrl;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTweetParentId() {
        return tweetParentId;
    }

    public void setTweetParentId(String tweetParentId) {
        this.tweetParentId = tweetParentId;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
