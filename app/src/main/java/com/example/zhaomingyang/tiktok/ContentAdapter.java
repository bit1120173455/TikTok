package com.example.zhaomingyang.tiktok;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaomingyang.tiktok.ContentAdapter;

import java.util.List;

public class ContentAdapter extends BaseAdapter {
    private Context context;
    private List<ContentModel> list;
    private int shoucang = 1;
    private int dingyue = 1;
    public ContentAdapter(Context context, List<ContentModel> list) {
        super();
        this.context = context;
        this.list = list;
    }



    @Override

    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }



    @Override

    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_menu, null);
            hold.imageView = (ImageView) convertView.findViewById(R.id.item_imageview);
            hold.textView = (TextView) convertView.findViewById(R.id.item_textview);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }

        ContentModel model = list.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (model.getId())
                {
                    case 1: {
                        AlertDialog.Builder builder =  new AlertDialog.Builder(ContentAdapter.this.context);
                        builder.setTitle("个人信息");
                        builder.setItems(new String[]{"赵明阳 1120173455", "吴正良 1120171220", "詹亘泽 1120173598"}, null);
                        builder.setPositiveButton("确定", null);
                        builder.setNegativeButton("取消",null);
                        builder.show();
                        break;
                    }
                    case 2:{
                        if (shoucang == 1){
                            hold.imageView.setImageResource(R.drawable.like1);
                            shoucang =2;
                        }
                        else{
                            hold.imageView.setImageResource(R.drawable.like);
                            shoucang = 1;
                        }
                        break;
                    }
                    case 3:{
                        if (dingyue == 1){
                            hold.imageView.setImageResource(R.drawable.love1);
                           dingyue =2;
                        }
                        else{
                            hold.imageView.setImageResource(R.drawable.love);
                            dingyue = 1;
                        }
                        break;
                    }
                    case 4:{
                        AlertDialog.Builder builder =  new AlertDialog.Builder(ContentAdapter.this.context);
                        builder.setTitle("视频转发");
                        builder.setItems(new String[]{"确定转发当前视频么？"}, null);
                        builder.setPositiveButton("确定", null);
                        builder.setNegativeButton("取消",null);
                        builder.show();
                    break;
                    }
                    default:{
                        ;break;
                    }

                }
            }
        });

        hold.imageView.setImageResource(list.get(position).getImageView());
        hold.textView.setText(list.get(position).getText());

        return convertView;
    }



    class ViewHold {
        public ImageView imageView;
        public TextView textView;
    }



}

