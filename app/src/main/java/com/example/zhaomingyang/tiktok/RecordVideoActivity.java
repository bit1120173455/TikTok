package com.example.zhaomingyang.tiktok;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.zhaomingyang.tiktok.bean.Feed;
import com.example.zhaomingyang.tiktok.bean.PostVideoResponse;
import com.example.zhaomingyang.tiktok.internet.ResourceUtils;
import com.example.zhaomingyang.tiktok.network.IMiniDouyinService;
import com.example.zhaomingyang.tiktok.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.zhaomingyang.tiktok.bean.*;
import com.example.zhaomingyang.tiktok.network.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecordVideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private static final int REQUEST_VIDEO_CAPTURE = 2;
    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    File mediaFile;
    private static final int PICK_IMAGE = 10;
    private static final int PICK_VIDEO = 20;
    private static final String TAG = "RecordVideoActivity";
    private RecyclerView mRv;
    private List<Feed> mFeeds = new ArrayList<>();
    public Uri mSelectedImage;
    private Uri mSelectedVideo;
    public Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);


        //////
        Button button_main=(Button) findViewById(R.id.tv_main_video);
        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordVideoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button button_message=(Button) findViewById(R.id.message_video);
        button_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordVideoActivity.this,MessageActivity.class);
                startActivity(intent);
                finish();
            }
        });

     //post
    mBtn = (Button)findViewById(R.id.button_post_it);
    mBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String s = mBtn.getText().toString();

            if (getString(R.string.select_an_image).equals(s)) {

                chooseImage();

            } else if (getString(R.string.select_a_video).equals(s)) {

                chooseVideo();

            } else if (getString(R.string.post_it).equals(s)) {

                if (mSelectedVideo != null && mSelectedImage != null) {

                    postVideo();

                } else {

                    throw new IllegalArgumentException("error data uri, mSelectedVideo = " + mSelectedVideo + ", mSelectedImage = " + mSelectedImage);

                }

            } else if ((getString(R.string.success_try_refresh).equals(s))) {

                mBtn.setText(R.string.select_an_image);

            }
        }
    });


        videoView = findViewById(R.id.video_view);
        if (takeVideoIntent.resolveActivity(getPackageManager())!=null)
                    startActivityForResult(takeVideoIntent,REQUEST_VIDEO_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        } else if (resultCode == RESULT_OK && null != data) {
            if (requestCode == PICK_IMAGE) {

                mSelectedImage = data.getData();

                Log.d(TAG, "selectedImage = " + mSelectedImage);

                mBtn.setText(R.string.select_a_video);

            } else if (requestCode == PICK_VIDEO) {

                mSelectedVideo = data.getData();

                Log.d(TAG, "mSelectedVideo = " + mSelectedVideo);

                mBtn.setText(R.string.post_it);

            }

        }
    }
    //video
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE: {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1003);
                }
                startActivityForResult(takeVideoIntent,REQUEST_VIDEO_CAPTURE);
                break;
            }
            case REQUEST_VIDEO_CAPTURE: {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1003);
                }
                startActivityForResult(takeVideoIntent,REQUEST_VIDEO_CAPTURE);
                break;
            }
            default: {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1003);
                }
                startActivityForResult(takeVideoIntent,REQUEST_VIDEO_CAPTURE);
                break;
            }
        }
    }

    /////////



    public void chooseImage() {

        //

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"select picture"),PICK_IMAGE);

    }
    public void chooseVideo() {

        //

        Intent intent = new Intent();

        intent.setType("video/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"select video"),PICK_VIDEO);

    }

    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {

        // if NullPointerException thrown, try to allow storage permission in system settings

        File f = new File(ResourceUtils.getRealPath(RecordVideoActivity.this, uri));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);

        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);

    }



    private void postVideo() {

        mBtn.setText("POSTING...");

        mBtn.setEnabled(false);



        //

        Retrofit retrofit =new Retrofit.Builder()

                .baseUrl("http://10.108.10.39:8080/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        final IMiniDouyinService iMiniDouyinService = retrofit.create(IMiniDouyinService.class);

        new Thread(){

            @Override

            public  void run(){

                Call<PostVideoResponse> call = iMiniDouyinService.createVideo("1120173455","赵明阳",

                        getMultipartFromUri("cover_image",mSelectedImage),

                        getMultipartFromUri("video",mSelectedVideo));

                call.enqueue(new Callback<PostVideoResponse>() {

                    @Override

                    public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {

                        runOnUiThread(()-> Toast.makeText(RecordVideoActivity.this, "上传成功", Toast.LENGTH_SHORT).show());

                        Intent intent = new Intent(RecordVideoActivity.this,MainActivity.class);
                        startActivity(intent);
                    }



                    @Override

                    public void onFailure(Call<PostVideoResponse> call, Throwable t) {

                        runOnUiThread(()->Toast.makeText(RecordVideoActivity.this, "上传失败", Toast.LENGTH_SHORT).show());

                    }

                });

            }

        }.start();

        // if success, make a text Toast and show

    }


//    public  void getResponse(Callback<FeedResponse> callback) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//
//                .baseUrl("http://10.108.10.39:8080/")
//
//                .addConverterFactory(GsonConverterFactory.create())
//
//                .build();
//
//        retrofit.create(IMiniDouyinService.class).getFeed().
//
//                enqueue(callback);
//
//    }



}
