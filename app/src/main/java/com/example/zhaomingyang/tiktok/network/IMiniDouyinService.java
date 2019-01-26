package com.example.zhaomingyang.tiktok.network;



import com.example.zhaomingyang.tiktok.bean.FeedResponse;
import com.example.zhaomingyang.tiktok.bean.PostVideoResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IMiniDouyinService {
    @Multipart
    @POST("minidouyin/video")
    Call<PostVideoResponse> createVideo(
            @Query("student_id") String param1,
            @Query("user_name") String param2,
            @Part MultipartBody.Part file1, @Part MultipartBody.Part file2);
    //url: http://10.108.10.39:8080/minidouyin/feed
    @GET("minidouyin/feed")
    Call<FeedResponse> allFeed();
}
