package com.example.zhaomingyang.tiktok;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.zhaomingyang.tiktok.model.Message;
import com.example.zhaomingyang.tiktok.model.PullParser;

import java.io.InputStream;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements MessageAdapter.ListItemClickListener{
    private static final String TAG="dik dok";

    private MessageAdapter mMyAdapter;
    private RecyclerView mMessageListView;

    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        mMessageListView=findViewById(R.id.rv_list);

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
