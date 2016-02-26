
package com.codepath.apps.twitter.models.gson.userlist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class UserListResponse {

    public static UserListResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        UserListResponse userListResponse = gson.fromJson(response, UserListResponse.class);
        return userListResponse;
    }

    @SerializedName("previous_cursor")
    @Expose
    private Long previousCursor;
    @SerializedName("previous_cursor_str")
    @Expose
    private String previousCursorStr;
    @SerializedName("next_cursor")
    @Expose
    private Long nextCursor;
    @SerializedName("users")
    @Expose
    private ArrayList<User> users = new ArrayList<User>();
    @SerializedName("next_cursor_str")
    @Expose
    private String nextCursorStr;

    /**
     * 
     * @return
     *     The previousCursor
     */
    public Long getPreviousCursor() {
        return previousCursor;
    }

    /**
     * 
     * @param previousCursor
     *     The previous_cursor
     */
    public void setPreviousCursor(Long previousCursor) {
        this.previousCursor = previousCursor;
    }

    /**
     * 
     * @return
     *     The previousCursorStr
     */
    public String getPreviousCursorStr() {
        return previousCursorStr;
    }

    /**
     * 
     * @param previousCursorStr
     *     The previous_cursor_str
     */
    public void setPreviousCursorStr(String previousCursorStr) {
        this.previousCursorStr = previousCursorStr;
    }

    /**
     * 
     * @return
     *     The nextCursor
     */
    public Long getNextCursor() {
        return nextCursor;
    }

    /**
     * 
     * @param nextCursor
     *     The next_cursor
     */
    public void setNextCursor(Long nextCursor) {
        this.nextCursor = nextCursor;
    }

    /**
     * 
     * @return
     *     The users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * 
     * @param users
     *     The users
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * 
     * @return
     *     The nextCursorStr
     */
    public String getNextCursorStr() {
        return nextCursorStr;
    }

    /**
     * 
     * @param nextCursorStr
     *     The next_cursor_str
     */
    public void setNextCursorStr(String nextCursorStr) {
        this.nextCursorStr = nextCursorStr;
    }

}
