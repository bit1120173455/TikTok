package com.example.zhaomingyang.tiktok.bean;

import com.google.gson.annotations.SerializedName;

public class PostVideoResponse {

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @SerializedName("success")
    private boolean success;

    public Feed getItem() {
        return item;
    }

    public void setItem(Feed item) {
        this.item = item;
    }

    @SerializedName("item")
    private Feed item;


}
