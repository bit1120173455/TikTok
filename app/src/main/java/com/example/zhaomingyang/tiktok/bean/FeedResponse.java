package com.example.zhaomingyang.tiktok.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedResponse {

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @SerializedName("success")
    private boolean success;

    public List<Feed> getFeed() {
        return feeds;
    }

    public void setFeed(List<Feed> feed) {
        this.feeds = feed;
    }

    @SerializedName("feeds")
    private List<Feed> feeds;

}
