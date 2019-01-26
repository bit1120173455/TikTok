package com.example.zhaomingyang.tiktok.internet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostVideoResponse {
    @SerializedName("success")
    boolean isSuccess;

    @SerializedName("item")
    List<Feed> items;

    public boolean isSuccess() {
        return isSuccess;
    }

    public List<Feed> getItems() {
        return items;
    }
}
