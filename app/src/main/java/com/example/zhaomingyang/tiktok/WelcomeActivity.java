package com.example.zhaomingyang.tiktok;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try{
            getSupportActionBar().hide();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_welcome);

        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3500);
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        mThread.start();
    }
}
