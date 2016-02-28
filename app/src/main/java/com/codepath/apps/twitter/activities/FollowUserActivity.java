package com.codepath.apps.twitter.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragments.FollowersFragment;
import com.codepath.apps.twitter.fragments.FollowingFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FollowUserActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_follow_user);

    ButterKnife.bind(this);
    // Toolbar
    setSupportActionBar(toolbar);
    setupToolbar();

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

  public void setupToolbar(){
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null){
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayUseLogoEnabled(true);
    }
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NavUtils.navigateUpTo(this, intent);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
