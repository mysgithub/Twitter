
package com.codepath.apps.twitter.models.gson.message;

import com.codepath.apps.twitter.models.Message;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.utils.TwitterUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.Arrays;

@Generated("org.jsonschema2pojo")
public class DirectMessageResponse {

    ArrayList<DirectMessageResponse> arrayList;

    public DirectMessageResponse(){
        arrayList = new ArrayList<>();
    }

    public static DirectMessageResponse parseJSON(String jsonArray){
        DirectMessageResponse directMessageResponse = new DirectMessageResponse();

        Gson gson = new GsonBuilder().create();
        DirectMessageResponse[] mcArray = gson.fromJson(jsonArray, DirectMessageResponse[].class);
        directMessageResponse.arrayList = new ArrayList<>(Arrays.asList(mcArray));

        return directMessageResponse;
    }

    public ArrayList<Message> getMessageList(){

        ArrayList<Message> messageArrayList = new ArrayList<>();

        for(DirectMessageResponse messageResponse : arrayList){
            Message message = new Message();
            message.setMessage(messageResponse.getText());
            message.setCreatedAt(TwitterUtil.getDateFromString(messageResponse.getSender().getCreatedAt()));
            User u = new User();
            u.setProfileImageUrl(messageResponse.getSender().getProfileImageUrl());
            u.setUid(messageResponse.getSender().getId());
            u.setName(messageResponse.getSender().getName());
            u.setScreenName(messageResponse.getSender().getScreenName());
            message.setUser(u);

            messageArrayList.add(message);
        }

        return messageArrayList;
    }

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("entities")
    @Expose
    private Entities entities;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("id_str")
    @Expose
    private String idStr;
    @SerializedName("recipient")
    @Expose
    private Recipient recipient;
    @SerializedName("recipient_id")
    @Expose
    private Long recipientId;
    @SerializedName("recipient_screen_name")
    @Expose
    private String recipientScreenName;
    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("sender_id")
    @Expose
    private Long senderId;
    @SerializedName("sender_screen_name")
    @Expose
    private String senderScreenName;
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The entities
     */
    public Entities getEntities() {
        return entities;
    }

    /**
     * 
     * @param entities
     *     The entities
     */
    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The idStr
     */
    public String getIdStr() {
        return idStr;
    }

    /**
     * 
     * @param idStr
     *     The id_str
     */
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    /**
     * 
     * @return
     *     The recipient
     */
    public Recipient getRecipient() {
        return recipient;
    }

    /**
     * 
     * @param recipient
     *     The recipient
     */
    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    /**
     * 
     * @return
     *     The recipientId
     */
    public Long getRecipientId() {
        return recipientId;
    }

    /**
     * 
     * @param recipientId
     *     The recipient_id
     */
    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * 
     * @return
     *     The recipientScreenName
     */
    public String getRecipientScreenName() {
        return recipientScreenName;
    }

    /**
     * 
     * @param recipientScreenName
     *     The recipient_screen_name
     */
    public void setRecipientScreenName(String recipientScreenName) {
        this.recipientScreenName = recipientScreenName;
    }

    /**
     * 
     * @return
     *     The sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * 
     * @param sender
     *     The sender
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    /**
     * 
     * @return
     *     The senderId
     */
    public Long getSenderId() {
        return senderId;
    }

    /**
     * 
     * @param senderId
     *     The sender_id
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    /**
     * 
     * @return
     *     The senderScreenName
     */
    public String getSenderScreenName() {
        return senderScreenName;
    }

    /**
     * 
     * @param senderScreenName
     *     The sender_screen_name
     */
    public void setSenderScreenName(String senderScreenName) {
        this.senderScreenName = senderScreenName;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

}
