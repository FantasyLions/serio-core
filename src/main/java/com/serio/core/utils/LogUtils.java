package com.serio.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志 工具类,可以在任何地方使用日志
 * 
 * @author rsncsoft
 * 
 */
public class LogUtils {
  // 传入对象记录日志
  public static void debug(String message, Object obj) {
    Logger logger = LoggerFactory.getLogger(obj.getClass());
    logger.debug(message);
  }

  public static void info(String message, Object obj) {
    Logger logger = LoggerFactory.getLogger(obj.getClass());
    logger.info(message);
  }

  public static void warn(String message, Object obj) {
    Logger logger = LoggerFactory.getLogger(obj.getClass());
    logger.warn(message);
  }

  public static void error(String message, Object obj) {
    Logger logger = LoggerFactory.getLogger(obj.getClass());
    logger.error(message);
  }

  public static void trace(String message, Object obj) {
    Logger logger = LoggerFactory.getLogger(obj.getClass());
    logger.trace(message);
  }

  // 传入类 记录日志
  public static void debug(String message, Class clazz) {
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.debug(message);
  }

  public static void info(String message, Class clazz) {
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.info(message);
  }

  public static void warn(String message, Class clazz) {
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.warn(message);
  }

  public static void error(String message, Class clazz) {
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.error(message);
  }

  public static void trace(String message, Class clazz) {
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.trace(message);
  }
}
