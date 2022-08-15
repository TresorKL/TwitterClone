package com.example.twitterclone.processor;

import android.os.AsyncTask;
import android.util.Log;

import com.example.twitterclone.tweet.Tweet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RetrieveTweetTask extends AsyncTask<String, String, String> {


    List<Tweet> tweetList;

    public RetrieveTweetTask(List<Tweet> tweetList) {
        this.tweetList = tweetList;
    }





    @Override
    protected String doInBackground(String... strings) {

        String userName;
        String tweetText;
        String userProfileUrl;
        String tweetImgUrl;
        int id;
        int parentId;


        try {
            Connection connection = getConnection();

            Statement stmnt = connection.createStatement();
            String sql = "select id, username,profile_url,image_url,parent_id,tweet_text,date_created from tweet_tbl";

            ResultSet rs = stmnt.executeQuery(sql);

            while (rs.next() != false) {

                id = rs.getInt("id");
                userName = rs.getString("username");
                userProfileUrl = rs.getString("profile_url");
                tweetImgUrl = rs.getString("image_url");
                parentId = rs.getInt("parent_id");
                tweetText = rs.getString("tweet_text");

                Tweet tweet = new Tweet();
                tweet.setId(id);
                tweet.setUserName(userName);
                tweet.setUserProfileUrl(userProfileUrl);
                tweet.setTweetParentId(parentId);
                tweet.setTweetImgUrl(tweetImgUrl);
                tweet.setTweetText(tweetText);


                tweetList.add(tweet);


            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {

        String s = "com.mysql.cj.jdbc.Driver";



        String USER = "root";
        String PASS_WORD = "12345678";
        String DB_URL = "jdbc:mysql://localhost:3306/TwitterClone";
//192.168.0.60

        Class.forName(s).getInterfaces();
        Connection connection = null;

        connection = DriverManager.getConnection(DB_URL, USER, PASS_WORD);


        return connection;
    }
}
//jdbc:mysql://127.0.0.1:3306/?user=root