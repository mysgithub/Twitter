package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.fragments.UserTimelineFragment;
import com.codepath.apps.twitter.models.gson.TwitterProfileResponse;
import com.codepath.apps.twitter.network.TwitterClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

  TwitterClient client;
  TwitterProfileResponse twitterProfileResponse;

  @Bind(R.id.ivProfileImage) ImageView ivProfileImage;
  @Bind(R.id.tvName) TextView tvName;
  @Bind(R.id.tvTagline) TextView tvTagline;
  @Bind(R.id.tvFollowers) TextView tvFollowers;
  @Bind(R.id.tvFollowing) TextView tvFollowing;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    ButterKnife.bind(this);

    // Async Client
    client = TwitterApplication.getRestClient();
    client.getProfileInfo(mProfileInfoResponseHandler);

    // User Time line
    String screenName = getIntent().getStringExtra("screen_name");
    if(savedInstanceState == null){

      UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.flContainer, fragmentUserTimeline);
      ft.commit();
    }
  }

  private final TextHttpResponseHandler mProfileInfoResponseHandler = new TextHttpResponseHandler(){
    @Override
    public void onStart() {
      Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
      Log.d("DEBUG", "Resposne: " + responseString);
      // Using Gson
      twitterProfileResponse = TwitterProfileResponse.parseJSON(responseString);
      String formattedScreenName = String.format("@%s", twitterProfileResponse.getScreenName());

      getSupportActionBar().setTitle(formattedScreenName);

      // Populate
      populateProfileHeader(twitterProfileResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
      Toast.makeText(getApplicationContext(), "Sorry!! Unable to connect to twitter.com", Toast.LENGTH_SHORT).show();
    }
  };

  private void populateProfileHeader(TwitterProfileResponse twitterProfileResponse) {

    tvName.setText(twitterProfileResponse.getName());
    tvTagline.setText(twitterProfileResponse.getDescription());
    tvFollowers.setText(twitterProfileResponse.getFollowersCount() + " Followers");
    tvFollowing.setText(twitterProfileResponse.getFriendsCount() + " Followings");

    Glide.with(getApplicationContext()).load(twitterProfileResponse.getProfileImageUrl()).fitCenter().into(ivProfileImage);
  }


}
