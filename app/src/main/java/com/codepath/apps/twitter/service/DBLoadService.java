package com.codepath.apps.twitter.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/25/16.
 */
public class DBLoadService extends IntentService {




  public DBLoadService() {
    super("database-load-service");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    // Get Data
    Bundle bundle = intent.getBundleExtra("tweetsBundle");
    ArrayList<Tweet> tweetArrayList = bundle.getParcelableArrayList("tweetList");
    String type = intent.getStringExtra("type");
    // We should not keep too many things in DB - clean it as soon as possible


    // Start Processing
    if(tweetArrayList != null && tweetArrayList.size() > 0){
      for (Tweet tweet: tweetArrayList) {
        // Check if tweet is one of those two types then save else not
        if(tweet.getTweetType().equalsIgnoreCase(Tweet.HOME_TIMELINE) || tweet.getTweetType().equalsIgnoreCase(Tweet.MENTIONS_TIMELINE) ){
          User user = tweet.getUser();
          user.save();
          tweet.setUser(user);
          tweet.save();
        }

      }
    }

    Log.d("DEBUG", "DBLoadService Completed - " + type);
  }
}
