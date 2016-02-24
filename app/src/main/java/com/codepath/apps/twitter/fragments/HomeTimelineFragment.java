package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.network.TwitterClient;
import com.codepath.apps.twitter.utils.TwitterUtil;

/**
 * Created by Shyam Rokde on 2/23/16.
 */
public class HomeTimelineFragment extends TweetsFragment {

  private TwitterClient client;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Async Client
    client = TwitterApplication.getRestClient();

    populateTimeline();
  }

  private void populateTimeline(){
    if(TwitterUtil.isInternetAvailable()){
      client.getHomeTimeline(mJsonHttpResponseHandler, 0);
    }else {
      // No Internet - doosh...
      // TODO - getStoredTweets(0);
    }
  }
}
