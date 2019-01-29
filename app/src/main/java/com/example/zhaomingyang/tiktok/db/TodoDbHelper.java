package com.example.zhaomingyang.tiktok.db;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;



import static com.example.zhaomingyang.tiktok.db.TodoContract.SQL_CREAT_TODOS;

import static com.example.zhaomingyang.tiktok.db.TodoContract.SQL_DELETE_TODOENTRIES;

public class TodoDbHelper extends SQLiteOpenHelper {

    public TodoDbHelper(Context context) {

        super(context, "todo.db", null, 1);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREAT_TODOS);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_TODOENTRIES);

        onCreate(db);

    }



}
