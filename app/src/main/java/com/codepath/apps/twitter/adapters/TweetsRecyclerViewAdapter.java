package com.codepath.apps.twitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.activities.ProfileActivity;
import com.codepath.apps.twitter.fragments.ComposeTweetDialog;
import com.codepath.apps.twitter.fragments.TweetsFragment;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.TwitterUtil;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/17/16.
 */
public class TweetsRecyclerViewAdapter extends RecyclerView.Adapter<TimelineViewHolder>{

  protected ArrayList<Tweet> mTweets;
  protected Context mContext;
  protected TweetsFragment mFragment;

  public TweetsRecyclerViewAdapter(ArrayList<Tweet> tweets, Context context, TweetsFragment fragment){
    mTweets = tweets;
    mContext = context;
    mFragment = fragment;
  }

  @Override
  public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate custom layout
    View tweetResultView = inflater.inflate(R.layout.item_tweet, parent, false);
    // Return new holder instance
    return new TimelineViewHolder(tweetResultView);
  }

  @Override
  public void onBindViewHolder(TimelineViewHolder viewHolder, int position) {
    // 1. Get Tweet
    Tweet tweet = mTweets.get(position);

    // 2. Populate
    String formattedScreenName = String.format("@%s", tweet.getUser().getScreenName());
    viewHolder.tvUserName.setText(tweet.getUser().getName());
    viewHolder.tvScreenName.setText(formattedScreenName);
    viewHolder.tvBody.setText(tweet.getBody());
    viewHolder.ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recycled view
    Glide.with(mContext).load(tweet.getUser().getProfileImageUrl()).fitCenter()
        .into(viewHolder.ivProfileImage);
    viewHolder.ivProfileImage.setTag(tweet.getUser().getScreenName());
    viewHolder.tvTime.setText(TwitterUtil.getFormattedRelativeTime(tweet.getCreatedAt()));
    viewHolder.tvReTweetCount.setText(tweet.getReTweetCount().toString());
    viewHolder.tvLikeCount.setText(tweet.getFavoriteCount().toString());

    // 3. Set Profile Image listener
    viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ProfileActivity.class);
        intent.putExtra("screen_name", v.getTag().toString());
        v.getContext().startActivity(intent);
      }
    });

    // 4. Set Reply Image listener
    viewHolder.ibReply.setTag(R.id.reply_tag_id, tweet);
    viewHolder.ibReply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Tweet t = (Tweet) v.getTag(R.id.reply_tag_id);
        ComposeTweetDialog dialog = ComposeTweetDialog.newInstance(t);
        dialog.show(mFragment.getFragmentManager(), "compose");
      }
    });
    // 5. Set Retweet handler
    viewHolder.ibRetweet.setTag(position);
    if(tweet.isReTweeted()){
      viewHolder.ibRetweet.setImageResource(R.drawable.ic_action_retweet_on);
    }else{
      viewHolder.ibRetweet.setImageResource(R.drawable.ic_action_retweet);
    }
    viewHolder.ibRetweet.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = (int) v.getTag();
        if (mFragment != null) {
          mFragment.onRetweetUnretweet(position);
        }
      }
    });

    // 6. Favorite handler
    viewHolder.ibLike.setTag(position);
    if(tweet.isFavorite()){
      viewHolder.ibLike.setImageResource(R.drawable.ic_action_like_on);
    }else{
      viewHolder.ibLike.setImageResource(R.drawable.ic_action_like);
    }
    viewHolder.ibLike.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = (int) v.getTag();
        if (mFragment != null) {
          mFragment.onLikeUnlike(position);
        }
      }
    });


  }



  @Override
  public int getItemCount() {
    return mTweets.size();
  }


  public void clear() {
    final int size = getItemCount();
    mTweets.clear();
    notifyItemRangeRemoved(0, size);
  }
}
