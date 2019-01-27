package com.example.zhaomingyang.tiktok.internet;

import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("student_id")
    String student_id;

    @SerializedName("user_name")
    String user_name;

    @SerializedName("image_url")
    String image_url;

    @SerializedName("video_url")
    String video_url;


    public String getStudent_id() {
        return student_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getVideo_url() {
        return video_url;
    }
}
