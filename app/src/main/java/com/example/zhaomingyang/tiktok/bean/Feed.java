package com.example.zhaomingyang.tiktok.bean;

import com.google.gson.annotations.SerializedName;

public class Feed {

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    /**
     * The class of videos and pictures to be posted.
     */
    @SerializedName("student_id")
    private String student_id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @SerializedName("user_name")
    private String user_name;

    public String getCover_image() {
        return image_url;
    }

    public void setCover_image(String cover_image) {
        this.image_url = cover_image;
    }

    @SerializedName("image_url")
    private String image_url;

    public String getVideo() {
        return video_url;
    }

    public void setVideo(String video) {
        this.video_url = video;
    }

    @SerializedName("video_url")
    private String video_url;
}
