package com.huaxing.a3dgame.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaxing.a3dgame.R;
import com.huaxing.a3dgame.utils.CacheManagerUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 */
public class PullToRe_ListView_Adapter extends BaseAdapter {
    private LinkedList<HashMap<String,Object>> data;
    private Context context;

    public PullToRe_ListView_Adapter(LinkedList<HashMap<String,Object>> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.pulltorefresh_listview_item,null);
            holder=new ViewHolder();
            holder.imageView=(ImageView) convertView.findViewById(R.id.pulltorefresh_listview_item_imageview);
            holder.tv_title=(TextView)convertView.findViewById(R.id.pulltorefresh_listview_item_tv1);
            holder.tv_date=(TextView)convertView.findViewById(R.id.pulltorefresh_listview_item_tv2);
            holder.tv_click=(TextView)convertView.findViewById(R.id.pulltorefresh_listview_item_tv3);

            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        //填充数据
        holder.tv_title.setText(data.get(position).get("title").toString());
        //格式化时间
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long date=Long.parseLong(data.get(position).get("senddate").toString());
        Date formatDate=new Date(date);
        holder.tv_date.setText(format.format(formatDate));

        holder.tv_click.setText(data.get(position).get("click").toString());
        //设置图片
        String imagePath=data.get(position).get("litpic").toString();
        Log.i("bbb",imagePath);
        if(imagePath!=null&&imagePath.contains(".jpg")) {
            //获得压缩图片不同的比例
            GridViewAdapter.isGame=false;
            holder.imageView.setTag(imagePath);
          new CacheManagerUtils().getBitmapToCache(imagePath,holder.imageView);
        }



        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView tv_title,tv_date,tv_click;
    }
}
