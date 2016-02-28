package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.gson.search.SearchResponse;
import com.codepath.apps.twitter.utils.TwitterUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/27/16.
 */
public class SearchTwitterFragment extends TweetsFragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    populateTimeline();
  }

  private void populateTimeline() {
    if(TwitterUtil.isInternetAvailable()){
      getTweets(0);
    }
  }

  @Override
  public void getTweets(long maxId) {
    String query = getArguments().getString("query");
    if(TwitterUtil.isInternetAvailable()){
      client.getTweetsOnSearch(mJsonHttpResponseHandler, maxId, query);
    }else {
      Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public String getType() {
    return null;
  }

  public static SearchTwitterFragment newInstance(String query) {
    SearchTwitterFragment searchTwitterFragment = new SearchTwitterFragment();
    Bundle args = new Bundle();
    args.putString("query", query);
    searchTwitterFragment.setArguments(args);
    return searchTwitterFragment;
  }

  @Override
  protected void setupSwipeRefresh() {
    //No swipe refresh
    //super.setupSwipeRefresh();
    swipeContainer.setEnabled(false);
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

      // Search Response

      int curSize = tweetsRecyclerViewAdapter.getItemCount();
      ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray, null);
      tweets.addAll(arrayList);

      tweetsRecyclerViewAdapter.notifyItemRangeInserted(curSize, arrayList.size());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
      Log.d("DEBUG", "Response: " + response.toString());

      // Search Response
      SearchResponse searchResponse = SearchResponse.parseJSON(response.toString());

      int curSize = tweetsRecyclerViewAdapter.getItemCount();
      ArrayList<Tweet> arrayList = searchResponse.getTweetList();
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
