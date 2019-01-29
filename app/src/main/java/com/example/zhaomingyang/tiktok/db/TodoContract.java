package com.example.zhaomingyang.tiktok.db;

import android.provider.BaseColumns;
public final class TodoContract {
    private TodoContract() { }

    public static class TodoEntry implements BaseColumns{

        public static final String TABLE_NAME= "Todo";

        public static final String MESSAGE =  "message";

        public static final String TIME =  "time";
        public static final String STATE = "state";

    }

    static final String SQL_CREAT_TODOS = "CREATE TABLE "+ TodoEntry.TABLE_NAME +

            " ( "+TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + TodoEntry.MESSAGE + " TEXT , "

            +TodoEntry.TIME + " TEXT, "+TodoEntry.STATE+" INTEGER )" ;

    static final String SQL_DELETE_TODOENTRIES = "DROP TABLE IF EXISTS "+TodoEntry.TABLE_NAME;

}