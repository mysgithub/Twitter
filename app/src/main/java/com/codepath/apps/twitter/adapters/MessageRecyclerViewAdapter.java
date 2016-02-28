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
import com.codepath.apps.twitter.models.Message;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/27/16.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageViewHolder> {

  public ArrayList<Message> mMessages;
  Context mContext;

  public MessageRecyclerViewAdapter(ArrayList<Message> messages, Context context){
    mMessages = messages;
    mContext = context;
  }

  @Override
  public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate custom layout
    View resultView = inflater.inflate(R.layout.item_message, parent, false);

    return new MessageViewHolder(resultView);
  }

  @Override
  public void onBindViewHolder(MessageViewHolder viewHolder, int position) {
    // 1. Get User Object
    Message message = mMessages.get(position);
    // 2. Populate View
    viewHolder.tvUserName.setText(message.getUser().getName());
    viewHolder.tvScreenName.setText(String.format("@%s", message.getUser().getScreenName()));
    viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
    Glide.with(mContext).load(message.getUser().getProfileImageUrl()).fitCenter().into(viewHolder.ivProfileImage);
    viewHolder.ivProfileImage.setTag(message.getUser().getScreenName());
    viewHolder.tvMessage.setText(message.getMessage());

    // 3. Set Profile Image listner
    viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ProfileActivity.class);
        intent.putExtra("screen_name", v.getTag().toString());
        v.getContext().startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mMessages.size();
  }

  public void clear() {
    final int size = getItemCount();
    mMessages.clear();
    notifyItemRangeRemoved(0, size);
  }
}
