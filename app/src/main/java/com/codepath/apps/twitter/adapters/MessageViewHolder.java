package com.codepath.apps.twitter.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shyam Rokde on 2/27/16.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder{
  @Bind(R.id.ivProfileImage) public ImageView ivProfileImage;
  @Bind(R.id.tvUserName) public TextView tvUserName;
  @Bind(R.id.tvScreenName) public TextView tvScreenName;
  @Bind(R.id.tvTime) public TextView tvTime;
  @Bind(R.id.tvMessage) public TextView tvMessage;


  public MessageViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
