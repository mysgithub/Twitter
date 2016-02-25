package com.codepath.apps.twitter.interfaces;

/**
 * Created by Shyam Rokde on 2/23/16.
 */
public interface ITweetsFragment {
  void getTweets(long maxId);
  String getType();
}
