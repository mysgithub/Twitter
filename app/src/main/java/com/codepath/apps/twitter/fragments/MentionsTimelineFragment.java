package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.network.TwitterClient;
import com.codepath.apps.twitter.utils.TwitterUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/23/16.
 */
public class MentionsTimelineFragment extends TweetsFragment{

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
      getTweets(0);
    }else {
      // No Internet - doosh...
      // TODO - getStoredTweets(0);
    }
  }

  @Override
  public void getTweets(long maxId) {
    client.getMentionsTimeline(mJsonHttpResponseHandler, maxId);
  }

  private final JsonHttpResponseHandler mJsonHttpResponseHandler = new JsonHttpResponseHandler() {
    @Override
    public void onStart() {
      Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onFinish() {
      super.onFinish();
      //Progress Bar
      // Swipe Refreshing
      swipeContainer.setRefreshing(false);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
      Log.d("DEBUG", "Resposne: " + jsonArray.toString());

      int curSize = tweetsRecyclerViewAdapter.getItemCount();
      ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray);
      tweets.addAll(arrayList);

      // TODO: Remove later
      Log.d("DEBUG", "curSize: " + curSize);
      Log.d("DEBUG", "tweets.size: " + tweets.size());
      Log.d("DEBUG", "arrayList.size: " + arrayList.size());

      tweetsRecyclerViewAdapter.notifyItemRangeInserted(curSize, arrayList.size());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      Log.d("Failed2: ", "" + statusCode);
      Log.d("Error : ", "" + throwable);
      Log.d("Exception:", errorResponse.toString());
    }
  };
}
