package com.example.douban.DATABASE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class History_database extends SQLiteOpenHelper {
    static String TABLE_HISTORY = "CREATE TABLE history(id integer PRIMARY KEY AUTOINCREMENT,value text)";
    public History_database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("History_database","当前版本："+i+"----->更新版本"+i1);
    }
}
