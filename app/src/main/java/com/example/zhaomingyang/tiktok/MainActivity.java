package com.example.zhaomingyang.tiktok;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaomingyang.tiktok.internet.network;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.ListItemClickListener{

//    ListView listView;
    private static String TAG="MainActivity";
    ContentAdapter adapter;
    List<ContentModel> list;
    DrawerLayout drawerLayout;
    private TextView tv_messages;

    private RecyclerView mVideo_List;
    private network mNetwork;

    private MyAdapter mMyadapter;

    private StandardGSYVideoPlayer c_Player;//当前正在播放的播放器

//  ImageView leftMenu;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_messages = findViewById(R.id.tv_messages);

        tv_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MessageActivity.class));
            }
        });

        mVideo_List = findViewById(R.id.rv_videos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mVideo_List.setLayoutManager(layoutManager);
        mVideo_List.setHasFixedSize(true);

        try {
            mNetwork = new network(this);
            mNetwork.fetchFeed();
            mMyadapter = new MyAdapter(mNetwork.getFeeds(), this);
            mVideo_List.setAdapter(mMyadapter);
            mVideo_List.addOnScrollListener(new RecyclerView.OnScrollListener() {

                private int lastCompletelyVisibleItemPosition;

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                            Toast.makeText(MainActivity.this, "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                    }
                    Log.d(TAG, "onScrolled: lastVisiblePosition=" + lastCompletelyVisibleItemPosition);
                    int firstCompletelyVisibleItemPosition = ((LinearLayoutManager)layoutManager).findFirstCompletelyVisibleItemPosition();
                    View view = layoutManager.findViewByPosition(firstCompletelyVisibleItemPosition);
                    if(view!=null)
                    {
                        StandardGSYVideoPlayer player = view.findViewById(R.id.video_player);
                        if(!player.isInPlayingState()) {
                            player.startPlayLogic();
                            c_Player = player;
                        }
                    }
                }
            });
        }catch (Exception e){
            Log.d(TAG,"Fail to init RecyclerView.");
        }
        //左菜单
        ListView listView = (ListView)findViewById(R.id.list_left_view);
        initLeftData();
        adapter = new ContentAdapter(this,list);
        listView.setAdapter(adapter);
//        getActionBar().hide();
        ImageView leftMenu = (ImageView) findViewById(R.id.leftmenu);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        leftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        //右菜单
        ListView listView2 = (ListView)findViewById(R.id.list_right_view);
        initRightData();
        adapter = new ContentAdapter(this,list);
        listView2.setAdapter(adapter);
        ImageView rightMenu = (ImageView) findViewById(R.id.rightmenu);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        rightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);

            }
        });

    }


    public void initLeftData() {

        list = new ArrayList<ContentModel>();

        list.add(new ContentModel(R.drawable.people,"我们", 1));

        list.add(new ContentModel(R.drawable.like,"收藏", 2));

        list.add(new ContentModel( R.drawable.love,"订阅", 3));

        list.add(new ContentModel(R.drawable.post, "转发", 4));

        list.add(new ContentModel(R.drawable.history, "历史", 4));

    }
    public void initRightData() {

        list = new ArrayList<ContentModel>();

        list.add(new ContentModel(R.drawable.robot, "我们", 1));

        list.add(new ContentModel( R.drawable.robot,"订阅", 2));

        list.add(new ContentModel( R.drawable.robot,"收藏", 3));

        list.add(new ContentModel(R.drawable.robot, "投票", 4));

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(c_Player!=null)
            c_Player.onVideoPause();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }


    public RecyclerView getmVideo_List() {
        return mVideo_List;
    }
}
