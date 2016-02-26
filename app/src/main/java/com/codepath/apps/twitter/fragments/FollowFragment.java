package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitter.adapters.UsersRecyclerViewAdapter;
import com.codepath.apps.twitter.interfaces.IUsersFragment;
import com.codepath.apps.twitter.models.gson.userlist.User;
import com.codepath.apps.twitter.utils.TwitterUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shyam Rokde on 2/26/16.
 */
public abstract class FollowFragment extends Fragment implements IUsersFragment{

  @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
  @Bind(R.id.rvUsers) RecyclerView rvUsers;

  protected UsersRecyclerViewAdapter recyclerViewAdapter;
  protected ArrayList<User> mUserList;
  protected String screenName;
  protected Long nextCursor;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_follow_users, container, false);
    ButterKnife.bind(this, view);

    // Swipe Refresh
    setupSwipeRefresh();
    // Setup RecyclerView
    setupRecyclerView();

    return view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mUserList = new ArrayList<>();
    recyclerViewAdapter = new UsersRecyclerViewAdapter(mUserList, getContext());

    screenName = getArguments().getString("screenName");
  }

  public void setupSwipeRefresh(){
    // Setup refresh listener which triggers new data loading
    swipeContainer.setOnRefreshListener(mRefreshListener);
    // Configure the refreshing colors
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_blue_dark,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light);
  }

  private void setupRecyclerView(){

    rvUsers.setAdapter(recyclerViewAdapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    rvUsers.setLayoutManager(layoutManager);

    rvUsers.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        if (TwitterUtil.isInternetAvailable()) {
          getUsers(screenName);
        } else {
          Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }


  private final SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
      if(TwitterUtil.isInternetAvailable()) {
        recyclerViewAdapter.clear(); // Clear Old Items
        nextCursor = null;
        getUsers(screenName); // Get New
      }else{
        swipeContainer.setRefreshing(false);
        Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
      }
    }
  };

}
