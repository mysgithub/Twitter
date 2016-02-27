
package com.codepath.apps.twitter.models.gson.search;

import com.codepath.apps.twitter.models.*;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.utils.TwitterUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class SearchResponse {

    public static SearchResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        SearchResponse searchResponse = gson.fromJson(response, SearchResponse.class);
        return searchResponse;
    }

    public ArrayList<Tweet> getTweetList(){
        ArrayList<Tweet> arrayList = new ArrayList<>();
        for(Status status: this.statuses){
            Tweet tweet = new Tweet();
            tweet.setBody(status.getText());
            tweet.setUid(status.getId());
            tweet.setCreatedAt(TwitterUtil.getDateFromString(status.getCreatedAt()));
            tweet.setIsFavorite(status.getFavorited());
            tweet.setReTweetCount(status.getRetweetCount());
            tweet.setIsReTweeted(status.getRetweeted());
            tweet.setFavoriteCount(status.getUser().getFavouritesCount());
            com.codepath.apps.twitter.models.User u = new User();
            u.setUid(status.getUser().getId());
            u.setName(status.getUser().getName());
            u.setScreenName(status.getUser().getScreenName());
            u.setProfileImageUrl(status.getUser().getProfileImageUrl());
            tweet.setUser(u);

            arrayList.add(tweet);
        }

        return arrayList;
    }

    @SerializedName("statuses")
    @Expose
    private List<Status> statuses = new ArrayList<Status>();
    @SerializedName("search_metadata")
    @Expose
    private SearchMetadata searchMetadata;

    /**
     * 
     * @return
     *     The statuses
     */
    public List<Status> getStatuses() {
        return statuses;
    }

    /**
     * 
     * @param statuses
     *     The statuses
     */
    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    /**
     * 
     * @return
     *     The searchMetadata
     */
    public SearchMetadata getSearchMetadata() {
        return searchMetadata;
    }

    /**
     * 
     * @param searchMetadata
     *     The search_metadata
     */
    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.searchMetadata = searchMetadata;
    }

}
