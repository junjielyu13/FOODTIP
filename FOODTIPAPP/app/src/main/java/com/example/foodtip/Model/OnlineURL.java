package com.example.foodtip.Model;

import java.io.Serializable;

public class OnlineURL implements Serializable {
    private String url;

    public OnlineURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
