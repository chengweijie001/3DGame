package com.huaxing.a3dgame.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.huaxing.a3dgame.model.GameNews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class SQLiteUtils {
    private Context context;

    public SQLiteUtils(Context context) {
        this.context = context;
    }

    /**
     * 保存数据到数据库
     * @param gameNews
     * @return
     */
    public synchronized boolean saveGamedataToSQLite(GameNews gameNews) {
        //创建数据库帮助类对象
        ContentValues values=null;
        long num=0;
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        if(!isExists(gameNews,database).moveToNext()) {
            database.beginTransaction();
            try {
                values= new ContentValues();
                values.put("id", gameNews.getId());
                values.put("typeid", gameNews.getTypeid());
                values.put("sortrank", gameNews.getSortrank());
                values.put("ismake", gameNews.getIsmake());
                values.put("channel", gameNews.getChannel());
                values.put("click", gameNews.getClick());
                values.put("title", gameNews.getTitle());
                values.put("shorttitle", gameNews.getShorttitle());
                values.put("writer", gameNews.getWriter());
                values.put("source", gameNews.getSource());
                values.put("litpic", gameNews.getLitpic());
                values.put("pubdate", gameNews.getPubdate());
                values.put("senddate", gameNews.getSenddate());
                values.put("mid", gameNews.getMid());
                values.put("keywords", gameNews.getKeywords());
                values.put("description", gameNews.getDescription());
                values.put("dutyadmin", gameNews.getDutyadmin());
                values.put("weight", gameNews.getWeight());
                values.put("typedir", gameNews.getTypedir());
                values.put("typename", gameNews.getTypename());
                values.put("isdefault", gameNews.getIsdefault());
                values.put("defaultname", gameNews.getDefaultname());
                values.put("namerule", gameNews.getNamerule());
                values.put("namerule2", gameNews.getNamerule2());
                values.put("sitepath", gameNews.getSitepath());
                values.put("arcurl", gameNews.getArcurl());
                values.put("typeurl", gameNews.getTypeurl());
                num=  database.insert("GameNews", null, values);
                Log.i("aaa","gameNews.getTypeid():"+gameNews.getTypeid());
                database.setTransactionSuccessful();


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (database != null && database.isOpen()) {
                    database.close();
                }
            }
        }

        return num > 0;


    }

    /**
     * 得到首页数据
     * @return
     */
    public LinkedList<HashMap<String,Object>> getGameNewsList(){
        //创建数据库帮助类
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from GameNews", null);
//        Cursor cursor=database.query("GameNews",null,"typeid=?",new String[]{typeId+""},null,null,null);
        LinkedList<HashMap<String,Object>> data = null;
        try {
            data = new LinkedList<HashMap<String,Object>>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (cursor.moveToNext()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String senddate = cursor.getString(cursor.getColumnIndex("senddate"));
            String click = cursor.getString(cursor.getColumnIndex("click"));
            String litpic = cursor.getString(cursor.getColumnIndex("litpic"));
            String arcurl = cursor.getString(cursor.getColumnIndex("arcurl"));
            map.put("title", title);
            map.put("senddate", senddate);
            map.put("click", click);
            map.put("litpic", litpic);
            map.put("arcurl", arcurl);
            data.add(map);
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return data;
    }
    /**
     * 根据数据Id从数据库得到数据
     */
    public LinkedList<HashMap<String,Object>> getGameNewsListByTypeId(int typeId) {
        //创建数据库帮助类
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Log.i("SubMainFragement",typeId+":getGameNewsList");
//        Cursor cursor = database.rawQuery("select * from GameNews where typeid=?", new String[]{typeId+""});
        Cursor cursor=database.query("GameNews",null,"typeid=?",new String[]{typeId+""},null,null,null);
        LinkedList<HashMap<String,Object>> data = null;
        try {
            data = new LinkedList<HashMap<String,Object>>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (cursor.moveToNext()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String senddate = cursor.getString(cursor.getColumnIndex("senddate"));
            String click = cursor.getString(cursor.getColumnIndex("click"));
            String litpic = cursor.getString(cursor.getColumnIndex("litpic"));
            String arcurl = cursor.getString(cursor.getColumnIndex("arcurl"));
            map.put("title", title);
            map.put("senddate", senddate);
            map.put("click", click);
            map.put("litpic", litpic);
            map.put("arcurl", arcurl);
            data.add(map);
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return data;
    }

    /**
     * 判断当前数据是否存在
     * @param gameNews
     * @return
     */
    public  Cursor isExists(GameNews gameNews,SQLiteDatabase db){
        if(gameNews!=null){
         return   db.query("GameNews",null,"id=?",new String[]{gameNews.getId()},null,null,null);
        }

        return null;
    }

    /**
     * 清空数据库
     */
    public void delAllDataFromSQLite(){
         MySQLiteOpenHelper helper=new MySQLiteOpenHelper(context);
        SQLiteDatabase database=helper.getReadableDatabase();
        database.delete("GameNews",null,null);
        if(database!=null&&database.isOpen()){
            database.close();
        }
    }
}
