package com.codepath.apps.twitter.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragments.SearchTwitterFragment;
import com.codepath.apps.twitter.fragments.TweetsFragment;
import com.codepath.apps.twitter.interfaces.OnTweetPostListener;
import com.codepath.apps.twitter.models.Tweet;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimelineSearchActivity extends AppCompatActivity implements OnTweetPostListener, TweetsFragment.onProgressListener{

  @Bind(R.id.toolbar) Toolbar toolbar;

  String searchQuery;
  SearchView searchView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline_search);

    ButterKnife.bind(this);
    // Toolbar
    setSupportActionBar(toolbar);
    showActionBar();


    // Get the intent, verify the action and get the query
    Intent intent = getIntent();
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      searchQuery = intent.getStringExtra(SearchManager.QUERY);

      // Search Time line
      if(savedInstanceState == null){
        SearchTwitterFragment searchTwitterFragment = SearchTwitterFragment.newInstance(searchQuery);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, searchTwitterFragment);
        ft.commit();
      }else{
        // Get Fragment
      }


    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_search, menu);

    MenuItem menuItem = menu.findItem(R.id.miSearch);

    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    searchView = (SearchView) menu.findItem(R.id.miSearch).getActionView();
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setIconifiedByDefault(false); // Expand
    searchView.setMaxWidth(Integer.MAX_VALUE);
    searchView.setQuery(searchQuery, false);

    return super.onCreateOptionsMenu(menu);
  }

  private void showActionBar(){
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null){
      actionBar.setDisplayShowHomeEnabled(false);
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayUseLogoEnabled(false);
    }
  }

  @Override
  public void onTweetPost(Tweet tweet) {
    // Do Nothing
  }

  @Override
  public void onProgressStart() {

  }

  @Override
  public void onProgressStop() {

  }
}
