package com.huaxing.a3dgame.utils;

import android.os.Handler;

import com.huaxing.a3dgame.model.Game;
import com.huaxing.a3dgame.model.GameNews;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 解析Json数据的工具类
 */
public class JsonUtils {

    //游戏新闻Json数据
    public static List<GameNews> getGames(String json) throws  Exception{
        if(json!=null){
            List<GameNews> data=new ArrayList<GameNews>();
            JSONObject object=new JSONObject(json);
            JSONObject object1=object.getJSONObject("data");
            for(int i=0;i<20;i++){
                JSONObject jsonObject=object1.getJSONObject(i+"");
                String id=jsonObject.getString("id");
                String typeid=jsonObject.getString("typeid");
                String sortrank=jsonObject.getString("sortrank");
                String ismake=jsonObject.getString("ismake");
                String channel=jsonObject.getString("channel");
                String click=jsonObject.getString("click");
                String title=jsonObject.getString("title");
                String shorttitle=jsonObject.getString("shorttitle");
                String writer=jsonObject.getString("writer");
                String source=jsonObject.getString("source");
                String litpic="http://www.3dmgame.com"+jsonObject.getString("litpic");
                String pubdate=jsonObject.getString("pubdate");
                String senddate=jsonObject.getString("senddate");
                String mid=jsonObject.getString("mid");
                String keywords=jsonObject.getString("keywords");
                String description=jsonObject.getString("description");
                String dutyadmin=jsonObject.getString("dutyadmin");
                String weight=jsonObject.getString("weight");
                String typedir=jsonObject.getString("typedir");
                String typename=jsonObject.getString("typename");
                String isdefault=jsonObject.getString("isdefault");
                String defaultname=jsonObject.getString("defaultname");
                String namerule=jsonObject.getString("namerule");
                String namerule2=jsonObject.getString("namerule2");
                String sitepath=jsonObject.getString("sitepath");
                String arcurl=jsonObject.getString("arcurl");
                String typeurl=jsonObject.getString("typeurl");
                GameNews gameNews=new GameNews(id,typeid,sortrank,ismake,channel,click,title,shorttitle,
                        writer,source,litpic,pubdate,senddate,mid,keywords,description,dutyadmin,weight,
                        typedir,typename,isdefault,defaultname,namerule,namerule2,sitepath,arcurl,typeurl);
                data.add(gameNews);
            }
            return data;
        }

        return null;
    }
  //获得游戏新闻的方法
    public static LinkedList<HashMap<String,Object>> getLinkedList(String json){
        LinkedList<HashMap<String,Object>> data=new LinkedList<>();
        List<GameNews> gameNewses=new ArrayList<GameNews>();
        try {
            gameNewses=getGames(json);
            for (int i=0;i<gameNewses.size();i++){
                HashMap<String,Object>map=new HashMap<>();
                String title = gameNewses.get(i).getTitle();
                String senddate = gameNewses.get(i).getSenddate();
                String click = gameNewses.get(i).getClick();
                String litpic = gameNewses.get(i).getLitpic();
                String arcurl = gameNewses.get(i).getArcurl();
                map.put("title",title);
                map.put("senddate",senddate);
                map.put("click",click);
                map.put("litpic",litpic);
                map.put("arcurl",arcurl);
                data.add(map);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获得游戏介绍新闻
     * @param json
     * @return
     * @throws Exception
     */
    public static LinkedList<HashMap<String,Object>> getListFromJson(String json) throws Exception{
        if(json!=null){

            LinkedList<HashMap<String,Object>> data=new LinkedList<>();
            JSONObject object=new JSONObject(json);
            JSONObject object1=object.getJSONObject("data");
            for(int i=0;i<20;i++){
                HashMap<String,Object> map=new HashMap<>();
                JSONObject jsonObject=object1.getJSONObject(i+"");
                String id=jsonObject.getString("id");
                String typeid=jsonObject.getString("typeid");
                String title=jsonObject.getString("title");
                String litpic="http://www.3dmgame.com"+jsonObject.getString("litpic");
                String pubdate=jsonObject.getString("pubdate");
                String arcurl=jsonObject.getString("arcurl");
                map.put("id",id);
                map.put("typeid",typeid);
                map.put("title",title);
                map.put("litpic",litpic);
                map.put("pubdate",pubdate);
                map.put("arcurl",arcurl);
                data.add(map);
            }
            return data;
        }
        return null;
    }


    //解析游戏详情数据
    public static List<Game> getGamesFromJson(String json) throws  Exception{
        if(json!=null){
            List<Game> data=new ArrayList<>();
            JSONObject object=new JSONObject(json);
            String title=object.getString("title");
            String litpic="http://www.3dmgame.com"+object.getString("litpic");
            String pubdate=object.getString("pubdate");
            String description=object.getString("description");
            String game_bbs=object.getString("game_bbs");
            String websit=object.getString("websit");
            String release_company=object.getString("release_company");
            String made_company=object.getString("made_company");
            String terrace=object.getString("terrace");
            String language=object.getString("language");
            String release_date=object.getString("release_date");
            String tid=object.getString("tid");
            String arcurl=object.getString("arcurl");
            Game game=new Game(title,litpic,pubdate,description,game_bbs,websit,release_company,
                    made_company,terrace,language,release_date,tid,arcurl);
            data.add(game);
            return data;
        }
        return null;
    }
    //解析游戏详情数据2
    public static List<HashMap<String,Object>> getGamesThemeFromJson(String json) throws Exception{
        if(json!=null){
            List<HashMap<String,Object>> data=new ArrayList<>();
            JSONObject object=new JSONObject(json);
            JSONObject object1=object.getJSONObject("gl");
            for (int i=0;i<4;i++){
                JSONObject object2=object1.getJSONObject(i+"");
                String title=object2.getString("title");
                String writer=object2.getString("writer");
                String description=object2.getString("description");
                String arcurl=object2.getString("arcurl");
                HashMap<String,Object> map=new HashMap<>();
                map.put("title",title);
                map.put("writer",writer);
                map.put("description",description);
                map.put("arcurl",arcurl);
                data.add(map);
            }
            return data;
        }
        return null;
    }
}
