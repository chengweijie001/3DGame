package com.huaxing.a3dgame.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 保存图片到SD卡 读取
 */
public class FileCacheUtils {
   //SD卡的根目录
    private static final  File SD_FILE=Environment.getExternalStorageDirectory();
    //缓存目录
    private String cache_folder="file_cache";
    //判断SD卡是否挂载
    boolean isMounted=false;
    //缓存目录对象
    private static File CACHE_FOLDER=null;

    public FileCacheUtils() {
        //实例此类的同时，判断SD卡是否存在，并创建缓存目录
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.i("aaa","SD卡没有挂载");
        }else {
            isMounted=true;
            //创建缓存目录
            CACHE_FOLDER=new File(SD_FILE,cache_folder);
            if(!CACHE_FOLDER.exists()){
                CACHE_FOLDER.mkdirs();
            }
        }
    }
    //保存文件到缓存目录
    public void saveFileToCache(String urlStr, byte[] data){
        FileOutputStream fileOutputStream=null;
        if(isMounted){
            if(CACHE_FOLDER.exists()){
                //开始保存文件
                String fileName=urlStr.substring(urlStr.lastIndexOf("/")+1);
                File file=new File(CACHE_FOLDER,fileName);
                //开始写文件
                try {
                    fileOutputStream=new FileOutputStream(file);
                    if(data!=null){
                        fileOutputStream.write(data,0,data.length);
                        Log.i("aaa","保存成功了");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (fileOutputStream!=null){
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    //获取文件
    public Bitmap getBitmapFromSDCard(String urlStr){
        if(urlStr!=null){
            // 获得文件名
            String fileName=urlStr.substring(urlStr.lastIndexOf("/")+1);
            File file=new File(CACHE_FOLDER,fileName);
            if(file.exists()){
                return BitmapFactory.decodeFile(file.getAbsolutePath());
            }
        }
        return  null;
    }
    //清空缓存
    public void clear(){
        if(isMounted){
            File[] files=CACHE_FOLDER.listFiles();
            for (File file:files){
                file.delete();
            }
        }
    }

//    //保存图片到SD卡
//    public static String saveImageFile(byte[] data, String fileName) {
//        //判断SD卡是否挂载
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            //获得跟目录
//            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            //创建文件
//            File file = new File(root, fileName);
//            //创建文件写入流
//            FileOutputStream fileOutputStream = null;
//            try {
//                fileOutputStream = new FileOutputStream(file);
//                fileOutputStream.write(data, 0, data.length);
//                return file.getAbsolutePath();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (fileOutputStream != null) {
//                    try {
//                        fileOutputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }
//
//        return null;
//    }
//
//    //读取文件
//    public static byte[] readImageFile(String path) {
//        File file = new File(path);
//        BufferedInputStream bufferedInputStream = null;
//        try {
//            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            byte[] buf = new byte[1024 * 4];
//            int len = 0;
//            while ((len = bufferedInputStream.read(buf)) != -1) {
//                byteArrayOutputStream.write(buf, 0, len);
//            }
//            return byteArrayOutputStream.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (bufferedInputStream != null) {
//                try {
//                    bufferedInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }
}
