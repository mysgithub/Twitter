package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitter.adapters.MessageRecyclerViewAdapter;
import com.codepath.apps.twitter.fragments.ComposeMessageDialog;
import com.codepath.apps.twitter.models.Message;
import com.codepath.apps.twitter.models.gson.message.DirectMessageResponse;
import com.codepath.apps.twitter.network.TwitterClient;
import com.codepath.apps.twitter.utils.TwitterUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.rvMessages) RecyclerView rvMessages;

  ArrayList<Message> messageArrayList;
  MessageRecyclerViewAdapter adapter;
  private TwitterClient client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_message);

    ButterKnife.bind(this);
    // Toolbar
    setSupportActionBar(toolbar);
    setupToolbar();

    // Async Client
    client = TwitterApplication.getRestClient();

    // Setup adapter
    messageArrayList = new ArrayList<>();
    adapter = new MessageRecyclerViewAdapter(messageArrayList, getApplicationContext());

    // Setup RecyclerView
    setupRecyclerView();

    //populate messages
    getDirectMessages();

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id){
      case R.id.miPostMessage:
        showComposeMessageDialog();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void showComposeMessageDialog(){
    if(TwitterUtil.isInternetAvailable()) {
      FragmentManager fragmentManager = getSupportFragmentManager();
      ComposeMessageDialog dialog = ComposeMessageDialog.newInstance();
      dialog.show(fragmentManager, "compose");
    }else{
      Toast.makeText(getApplicationContext(), "Unable to connect to twitter.com", Toast.LENGTH_LONG).show();
    }
  }

  public void setupToolbar(){
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null){
      // UP
      //actionBar.setDisplayShowHomeEnabled(true); // is it selected
      actionBar.setDisplayHomeAsUpEnabled(true);
      // Title
      actionBar.setDisplayShowTitleEnabled(true);
      actionBar.setTitle(R.string.message);
      // Logo
      actionBar.setDisplayUseLogoEnabled(true);
      actionBar.setLogo(R.drawable.ic_twitter);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_message, menu);

    return true;
  }

  private void setupRecyclerView(){

    rvMessages.setAdapter(adapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    rvMessages.setLayoutManager(layoutManager);

    rvMessages.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        if (TwitterUtil.isInternetAvailable()) {
          //getDirectMessages();
        } else {
          Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void getDirectMessages() {
    client.getMessages(mJsonHttpResponseHandler);
  }

  private final JsonHttpResponseHandler mJsonHttpResponseHandler = new JsonHttpResponseHandler() {
    @Override
    public void onStart() {
      Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onFinish() {
      super.onFinish();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
      Log.d("DEBUG", "Response: " + jsonArray.toString());

      DirectMessageResponse directMessageResponse = DirectMessageResponse.parseJSON(jsonArray.toString());

      int curSize = adapter.getItemCount();
      ArrayList<Message> arrayList = directMessageResponse.getMessageList();
      messageArrayList.addAll(arrayList);
      adapter.notifyItemRangeInserted(curSize, arrayList.size());

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      Log.d("ERROR", getString(R.string.no_internet));
      Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
  };
}
