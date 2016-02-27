package com.codepath.apps.twitter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.activities.TweetDetailActivity;
import com.codepath.apps.twitter.adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitter.adapters.TweetsRecyclerViewAdapter;
import com.codepath.apps.twitter.interfaces.ITweetsFragment;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.gson.ReTweetResponse;
import com.codepath.apps.twitter.models.gson.favorites.FavoritesResponse;
import com.codepath.apps.twitter.network.TwitterClient;
import com.codepath.apps.twitter.utils.ItemClickSupport;
import com.codepath.apps.twitter.utils.TwitterUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shyam Rokde on 2/23/16.
 */
public abstract class TweetsFragment extends Fragment implements ITweetsFragment {

  @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
  @Bind(R.id.rvTweets) RecyclerView rvTweets;

  @Bind(R.id.miActionViewProgress) RelativeLayout miActionProgressItem;

  protected ArrayList<Tweet> tweets;
  protected TweetsRecyclerViewAdapter tweetsRecyclerViewAdapter;
  protected onProgressListener progressListener;
  protected TwitterClient client;


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

    // Async Client
    client = TwitterApplication.getRestClient();

    tweets = new ArrayList<>();
    tweetsRecyclerViewAdapter = new TweetsRecyclerViewAdapter(tweets, getContext(), this);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    progressListener = (onProgressListener) context;
  }

  private void setupItemClick() {
    // Item click Listener
    ItemClickSupport.addTo(rvTweets).setOnItemClickListener(mItemClickListener);
  }


  protected void setupSwipeRefresh(){
    // Setup refresh listener which triggers new data loading
    swipeContainer.setOnRefreshListener(mRefreshListener);
    // Configure the refreshing colors
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_blue_dark,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light);
  }

  private void setupRecyclerView(){

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
          if (getType() != null) {
            getStoredTweets(page);
          } else {
            Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
          }
        }
      }
    });
  }

  public void onLikeUnlike(final int position) {
    final Tweet tweet = tweets.get(position);
    client.postLikeUnLike(new JsonHttpResponseHandler() {
      @Override
      public void onStart() {
        Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d("DEBUG", "Resposne: " + response.toString());

        FavoritesResponse favoritesResponse = FavoritesResponse.parseJSON(response.toString());
        Tweet tweet = tweets.get(position);
        if (tweet.isFavorite()) {
          tweet.setIsFavorite(false);
        } else {
          tweet.setIsFavorite(true);
        }
        tweet.setFavoriteCount(favoritesResponse.getUser().getFavouritesCount());
        tweets.set(position, tweet);
        tweetsRecyclerViewAdapter.notifyItemChanged(position);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.d("ERROR", errorResponse.toString());
        Toast.makeText(getContext(), "Unable to complete your request!!!", Toast.LENGTH_SHORT).show();
      }
    }, tweet.getUid(), tweet.isFavorite());
  }

  public void onRetweetUnretweet(final int position) {
    final Tweet tweet = tweets.get(position);

    client.postRetweetUnretweet(new JsonHttpResponseHandler() {
      @Override
      public void onStart() {
        Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d("DEBUG", "Resposne: " + response.toString());

        ReTweetResponse reTweetResponse = ReTweetResponse.parseJSON(response.toString());
        Tweet tweet = tweets.get(position);
        if (tweet.isReTweeted()) {
          tweet.setIsReTweeted(false);
        }else{
          tweet.setIsReTweeted(true);
        }
        tweet.setReTweetCount(reTweetResponse.getRetweetCount());
        tweets.set(position, tweet);
        tweetsRecyclerViewAdapter.notifyItemChanged(position);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.d("ERROR", errorResponse.toString());
        Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
      }
    }, tweet.getUid(), tweet.isReTweeted());
  }

  /**
   * Get Stored Tweets
   * @param page
   */
  public void getStoredTweets(int page){
    int curSize = tweetsRecyclerViewAdapter.getItemCount();
    List<Tweet> tweetList = Tweet.getAll(page, getType());

    if(tweetList.size() > 0){
      tweets.addAll(tweetList);
      tweetsRecyclerViewAdapter.notifyItemRangeInserted(curSize, tweetList.size());
    }else if(!TwitterUtil.isInternetAvailable()){
      Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
  }



  public void onNewTweetPost(Tweet tweet) {
    // Add Tweet in the beginning of list
    tweets.add(0, tweet);
    tweetsRecyclerViewAdapter.notifyItemInserted(0);
  }

  // vars
  private final SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
      if(TwitterUtil.isInternetAvailable()) {
        tweetsRecyclerViewAdapter.clear(); // Clear Old Items
        getTweets(0); // Get New
      }else{
        swipeContainer.setRefreshing(false);
        Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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




  public interface onProgressListener {
    public void onProgressStart();
    public void onProgressStop();
  }


  public void showProgress(){
    miActionProgressItem.setVisibility(View.VISIBLE);
  }

  public void hideProgress(){
    miActionProgressItem.setVisibility(View.INVISIBLE);
  }
}
