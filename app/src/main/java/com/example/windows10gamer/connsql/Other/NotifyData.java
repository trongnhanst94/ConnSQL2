package com.example.windows10gamer.connsql.Other;

/**
 * Created by EVRESTnhan on 2/22/2018.
 */

public class NotifyData {
    String title;
    String body;

    public NotifyData(String title, String body ) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
