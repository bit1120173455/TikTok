package com.example.zhaomingyang.tiktok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.zhaomingyang.tiktok.internet.Feed;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;
import java.util.zip.Inflater;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final String TAG="MyAdapter";
    private List<Feed> mFeeds;
    private static int viewHolderCount;
    private final ListItemClickListener mOnClickListener;

    public MyAdapter(List<Feed> feeds, ListItemClickListener listener) {
        viewHolderCount=0;
        mFeeds=feeds;
        mOnClickListener=listener;
        Log.d(TAG,"Created");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.video_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,viewGroup,shouldAttachToParentImmediately);
        MyViewHolder viewHolder=new MyViewHolder(view);

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + viewHolderCount);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: #" + position);
        myViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();//定义新变量的代价太大，需要实时维护，所以直接通过这个来返回
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final StandardGSYVideoPlayer mVideoPlayer;
        private final TextView mTextView;
        //private final Button mButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mVideoPlayer = itemView.findViewById(R.id.video_player);
            mTextView = itemView.findViewById(R.id.tv_info);
            //mButton = itemView.findViewById(R.id.btn_details);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onListItemClick(getAdapterPosition());
                }
            });
            /*mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onListButtonClick(getAdapterPosition());
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            int  clickedPosition = getAdapterPosition();
            if(mOnClickListener!=null){
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }

        public void bind(int position){
            //String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
            Log.d(TAG,"Bind RecyclerView on " + Integer.toString(position) + " item.");
            String video_source = mFeeds.get(position).getVideo_url();
            String img_source = mFeeds.get(position).getImage_url();
            String username = mFeeds.get(position).getUser_name();
            String student_id = mFeeds.get(position).getStudent_id();

            mTextView.setText(username+"\n"+student_id);

            mVideoPlayer.setUp(video_source,true,"视频");
            mVideoPlayer.setLooping(true);
            ImageView imageView = new ImageView(mVideoPlayer.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setContentDescription("Video");
            //使用Glide加载封面图
            Glide.with(imageView.getContext()).setDefaultRequestOptions(
                    new RequestOptions()
                            .frame(3000000)
                            .centerCrop()
                            .error(R.mipmap.xxx2)
                            .placeholder(R.mipmap.xxx1))
                    .load(img_source).into(imageView);
            mVideoPlayer.setThumbImageView(imageView);
            //mVideoPlayer.setImageResource(R.mipmap.xxx1);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public void setmFeeds(List<Feed> mFeeds) {
        this.mFeeds = mFeeds;
    }
}
