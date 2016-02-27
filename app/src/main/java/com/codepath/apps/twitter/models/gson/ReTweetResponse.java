package com.codepath.apps.twitter.models.gson;

/**
 * Created by Shyam Rokde on 2/26/16.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

@Generated("org.jsonschema2pojo")
public class ReTweetResponse {

  public static ReTweetResponse parseJSON(String response){
    Gson gson = new GsonBuilder().create();
    ReTweetResponse reTweetResponse = gson.fromJson(response, ReTweetResponse.class);
    return reTweetResponse;
  }

  @SerializedName("truncated")
  @Expose
  private Boolean truncated;
  @SerializedName("retweeted")
  @Expose
  private Boolean retweeted;
  @SerializedName("id_str")
  @Expose
  private String idStr;
  @SerializedName("coordinates")
  @Expose
  private Object coordinates;
  @SerializedName("in_reply_to_screen_name")
  @Expose
  private Object inReplyToScreenName;
  @SerializedName("in_reply_to_status_id_str")
  @Expose
  private Object inReplyToStatusIdStr;
  @SerializedName("geo")
  @Expose
  private Object geo;
  @SerializedName("in_reply_to_status_id")
  @Expose
  private Object inReplyToStatusId;
  @SerializedName("contributors")
  @Expose
  private Object contributors;
  @SerializedName("source")
  @Expose
  private String source;
  @SerializedName("in_reply_to_user_id_str")
  @Expose
  private Object inReplyToUserIdStr;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("favorited")
  @Expose
  private Boolean favorited;
  @SerializedName("retweet_count")
  @Expose
  private Long retweetCount;
  @SerializedName("in_reply_to_user_id")
  @Expose
  private Object inReplyToUserId;
  @SerializedName("text")
  @Expose
  private String text;

  /**
   *
   * @return
   * The truncated
   */
  public Boolean getTruncated() {
    return truncated;
  }

  /**
   *
   * @param truncated
   * The truncated
   */
  public void setTruncated(Boolean truncated) {
    this.truncated = truncated;
  }

  /**
   *
   * @return
   * The retweeted
   */
  public Boolean getRetweeted() {
    return retweeted;
  }

  /**
   *
   * @param retweeted
   * The retweeted
   */
  public void setRetweeted(Boolean retweeted) {
    this.retweeted = retweeted;
  }

  /**
   *
   * @return
   * The idStr
   */
  public String getIdStr() {
    return idStr;
  }

  /**
   *
   * @param idStr
   * The id_str
   */
  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  /**
   *
   * @return
   * The coordinates
   */
  public Object getCoordinates() {
    return coordinates;
  }

  /**
   *
   * @param coordinates
   * The coordinates
   */
  public void setCoordinates(Object coordinates) {
    this.coordinates = coordinates;
  }

  /**
   *
   * @return
   * The inReplyToScreenName
   */
  public Object getInReplyToScreenName() {
    return inReplyToScreenName;
  }

  /**
   *
   * @param inReplyToScreenName
   * The in_reply_to_screen_name
   */
  public void setInReplyToScreenName(Object inReplyToScreenName) {
    this.inReplyToScreenName = inReplyToScreenName;
  }

  /**
   *
   * @return
   * The inReplyToStatusIdStr
   */
  public Object getInReplyToStatusIdStr() {
    return inReplyToStatusIdStr;
  }

  /**
   *
   * @param inReplyToStatusIdStr
   * The in_reply_to_status_id_str
   */
  public void setInReplyToStatusIdStr(Object inReplyToStatusIdStr) {
    this.inReplyToStatusIdStr = inReplyToStatusIdStr;
  }

  /**
   *
   * @return
   * The geo
   */
  public Object getGeo() {
    return geo;
  }

  /**
   *
   * @param geo
   * The geo
   */
  public void setGeo(Object geo) {
    this.geo = geo;
  }

  /**
   *
   * @return
   * The inReplyToStatusId
   */
  public Object getInReplyToStatusId() {
    return inReplyToStatusId;
  }

  /**
   *
   * @param inReplyToStatusId
   * The in_reply_to_status_id
   */
  public void setInReplyToStatusId(Object inReplyToStatusId) {
    this.inReplyToStatusId = inReplyToStatusId;
  }

  /**
   *
   * @return
   * The contributors
   */
  public Object getContributors() {
    return contributors;
  }

  /**
   *
   * @param contributors
   * The contributors
   */
  public void setContributors(Object contributors) {
    this.contributors = contributors;
  }

  /**
   *
   * @return
   * The source
   */
  public String getSource() {
    return source;
  }

  /**
   *
   * @param source
   * The source
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   *
   * @return
   * The inReplyToUserIdStr
   */
  public Object getInReplyToUserIdStr() {
    return inReplyToUserIdStr;
  }

  /**
   *
   * @param inReplyToUserIdStr
   * The in_reply_to_user_id_str
   */
  public void setInReplyToUserIdStr(Object inReplyToUserIdStr) {
    this.inReplyToUserIdStr = inReplyToUserIdStr;
  }

  /**
   *
   * @return
   * The createdAt
   */
  public String getCreatedAt() {
    return createdAt;
  }

  /**
   *
   * @param createdAt
   * The created_at
   */
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  /**
   *
   * @return
   * The favorited
   */
  public Boolean getFavorited() {
    return favorited;
  }

  /**
   *
   * @param favorited
   * The favorited
   */
  public void setFavorited(Boolean favorited) {
    this.favorited = favorited;
  }

  /**
   *
   * @return
   * The retweetCount
   */
  public Long getRetweetCount() {
    return retweetCount;
  }

  /**
   *
   * @param retweetCount
   * The retweet_count
   */
  public void setRetweetCount(Long retweetCount) {
    this.retweetCount = retweetCount;
  }

  /**
   *
   * @return
   * The inReplyToUserId
   */
  public Object getInReplyToUserId() {
    return inReplyToUserId;
  }

  /**
   *
   * @param inReplyToUserId
   * The in_reply_to_user_id
   */
  public void setInReplyToUserId(Object inReplyToUserId) {
    this.inReplyToUserId = inReplyToUserId;
  }

  /**
   *
   * @return
   * The text
   */
  public String getText() {
    return text;
  }

  /**
   *
   * @param text
   * The text
   */
  public void setText(String text) {
    this.text = text;
  }
}
