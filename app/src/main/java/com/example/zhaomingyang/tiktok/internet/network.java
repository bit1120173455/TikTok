package com.example.zhaomingyang.tiktok.internet;

import android.content.Context;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class network {

    private static String BASE_URL="http://10.108.10.39:8080";
    private static String TAG=new String();
    private static String msg=new String();
    private static List<Feed> feeds=new ArrayList<>();
    private static String STUDENT_ID="1120173598";
    private static String USER_NAME="ZGZ";
    private static String IMG_NAME="name";
    private static String IMGUrl="url";
    private static String VIDEO_NAME="url";
    private static List<Feed> items=new ArrayList<>();
    public static void fetchFeed(final RecyclerView rv){
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(IMiniTikTokService.class ).feedResponse()
                .enqueue(new Callback<FeedResponse>() {
                    @Override
                    public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                        Log.d(TAG , "get response!");
                        feeds=response.body().getFeeds();
                        //rv.getAdapter().refrush(DatabaseUtils.loadItemsFromDatabase());
                    }

                    @Override
                    public void onFailure(Call<FeedResponse> call, Throwable t) {
                        Log.d(TAG,t.getMessage());
                    }
                });

    }

    private static MultipartBody.Part getMultipartFromUri(String name, Uri uri, Context context) {
        // if NullPointerException thrown, try to allow storage permission in system settings
        File f = new File(ResourceUtils.getRealPath(context, uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }


    public static void postvideo(Uri imgUri,Uri videoUrl,Context context,final RecyclerView rv){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(IMiniTikTokService.class).CreateVideo(
            STUDENT_ID,
            USER_NAME,
            getMultipartFromUri(IMG_NAME,imgUri,context),
            getMultipartFromUri(VIDEO_NAME,videoUrl,context)
        ).enqueue(new Callback<PostVideoResponse>(){
            @Override
            public void onResponse(Call<PostVideoResponse> call,Response<PostVideoResponse>response){
                Log.d(TAG,"post response");
                items=response.body().getItems();

                Feed item=items.get(0);
                rv.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PostVideoResponse> call,Throwable t){
                Log.d(TAG,"post failed!");
            }
        }
        );
    }
}
