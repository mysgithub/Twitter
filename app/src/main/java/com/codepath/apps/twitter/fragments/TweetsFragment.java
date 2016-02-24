package com.codepath.apps.twitter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.activities.TweetDetailActivity;
import com.codepath.apps.twitter.adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitter.adapters.TweetsRecyclerViewAdapter;
import com.codepath.apps.twitter.interfaces.OnTweetPostListener;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.ItemClickSupport;
import com.codepath.apps.twitter.utils.TwitterUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shyam Rokde on 2/23/16.
 */
public class TweetsFragment extends Fragment implements OnTweetPostListener {

  @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
  @Bind(R.id.rvTweets) RecyclerView rvTweets;

  protected ArrayList<Tweet> tweets;
  protected TweetsRecyclerViewAdapter tweetsRecyclerViewAdapter;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_tweets, container, false);
    ButterKnife.bind(this, view);

    // Swipe Refresh
    setupSwipeRefresh();

    // Setup RecyclerView
    setupRecyclerView();

    // Item click support
    setupItemClick();


    return view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }



  private void setupItemClick() {
    // Item click Listener
    ItemClickSupport.addTo(rvTweets).setOnItemClickListener(mItemClickListener);
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
    tweets = new ArrayList<>();
    tweetsRecyclerViewAdapter = new TweetsRecyclerViewAdapter(tweets, getContext());
    //lvTweets.setAdapter(tweetsArrayAdapter);
    rvTweets.setAdapter(tweetsRecyclerViewAdapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    rvTweets.setLayoutManager(layoutManager);

    rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        if (TwitterUtil.isInternetAvailable()) {
          long maxId = tweets.get(tweets.size() - 1).getUid() - 1; // -1 so that duplicate will not appear..
          getTweets(maxId);
        } else {
          getStoredTweets(page);
        }
      }
    });
  }

  protected void getTweets(long maxId){
    // Override this...
  }

  /**
   * Get Stored Tweets
   * @param page
   */
  public void getStoredTweets(int page){
    int curSize = tweetsRecyclerViewAdapter.getItemCount();
    List<Tweet> tweetList = Tweet.getAll(page);
    if(tweetList.size() > 0){
      tweets.addAll(tweetList);
      tweetsRecyclerViewAdapter.notifyItemRangeInserted(curSize, tweetList.size());
    }
  }

  @Override
  public void onTweetPost(Tweet tweet) {
    // Add Tweet in the beginning of list
    tweets.add(0, tweet);
    tweetsRecyclerViewAdapter.notifyItemInserted(0);
  }

  // vars
  private final SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
      if(TwitterUtil.isInternetAvailable()) {
        // Clear Old Items
        tweetsRecyclerViewAdapter.clear();
        // Get New
        getTweets(0);
      }else{
        //Toast.makeText(TimelineActivity.this, "Unable to connect to twitter.com", Toast.LENGTH_LONG).show();
        swipeContainer.setRefreshing(false);
      }
    }
  };

  private final ItemClickSupport.OnItemClickListener mItemClickListener = new ItemClickSupport.OnItemClickListener() {
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

      Tweet tweet = tweets.get(position);

      Intent i = new Intent(getContext(), TweetDetailActivity.class);
      Bundle bundle = new Bundle();
      bundle.putParcelable("tweet", tweet);
      i.putExtras(bundle);
      startActivity(i);
    }
  };




}
