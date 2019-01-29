package com.example.zhaomingyang.tiktok.VideoPlayer;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class WZLPlayer extends StandardGSYVideoPlayer {
    public WZLPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
        mTopContainer.setAlpha(0);
    }

    public WZLPlayer(Context context) {
        super(context);
        mTopContainer.setAlpha(0);
    }

    public WZLPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTopContainer.setAlpha(0);
    }

    public String get_Video_url(){
        return mOriginUrl;
    }
}
