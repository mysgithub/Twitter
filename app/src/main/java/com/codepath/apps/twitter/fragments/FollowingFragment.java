package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.models.gson.userlist.User;
import com.codepath.apps.twitter.models.gson.userlist.UserListResponse;
import com.codepath.apps.twitter.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/26/16.
 */
public class FollowingFragment extends FollowFragment {
  private TwitterClient client;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Async Client
    client = TwitterApplication.getRestClient();

    // populate data
    populate();
  }

  private void populate() {
    getUsers(screenName);
    Log.d("DEBUG", "We got it in following -" + screenName); // TODO: Remove this
  }

  public static FollowingFragment newInstance(String screenName){
    FollowingFragment fragment = new FollowingFragment();
    Bundle args = new Bundle();
    args.putString("screenName", screenName);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void getUsers(String screenName) {
    client.getFollowingUserList(mJsonHttpResponseHandler, screenName, nextCursor);
  }

  private final JsonHttpResponseHandler mJsonHttpResponseHandler = new JsonHttpResponseHandler() {
    @Override
    public void onStart() {
      Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onFinish() {
      super.onFinish();
      // TODO: Progress Bar
      // Swipe Refreshing
      swipeContainer.setRefreshing(false);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
      Log.d("DEBUG", "Response: " + response);

      UserListResponse userListResponse = UserListResponse.parseJSON(response.toString());

      nextCursor = userListResponse.getNextCursor();
      Log.d("DEBUG", "Next Cursor-" + userListResponse.getNextCursor() + " Prev Cursor-" + userListResponse.getPreviousCursor());

      int curSize = recyclerViewAdapter.getItemCount();
      ArrayList<User> arrayList = userListResponse.getUsers();
      mUserList.addAll(arrayList);
      recyclerViewAdapter.notifyItemRangeInserted(curSize, arrayList.size());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      Log.d("ERROR", getString(R.string.no_internet));
      Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
  };


}
