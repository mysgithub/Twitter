package com.codepath.apps.twitter.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragments.FollowersFragment;
import com.codepath.apps.twitter.fragments.FollowingFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FollowUserActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_follow_user);

    // Get Type
    String type = getIntent().getStringExtra("type");
    String screenName = getIntent().getStringExtra("screen_name");

    if(savedInstanceState == null){
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

      if(type != null && type.equalsIgnoreCase("following")){
        FollowingFragment followingFragment = FollowingFragment.newInstance(screenName);
        ft.replace(R.id.flContainer, followingFragment);
      }else{
        FollowersFragment followersFragment = FollowersFragment.newInstance(screenName);
        ft.replace(R.id.flContainer, followersFragment);
      }

      ft.commit();
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
