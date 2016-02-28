package com.codepath.apps.twitter.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.fragments.TweetsFragment;
import com.codepath.apps.twitter.fragments.UserTimelineFragment;
import com.codepath.apps.twitter.interfaces.OnTweetPostListener;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.gson.TwitterProfileResponse;
import com.codepath.apps.twitter.network.TwitterClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity implements OnTweetPostListener, TweetsFragment.onProgressListener {

  TwitterClient client;
  TwitterProfileResponse twitterProfileResponse;

  @Bind(R.id.ivProfileImage) ImageView ivProfileImage;
  @Bind(R.id.tvName) TextView tvName;
  @Bind(R.id.tvTagline) TextView tvTagline;
  @Bind(R.id.tvFollowers) TextView tvFollowers;
  @Bind(R.id.tvFollowing) TextView tvFollowing;
  @Bind(R.id.ivProfileBackground) ImageView ivProfileBackground;
  @Bind(R.id.tvScreenName) TextView tvScreenName;

  private String screenName;
  MenuItem miActionProgressItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    ButterKnife.bind(this);

    // Get screenName
    String screenName = getIntent().getStringExtra("screen_name");

    // Async Client
    client = TwitterApplication.getRestClient();
    client.getProfileInfo(screenName, mProfileInfoResponseHandler);

    // User Time line
    if(savedInstanceState == null){
      UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.flContainer, fragmentUserTimeline);
      ft.commit();
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    miActionProgressItem = menu.findItem(R.id.miActionProgress);
    return super.onPrepareOptionsMenu(menu);
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
      //String formattedScreenName = String.format("@%s", twitterProfileResponse.getScreenName());

      //getSupportActionBar().setTitle(formattedScreenName);

      // Populate
      populateProfileHeader(twitterProfileResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
      Toast.makeText(getApplicationContext(), "Sorry!! Unable to connect to twitter.com", Toast.LENGTH_SHORT).show();
    }
  };

  private void populateProfileHeader(TwitterProfileResponse twitterProfileResponse) {

    //int bgColor = Integer.parseInt(twitterProfileResponse.getProfileBackgroundColor());


    //ivProfileBackground.setBackgroundColor();
    if(twitterProfileResponse.getProfileUseBackgroundImage()){
      // glide
      Glide.with(getApplicationContext()).load(twitterProfileResponse.getProfileBackgroundImageUrl()).centerCrop().into(ivProfileBackground);
    }else{
      ivProfileBackground.setBackgroundColor(Color.parseColor("#" + twitterProfileResponse.getProfileBackgroundColor()));
    }

    tvName.setText(twitterProfileResponse.getName());
    tvScreenName.setText(String.format("@%s", twitterProfileResponse.getScreenName()));
    tvTagline.setText(twitterProfileResponse.getDescription());


    tvFollowers.setText(Html.fromHtml(String.format("<b>%,d</b> %s", twitterProfileResponse.getFollowersCount(), getString(R.string.followers))));
    tvFollowing.setText(Html.fromHtml(String.format("<b>%,d</b> %s", twitterProfileResponse.getFriendsCount(), getString(R.string.following))));

    Glide.with(getApplicationContext()).load(twitterProfileResponse.getProfileImageUrl()).fitCenter().into(ivProfileImage);

    screenName = twitterProfileResponse.getScreenName();

    tvFollowers.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("DEBUG", "tvFollowers clicked");
        Intent i = new Intent(getApplicationContext(), FollowUserActivity.class);
        i.putExtra("screen_name", screenName);
        startActivity(i);
      }
    });

    tvFollowing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("DEBUG", "tvFollowing clicked");
        Intent i = new Intent(getApplicationContext(), FollowUserActivity.class);
        i.putExtra("screen_name", screenName);
        i.putExtra("type", "following");
        startActivity(i);
      }
    });

  }


  @Override
  public void onProgressStart() {
    // Show progress item
    if(miActionProgressItem != null) {
      miActionProgressItem.setVisible(true);
    }
  }

  @Override
  public void onProgressStop() {
    // Hide progress item
    if(miActionProgressItem != null) {
      miActionProgressItem.setVisible(false);
    }
  }

  @Override
  public void onTweetPost(Tweet tweet) {
    // TODO: Do Nothing
  }
}
