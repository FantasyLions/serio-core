package com.serio.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

  /**
   * 获取配置文件中指定键的值
   * 
   * @param fileName 配置文件全路径名,要求为*.properties文件
   * @param key 键的名称
   * @return 该键对应的值
   */
  public static String getConfig(String fileName, String key) {
    Properties pro = new Properties();
    FileInputStream inStream = null;
    try {
      inStream = new FileInputStream(new File(fileName));
      pro.load(inStream);
      String value = pro.getProperty(key);
      return new String(value.getBytes("ISO8859-1"),"UTF-8");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inStream != null) {
        try {
          inStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
  /**
   * 批量获取配置文件中指定键的值
   * 
   * @param fileName 配置文件全路径名,要求为*.properties文件
   * @param key 键的名称
   * @return 该键对应的值
   */
  public static Map<String,String> getConfigs(String fileName, List<String>keys) {
    Properties pro = new Properties();
    FileInputStream inStream = null;
    Map<String,String>map=new HashMap<String,String>();
    try {
      inStream = new FileInputStream(new File(fileName));
      pro.load(inStream);
      for (String key : keys) {
        map.put(key, new String(pro.getProperty(key).getBytes("ISO8859-1"),"UTF-8"));
      }
      return map;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inStream != null) {
        try {
          inStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
  /**
   * 根据类找到配置文件,并获取值
   * @param fileName 配置文件文件名
   * @param key 主键
   * @param configClass 当前类
   * @return 主键对应的值
   */
  public static String getConfig(String fileName, String key, Class configClass) {
    String configFileName = configClass.getClassLoader().getResource(fileName).getPath().toString();
    return getConfig(configFileName, key);
  }
  /**
   * 根据类找到配置文件,并批量获取值
   * @param fileName 配置文件文件名
   * @param key 主键
   * @param configClass 当前类
   * @return 主键对应的值
   */
  public static Map<String, String> getConfigs(String fileName, List<String>keys, Class configClass) {
    String configFileName = configClass.getClassLoader().getResource(fileName).getPath().toString();
    return getConfigs(configFileName, keys);
  }
  /**
   * 根据对象找到配置文件,并获取值
   * @param fileName 配置文件文件名
   * @param key 主键
   * @param configObj 当前类
   * @return 主键对应的值
   */
  public static String getConfig(String fileName, String key, Object configObj) {
    Class configClass = configObj.getClass();
    return getConfig(fileName, key, configClass);
  }
  
  /**
   * 获取配置文件中指定键的值,主要用于 功能开关
   * 
   * @param fileName 配置文件全路径名,要求为*.properties文件
   * @param key 键的名称
   * @return 该键对应的值
   */
  public static boolean getConfigForBoolean(String fileName, String key) {
    String value=getConfig(fileName,key);
    return "true".equals(value)||"show".equals(value)||value==null;
  }
  /**
   * 根据类和配置文件简单名称获取配置文件中指定键的值,主要用于 功能开关
   * 
   * @param fileName 配置文件简单名称
   * @param key 配置文件的键
   * @param configClass 当前对象的类
   * @return 该键对应的值
   */
  public static boolean getConfigForBoolean(String fileName, String key, Class configClass) {
    String configFileName = configClass.getClassLoader().getResource(fileName).getPath().toString();
    return getConfigForBoolean(configFileName, key);
  }

  /**
   * 根据对象和配置文件简单名称获取配置文件中指定键的值,主要用于 功能开关
   * 
   * @param fileName 配置文件简单名称
   * @param key 配置文件的键
   * @param configObj 当前对象
   * @return 该键对应的值
   * @see 用法参见 com.ctn.vp.programschedule.controller.ProgramScheduleController1的toLivingRoom方法
   */
  public static boolean getConfigForBoolean(String fileName, String key, Object configObj) {
    Class configClass = configObj.getClass();
    return getConfigForBoolean(fileName, key, configClass);
  }

}
