package com.example.windows10gamer.connsql.Object;

import com.example.windows10gamer.connsql.Other.NotifyData;

/**
 * Created by EVRESTnhan on 2/22/2018.
 */

public class Message {
    String to;
    NotifyData notification;

    public Message(String to, NotifyData notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotifyData getNotification() {
        return notification;
    }

    public void setNotification(NotifyData notification) {
        this.notification = notification;
    }
}