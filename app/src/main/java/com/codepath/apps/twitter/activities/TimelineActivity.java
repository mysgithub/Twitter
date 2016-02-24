package com.codepath.apps.twitter.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.adapters.TweetsPagerAdapter;
import com.codepath.apps.twitter.fragments.ComposeTweetDialog;
import com.codepath.apps.twitter.utils.TwitterUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class TimelineActivity extends AppCompatActivity {

  @Bind(R.id.fab) FloatingActionButton fab;
  @Bind(R.id.viewpager) ViewPager vpPager;
  @Bind(R.id.tabs) PagerSlidingTabStrip tabStrip;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    ButterKnife.bind(this);

    // Change ActionBar Icon
    showTwitterIcon();
    // Floating Action Button
    setupFloatingActionButton();

    // Set viewpager & Tab Strip
    vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
    tabStrip.setViewPager(vpPager);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
      super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_timeline, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    switch (id){
      case R.id.itemCompose:
          showComposeTweetDialog();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }


  private void showComposeTweetDialog(){
      if(TwitterUtil.isInternetAvailable()) {
          FragmentManager fragmentManager = getSupportFragmentManager();
          ComposeTweetDialog dialog = ComposeTweetDialog.newInstance();

          dialog.show(fragmentManager, "compose");
      }else{
          Toast.makeText(TimelineActivity.this, "Unable to connect to twitter.com", Toast.LENGTH_LONG).show();
      }
  }

  private void showTwitterIcon(){
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null){
      actionBar.setDisplayShowHomeEnabled(true);
      actionBar.setLogo(R.drawable.ic_twitter);
      actionBar.setDisplayUseLogoEnabled(true);
    }
  }

  private void setupFloatingActionButton() {
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
        showComposeTweetDialog();
      }
    });
  }

}

