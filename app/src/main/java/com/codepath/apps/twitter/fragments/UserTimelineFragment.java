package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
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
 * Created by Shyam Rokde on 2/24/16.
 */
public class UserTimelineFragment extends TweetsFragment {

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
      Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void getTweets(long maxId) {
    String screenName = getArguments().getString("screenName");
    client.getUserTimeline(mJsonHttpResponseHandler, maxId, screenName);
  }

  public static UserTimelineFragment newInstance(String screenName){
    UserTimelineFragment userFragment = new UserTimelineFragment();
    Bundle args = new Bundle();
    args.putString("screenName", screenName);
    userFragment.setArguments(args);

    return userFragment;
  }

  @Override
  public String getType() {
    return null;
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
      Log.d("DEBUG", "Response: " + jsonArray.toString());

      int curSize = tweetsRecyclerViewAdapter.getItemCount();
      ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray, null);
      tweets.addAll(arrayList);

      tweetsRecyclerViewAdapter.notifyItemRangeInserted(curSize, arrayList.size());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      Log.d("ERROR", getString(R.string.no_internet));
      Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
  };
}
