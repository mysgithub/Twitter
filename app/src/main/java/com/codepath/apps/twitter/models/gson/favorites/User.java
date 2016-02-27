package com.codepath.apps.twitter.models.gson.favorites;

/**
 * Created by Shyam Rokde on 2/27/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

@Generated("org.jsonschema2pojo")
public class User {

  @SerializedName("contributors_enabled")
  @Expose
  private Boolean contributorsEnabled;
  @SerializedName("favourites_count")
  @Expose
  private Long favouritesCount;
  @SerializedName("follow_request_sent")
  @Expose
  private Boolean followRequestSent;
  @SerializedName("followers_count")
  @Expose
  private Long followersCount;
  @SerializedName("following")
  @Expose
  private Boolean following;
  @SerializedName("friends_count")
  @Expose
  private Long friendsCount;
  @SerializedName("geo_enabled")
  @Expose
  private Boolean geoEnabled;
  @SerializedName("id")
  @Expose
  private Long id;
  @SerializedName("id_str")
  @Expose
  private String idStr;
  @SerializedName("is_translator")
  @Expose
  private Boolean isTranslator;
  @SerializedName("screen_name")
  @Expose
  private String screenName;
  @SerializedName("show_all_inline_media")
  @Expose
  private Boolean showAllInlineMedia;
  @SerializedName("statuses_count")
  @Expose
  private Long statusesCount;
  @SerializedName("verified")
  @Expose
  private Boolean verified;

  /**
   *
   * @return
   * The contributorsEnabled
   */
  public Boolean getContributorsEnabled() {
    return contributorsEnabled;
  }

  /**
   *
   * @param contributorsEnabled
   * The contributors_enabled
   */
  public void setContributorsEnabled(Boolean contributorsEnabled) {
    this.contributorsEnabled = contributorsEnabled;
  }

  /**
   *
   * @return
   * The favouritesCount
   */
  public Long getFavouritesCount() {
    return favouritesCount;
  }

  /**
   *
   * @param favouritesCount
   * The favourites_count
   */
  public void setFavouritesCount(Long favouritesCount) {
    this.favouritesCount = favouritesCount;
  }

  /**
   *
   * @return
   * The followRequestSent
   */
  public Boolean getFollowRequestSent() {
    return followRequestSent;
  }

  /**
   *
   * @param followRequestSent
   * The follow_request_sent
   */
  public void setFollowRequestSent(Boolean followRequestSent) {
    this.followRequestSent = followRequestSent;
  }

  /**
   *
   * @return
   * The followersCount
   */
  public Long getFollowersCount() {
    return followersCount;
  }

  /**
   *
   * @param followersCount
   * The followers_count
   */
  public void setFollowersCount(Long followersCount) {
    this.followersCount = followersCount;
  }

  /**
   *
   * @return
   * The following
   */
  public Boolean getFollowing() {
    return following;
  }

  /**
   *
   * @param following
   * The following
   */
  public void setFollowing(Boolean following) {
    this.following = following;
  }

  /**
   *
   * @return
   * The friendsCount
   */
  public Long getFriendsCount() {
    return friendsCount;
  }

  /**
   *
   * @param friendsCount
   * The friends_count
   */
  public void setFriendsCount(Long friendsCount) {
    this.friendsCount = friendsCount;
  }

  /**
   *
   * @return
   * The geoEnabled
   */
  public Boolean getGeoEnabled() {
    return geoEnabled;
  }

  /**
   *
   * @param geoEnabled
   * The geo_enabled
   */
  public void setGeoEnabled(Boolean geoEnabled) {
    this.geoEnabled = geoEnabled;
  }

  /**
   *
   * @return
   * The id
   */
  public Long getId() {
    return id;
  }

  /**
   *
   * @param id
   * The id
   */
  public void setId(Long id) {
    this.id = id;
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
   * The isTranslator
   */
  public Boolean getIsTranslator() {
    return isTranslator;
  }

  /**
   *
   * @param isTranslator
   * The is_translator
   */
  public void setIsTranslator(Boolean isTranslator) {
    this.isTranslator = isTranslator;
  }

  /**
   *
   * @return
   * The screenName
   */
  public String getScreenName() {
    return screenName;
  }

  /**
   *
   * @param screenName
   * The screen_name
   */
  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  /**
   *
   * @return
   * The showAllInlineMedia
   */
  public Boolean getShowAllInlineMedia() {
    return showAllInlineMedia;
  }

  /**
   *
   * @param showAllInlineMedia
   * The show_all_inline_media
   */
  public void setShowAllInlineMedia(Boolean showAllInlineMedia) {
    this.showAllInlineMedia = showAllInlineMedia;
  }

  /**
   *
   * @return
   * The statusesCount
   */
  public Long getStatusesCount() {
    return statusesCount;
  }

  /**
   *
   * @param statusesCount
   * The statuses_count
   */
  public void setStatusesCount(Long statusesCount) {
    this.statusesCount = statusesCount;
  }

  /**
   *
   * @return
   * The verified
   */
  public Boolean getVerified() {
    return verified;
  }

  /**
   *
   * @param verified
   * The verified
   */
  public void setVerified(Boolean verified) {
    this.verified = verified;
  }

}
