package com.example.windows10gamer.connsql.Other.notifications;

/**
 * Created by EVRESTnhan on 2/22/2018.
 */

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationRequestModel {

    @SerializedName("data")
    private NotificationData mData;
    @SerializedName("to")
    private String mTo;

    public NotificationData getData() {
        return mData;
    }

    public void setData(NotificationData data) {
        mData = data;
    }

    public String getTo() {
        return mTo;
    }

    public void setTo(String to) {
        mTo = to;
    }
}
