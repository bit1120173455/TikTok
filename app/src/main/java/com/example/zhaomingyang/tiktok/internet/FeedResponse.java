package com.example.zhaomingyang.tiktok.internet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedResponse {
    @SerializedName("feeds")
    List<Feed> feeds;
    @SerializedName("success")
    boolean isSuccess;

    public List<Feed> getFeeds(){
        return feeds;
    }

    public boolean isSuccess(){
        return isSuccess;
    }
}
