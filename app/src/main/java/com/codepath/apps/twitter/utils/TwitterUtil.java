package com.codepath.apps.twitter.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;

import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.service.DBLoadService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shyam Rokde on 2/20/16.
 */
public class TwitterUtil {

  public final static int TWEET_MAX_ALLOWED_CHAR = 140;

  public static String getFormattedRelativeTime(Date timestamp){
    String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
    sf.setLenient(true);

    String relativeDate = "";

    try {
      //long dateMillis = sf.parse(createdAt).getTime();
      String relativeTime = DateUtils.getRelativeTimeSpanString(timestamp.getTime()).toString();
      String[] words = relativeTime.split("\\s+");
      if(relativeTime.startsWith("in")){
        relativeDate = "new";
      }else{
        relativeDate = String.format("%s%s", words[0], words[1].charAt(0));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return relativeDate;
  }

  /**
   * Gate Date From Twitter String Format
   * @param date
   * @return
   */
  public static Date getDateFromString(String date) {
    SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
    sf.setLenient(true);

    try {
      return sf.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean isInternetAvailable() {
    Runtime runtime = Runtime.getRuntime();
    try {
      Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
      int     exitValue = ipProcess.waitFor();
      return (exitValue == 0);
    } catch (IOException e)          { e.printStackTrace(); }
    catch (InterruptedException e) { e.printStackTrace(); }
    return false;
  }

  public static void storeTweets(Context context, ArrayList<Tweet> arrayList, String type){
    Intent i = new Intent(context, DBLoadService.class);
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList("tweetList", arrayList);
    i.putExtra("tweetsBundle", bundle);
    i.putExtra("type", type);
    context.startService(i);
  }
}
