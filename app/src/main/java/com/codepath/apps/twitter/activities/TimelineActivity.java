package com.codepath.apps.twitter.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.adapters.TweetsPagerAdapter;
import com.codepath.apps.twitter.fragments.ComposeTweetDialog;
import com.codepath.apps.twitter.fragments.HomeTimelineFragment;
import com.codepath.apps.twitter.fragments.TweetsFragment;
import com.codepath.apps.twitter.interfaces.OnTweetPostListener;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.TwitterUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class TimelineActivity extends AppCompatActivity implements OnTweetPostListener, TweetsFragment.onProgressListener {

  @Bind(R.id.fab) FloatingActionButton fab;
  @Bind(R.id.viewpager) ViewPager vpPager;
  @Bind(R.id.tabs) PagerSlidingTabStrip tabStrip;
  @Bind(R.id.toolbar) Toolbar toolbar;

  MenuItem miActionProgressItem;
  String mQuery;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    ButterKnife.bind(this);

    // Toolbar
    setSupportActionBar(toolbar);
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

    // Handle Search
    // Get the SearchView and set the searchable configuration
    // Associate searchable configuration with the SearchView
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.miSearch).getActionView();
    ComponentName cn = new ComponentName(this, TimelineSearchActivity.class);
    searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));


    //MenuItem searchItem = menu.findItem(R.id.miSearch);
    //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

    /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        //showProgressBar();
        // Show Toast - if no connection
        if (!TwitterUtil.isInternetAvailable()) {
          Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
          return false;
        }
        mQuery = query;
        // TODO: Lets do this in Fragment
        Log.d("DEBUG", "I got the query - " +mQuery);

        searchView.clearFocus(); // clear focus to avoid keyboard enter twice
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });*/

    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    miActionProgressItem = menu.findItem(R.id.miActionProgress);
    //ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    switch (id){
      case R.id.miCompose:
          showComposeTweetDialog();
        return true;
      case R.id.miProfile:
        showProfile();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onTweetPost(Tweet tweet) {
    // Don't know if this is correct or not..
    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
      if(fragment instanceof HomeTimelineFragment){
        ((HomeTimelineFragment) fragment).onNewTweetPost(tweet);
      }
    }
  }

  public void showProgressBar() {
    // Show progress item
    if(miActionProgressItem != null) {
      miActionProgressItem.setVisible(true);
    }
  }

  public void hideProgressBar() {
    // Hide progress item
    if(miActionProgressItem != null) {
      miActionProgressItem.setVisible(false);
    }
  }


  private void showProfile() {
    Intent intent = new Intent(this, ProfileActivity.class);
    startActivity(intent);
  }

  private void showComposeTweetDialog(){
      if(TwitterUtil.isInternetAvailable()) {
          FragmentManager fragmentManager = getSupportFragmentManager();
          ComposeTweetDialog dialog = ComposeTweetDialog.newInstance(null);
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

  @Override
  public void onProgressStart() {
    showProgressBar();
  }

  @Override
  public void onProgressStop() {
    hideProgressBar();
  }
}

