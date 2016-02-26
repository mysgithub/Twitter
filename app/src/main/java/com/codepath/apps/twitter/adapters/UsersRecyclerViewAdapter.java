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
import com.codepath.apps.twitter.models.gson.userlist.User;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/26/16.
 */
public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

  public ArrayList<User> mUserList;
  Context mContext;

  public UsersRecyclerViewAdapter(ArrayList<User> users, Context context){
    mUserList = users;
    mContext = context;
  }

  @Override
  public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate custom layout
    View resultView = inflater.inflate(R.layout.item_follow_user, parent, false);

    return new UserViewHolder(resultView);
  }

  @Override
  public void onBindViewHolder(UserViewHolder viewHolder, int position) {
    // 1. Get User Object
    User user = mUserList.get(position);
    // 2. Populate View
    viewHolder.tvUserName.setText(user.getName());
    viewHolder.tvScreenName.setText(String.format("@%s", user.getScreenName()));
    viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
    Glide.with(mContext).load(user.getProfileImageUrl()).fitCenter().into(viewHolder.ivProfileImage);
    viewHolder.ivProfileImage.setTag(user.getScreenName());

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
    return mUserList.size();
  }

  public void clear() {
    final int size = getItemCount();
    mUserList.clear();
    notifyItemRangeRemoved(0, size);
  }
}
