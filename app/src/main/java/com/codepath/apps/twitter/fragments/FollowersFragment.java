package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class FollowersFragment extends FollowFragment {

  private TwitterClient client;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Async Client
    client = TwitterApplication.getRestClient();

    // populate data
    populate();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Set ToolBar
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    if(actionBar != null){
      actionBar.setDisplayShowTitleEnabled(true);
      actionBar.setTitle(getString(R.string.title_followers));
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  public static FollowersFragment newInstance(String screenName){
    FollowersFragment fragment = new FollowersFragment();
    Bundle args = new Bundle();
    args.putString("screenName", screenName);
    fragment.setArguments(args);

    return fragment;
  }

  private void populate() {
    getUsers(screenName);
    Log.d("DEBUG", "We got it in following -" + screenName); // TODO: Remove this
  }

  @Override
  public void getUsers(String screenName) {
    client.getFollowersUserList(mJsonHttpResponseHandler, screenName, nextCursor);
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
