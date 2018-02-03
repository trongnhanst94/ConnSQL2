package com.example.windows10gamer.connsql.Object;

import com.example.windows10gamer.connsql.Other.NotifyData;

/**
 * Created by EVRESTnhan on 1/4/2018.
 */

public class Message {
    String to;
    NotifyData notification;

    public Message(String to, NotifyData notification) {
        this.to = to;
        this.notification = notification;
    }

}
