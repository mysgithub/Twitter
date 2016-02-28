package com.codepath.apps.twitter.models;

import android.os.Parcelable;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by Shyam Rokde on 2/27/16.
 */
@Parcel
public class Message implements Parcelable {

  private User user;
  private String message;
  private Date createdAt;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(android.os.Parcel dest, int flags) {
    dest.writeParcelable(this.user, 0);
    dest.writeString(this.message);
    dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
  }

  public Message() {
  }

  protected Message(android.os.Parcel in) {
    this.user = in.readParcelable(User.class.getClassLoader());
    this.message = in.readString();
    long tmpCreatedAt = in.readLong();
    this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
  }

  public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
    public Message createFromParcel(android.os.Parcel source) {
      return new Message(source);
    }

    public Message[] newArray(int size) {
      return new Message[size];
    }
  };
}
