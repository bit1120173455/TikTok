package com.example.zhaomingyang.tiktok.internet;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IMiniTikTokService {

    @Multipart

    @POST("/minidouyin/video")
    Call<PostVideoResponse> CreateVideo(
        @Query("student_id")
        String studentId,

        @Query("user_name")
        String userName,

        @Part MultipartBody.Part img,
        @Part MultipartBody.Part video
    );
    @GET("/minidouyin/feed")
    Call<FeedResponse> feedResponse();
}
