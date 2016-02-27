package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.TwitterUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/23/16.
 */
public class HomeTimelineFragment extends TweetsFragment {


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    populateTimeline();
  }

  private void populateTimeline(){

    if(TwitterUtil.isInternetAvailable()){
      getTweets(0);
    }else {
      // No Internet - doosh...
      getStoredTweets(0);
    }
  }

  @Override
  public void getTweets(long maxId) {
    progressListener.onProgressStart();
    client.getHomeTimeline(mJsonHttpResponseHandler, maxId);
  }

  @Override
  public String getType() {
    return Tweet.HOME_TIMELINE;
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
      progressListener.onProgressStop();
      // Swipe Refreshing
      swipeContainer.setRefreshing(false);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
      Log.d("DEBUG", "Resposne: " + jsonArray.toString());

      int curSize = tweetsRecyclerViewAdapter.getItemCount();
      ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray, Tweet.HOME_TIMELINE);
      tweets.addAll(arrayList);
      // Store in DB
      TwitterUtil.storeTweets(getContext(), arrayList, Tweet.HOME_TIMELINE);
      tweetsRecyclerViewAdapter.notifyItemRangeInserted(curSize, arrayList.size());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      Log.d("ERROR", errorResponse.toString());
      Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
  };


}
