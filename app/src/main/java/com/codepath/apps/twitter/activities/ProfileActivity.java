package com.codepath.apps.twitter.activities;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    String screenName = getIntent().getStringExtra("screen_name");
    if(savedInstanceState == null){

      UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.flContainer, fragmentUserTimeline);
      ft.commit();
    }
  }
}
