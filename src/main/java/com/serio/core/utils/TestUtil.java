package com.serio.core.utils;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUtil {

  public static <T> T find(Class<T> clazz) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    String className = clazz.getSimpleName();
    String clazzName = className.substring(0, 1).toLowerCase() + className.substring(1);
    return (T) ctx.getBean(clazzName);
  }

  /**
   * 将字符串追加输出文件
   */
  public static void write(String fileName, String content) {
    try {
      FileWriter writer = new FileWriter(fileName, true);
      writer.write(content + "\r\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
