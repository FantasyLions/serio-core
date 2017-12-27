package com.serio.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class JsonUtils {
  /**
   * 对json排序
   * 
   * @param json 需要排序的json
   * @param key 排序的字段名称
   * @return 排好序的json
   */
  public static String sortJson(String json, final String key) {
    JSONArray array = JSONArray.fromObject(json);
    Object[] objs = array.toArray();
    // 按照 给定的排序器排序
    Arrays.sort(objs, new Comparator<Object>() {
      @Override
      public int compare(Object o1, Object o2) {
        return JsonUtils.castJson2Num(o1, key) - castJson2Num(o2, key);
      }
    });
    // ba 单个json用逗号拼起来
    return "[" + StringUtils.join(objs, ",") + "]";
  }

  private static int castJson2Num(Object json, String key) {
    JSONObject jsonObj = JSONObject.fromObject(json);
    String time = jsonObj.getString(key);
    // 将 time转为整型,便于比较
    return (int) (Double.parseDouble(time) * 10000);
  }
  
  public static String readJson(String path){
    //从给定位置获取文件
    File file = new File(path);
    BufferedReader reader = null;
    //返回值,使用StringBuffer
    StringBuffer data = new StringBuffer();
    //
    try {
        reader = new BufferedReader(new FileReader(file));
        //每次读取文件的缓存
        String temp = null;
        while((temp = reader.readLine()) != null){
            data.append(temp);
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        //关闭文件流
        if (reader != null){
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return data.toString();
}
}
