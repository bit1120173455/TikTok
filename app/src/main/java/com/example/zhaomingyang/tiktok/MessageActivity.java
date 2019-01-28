package com.example.zhaomingyang.tiktok;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaomingyang.tiktok.model.Message;
import com.example.zhaomingyang.tiktok.model.PullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements MessageAdapter.ListItemClickListener{
    private static final String TAG="dik dok";

    private MessageAdapter mMyAdapter;
    private RecyclerView mMessageListView;
    ContentAdapter adapter;
    List<ContentModel> list;
    DrawerLayout drawerLayout;
    private TextView tv_main;

    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        mMessageListView=findViewById(R.id.rv_list);

        //拍照页面跳转//
        ImageView imageView = (ImageView)findViewById(R.id.iv_add_video_left);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this,RecordVideoActivity.class);
                startActivity(intent);
            }
        });

        tv_main = findViewById(R.id.tv_main);
        tv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class));
            }
        });

        //左菜单
        ListView listView = (ListView)findViewById(R.id.list_left_view_message);
        initLeftData();
        adapter = new ContentAdapter(this,list);
        listView.setAdapter(adapter);
//        getActionBar().hide();
        ImageView leftMenu = (ImageView) findViewById(R.id.leftmenu_message);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout_message);
        leftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });
        ListView listView2 = (ListView)findViewById(R.id.list_right_view_message);
        initRightData();
        adapter = new ContentAdapter(this,list);
        listView2.setAdapter(adapter);
        ImageView rightMenu = (ImageView) findViewById(R.id.rightmenu_message);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout_message);
        rightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);

            }
        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMessageListView.setLayoutManager(layoutManager);
        mMessageListView.setHasFixedSize(true);

        //load data from assets/data.xml
        try {
            InputStream assetInput = getAssets().open("data.xml");
            List<Message> messages = PullParser.pull2xml(assetInput);

            mMyAdapter=new MessageAdapter(messages,this);
            Log.d(TAG,mMyAdapter.toString());
            mMessageListView.setAdapter(mMyAdapter);
            mMessageListView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                private int lastCompletelyVisibleItemPosition;

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                            Toast.makeText(MessageActivity.this, "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
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
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void initLeftData() {

        list = new ArrayList<ContentModel>();

        list.add(new ContentModel(R.drawable.people,"我们", 1));

        list.add(new ContentModel(R.drawable.love,"订阅", 2));

        list.add(new ContentModel( R.drawable.like,"收藏", 3));

        list.add(new ContentModel(R.drawable.post, "转发", 4));

        list.add(new ContentModel(R.drawable.history, "历史", 4));

    }
    public void initRightData() {

        list = new ArrayList<ContentModel>();

        list.add(new ContentModel(R.drawable.people, "我们", 1));

        list.add(new ContentModel( R.drawable.love,"订阅", 2));

        list.add(new ContentModel( R.drawable.like,"收藏", 3));

        list.add(new ContentModel(R.drawable.post, "转发", 4));

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Log.d(TAG, "onListItemClick: ");
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();

    }
}
