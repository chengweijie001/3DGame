package com.huaxing.a3dgame.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/6.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context) {
        super(context, "gamedata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL("create table if not exists GameNews(id integer primary key,typeid,sortrank,ismake,channel,click,title,shorttitle," +
                "writer,source,litpic,pubdate,senddate,mid,keywords,description,dutyadmin,weight," +
                "typedir,typename,isdefault,defaultname,namerule,namerule2,sitepath,arcurl,typeurl)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
