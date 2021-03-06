package com.codepath.apps.twitter.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shyam Rokde on 2/20/16.
 */
public class TimelineViewHolder extends RecyclerView.ViewHolder {
  @Bind(R.id.ivProfileImage) public ImageView ivProfileImage;
  @Bind(R.id.tvUserName) public TextView tvUserName;
  @Bind(R.id.tvBody) public TextView tvBody;
  @Bind(R.id.tvTime) public TextView tvTime;
  @Bind(R.id.tvScreenName) public TextView tvScreenName;
  @Bind(R.id.ibReply) public ImageButton ibReply;
  @Bind(R.id.ibRetweet) public ImageButton ibRetweet;
  @Bind(R.id.tvReTweetCount) public TextView tvReTweetCount;
  @Bind(R.id.ibLike) public ImageButton ibLike;
  @Bind(R.id.tvLikeCount) public TextView tvLikeCount;

  public TimelineViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
