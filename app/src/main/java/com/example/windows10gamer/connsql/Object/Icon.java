package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 3/13/2018.
 */

public class Icon {
    private String name;
    private int link;
    private int color;
    private String context;
    private int level;

    public Icon(String name, int link, int color, String context, int level) {
        this.name = name;
        this.link = link;
        this.color = color;
        this.context = context;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
