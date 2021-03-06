package com.codepath.apps.twitter;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.bumptech.glide.request.target.ViewTarget;
import com.codepath.apps.twitter.network.TwitterClient;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = TwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TwitterApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		TwitterApplication.context = this;
		// ActiveAndroid
		ActiveAndroid.initialize(this);

		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("fonts/Roboto-Regular.ttf")
				.setFontAttrId(R.attr.fontPath)
				.build());

		ViewTarget.setTagId(R.id.glide_tag_id);
	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
	}
}