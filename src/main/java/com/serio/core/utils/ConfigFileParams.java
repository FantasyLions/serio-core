package com.serio.core.utils;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigFileParams {
  protected static Logger logger = LoggerFactory.getLogger(ConfigFileParams.class);
  // 资源束
  private static ResourceBundle configFileParamBundle = ResourceBundle
      .getBundle("configFileParams");
  // private static ResourceBundle pictureParams = ResourceBundle.getBundle("pictureParams");
  private static ResourceBundle pictureParams = ResourceBundle.getBundle("configFileParams");

  /**
   * 根据key获取资源文件值
   * 
   * @param key
   * @return
   */
  public static String getResourceBundle(String key) {
    String bundleVale = configFileParamBundle.getString(key);
    if (StringUtils.isBlank(bundleVale)) { // 为空时重新加载资源文件
      configFileParamBundle = ResourceBundle.getBundle("configFileParams");
      bundleVale = configFileParamBundle.getString(key);
    }
    return bundleVale;
  }

  public static String getPictureParams(String key) {
    String bundleVale = pictureParams.getString(key);
    if (StringUtils.isBlank(bundleVale)) {
      pictureParams = ResourceBundle.getBundle("pictureParams");
      bundleVale = pictureParams.getString(key);
    }
    return bundleVale;
  }

  /**
   * 获取 server发送邮箱地址
   * 
   * @return
   */
  public static String getEmailFrom() {
    return getResourceBundle("emailfrom");
  }

  /**
   * 获取 server 邮箱用户名
   * 
   * @return
   */
  public static String getEmailUsername() {
    return getResourceBundle("emailusername");
  }

  /**
   * 获取 server 邮箱密码
   * 
   * @return
   */
  public static String getEmailPassword() {
    return getResourceBundle("emailpassword");
  }

  /**
   * 获取 server 邮箱SMTP服务器
   * 
   * @return
   */
  public static String getEmailHost() {
    return getResourceBundle("emailhost");
  }

  /**
   * vptIP
   */
  public static String getVptIP() {
    return getResourceBundle("vptIP");
  }
  /**
   * bucketName
   */
  public static String getBucketName() {
    return getResourceBundle("bucketName");
  }

  /**
   * uploadType
   */
  public static String getUploadType() {
    return getResourceBundle("uploadType");
  }

  /**
   * vpmtIP
   */
  public static String getVpmtIP() {
    return getResourceBundle("vpmtIP");
  }

  /**
   * audio convert BitRate
   * 
   * @return
   */
  public static String getAudioConvertBitRate() {
    return getResourceBundle("auBitRate");
  }

  /**
   * audio convert Freq
   * 
   * @return
   */
  public static String getAudioConvertFreq() {
    return getResourceBundle("auFreq");
  }

  /**
   * 标清码率
   * 
   * @return
   */
  public static String getSdBitRate() {
    return getResourceBundle("sdBitRate");
  }

  /**
   * 标清视频宽度
   * 
   * @return
   */
  public static String getSdWidth() {
    return getResourceBundle("sdWidth");
  }

  /**
   * 标清视频高度
   * 
   * @return
   */
  public static String getSdHeight() {
    return getResourceBundle("sdHeight");
  }

  /**
   * 高清码率
   * 
   * @return
   */
  public static String getHdBitRate() {
    return getResourceBundle("hdBitRate");
  }

  /**
   * 高清视频宽度
   * 
   * @return
   */
  public static String getHdWidth() {
    return getResourceBundle("hdWidth");
  }

  /**
   * 高清视频高度
   * 
   * @return
   */
  public static String getHdHeight() {
    return getResourceBundle("hdHeight");
  }

  /**
   * 获取直播流的地址
   * 
   * @return
   */
  public static String getLiveRtmp() {
    return getResourceBundle("live_room_stream");

  }

  /**
   * 获取视频直播的地址
   * 
   * @return
   */
  public static String getLiveVideo() {
    return getResourceBundle("live_room_file_video");

  }

  /**
   * 获取手机的直播地址
   * 
   * @return
   */
  public static String getLiveMobile() {
    return getResourceBundle("live_room_file_mobile");

  }

  /**
   * 获取TV直播地址
   * 
   * @return
   */
  public static String getLiveTV() {
    return getResourceBundle("live_room_file_tv");
  }

  /**
   * 是否进行直播评论
   * 
   * @return
   */
  public static String getLiveComment() {
    return getResourceBundle("liveComment");
  }

  /**
   * 是否禁止评论
   * 
   * @return
   */
  public static String getProhibitComment() {
    return getResourceBundle("prohibitComment");
  }


  /**
   * 图片高度高度 isFrontImg 首页预览图片
   * 
   * @return
   */
  public static String getPicHeight(String type) {
    if (type.equals(Constants.NORMAL_BIG_PIC))
      return getPictureParams("normalBigPicHeight");
    else if (type.equals(Constants.SPECIAL_BIG_PIC))
      return getPictureParams("specialBigPicHeight");
    else if (type.equals(Constants.NORMAL_PIC))
      return getPictureParams("normalPicSmallHeight");
    else if (type.equals(Constants.LOGO_PIC))
      return getPictureParams("logoPicHeight");
    else
      return getPictureParams("mediaCoverHeight");
  }

  /**
   * 小图宽度
   * 
   * @return
   */
  public static String getPicWidth(String type) {
    if (type.equals(Constants.NORMAL_BIG_PIC))
      return getPictureParams("normalBigPicWidth");
    else if (type.equals(Constants.SPECIAL_BIG_PIC))
      return getPictureParams("specialBigPicWidth");
    else if (type.equals(Constants.NORMAL_PIC))
      return getPictureParams("normalPicSmallWidth");
    else if (type.equals(Constants.LOGO_PIC))
      return getPictureParams("logoPicWidth");
    else
      return getPictureParams("mediaCoverWidth");
  }

  /**
   * 大图高度
   * 
   * @return
   */
  public static String getBHeight() {
    return getResourceBundle("bHeight");
  }

  /**
   * 大图宽度
   * 
   * @return
   */
  public static String getBWidth() {
    return getResourceBundle("bWidth");
  }

  /**
   * 上传的图片后缀
   * 
   * @return
   */
  public static String getImgSuffix() {
    return getResourceBundle("imgSuffix");
  }

  /**
   * 上传的视频后缀
   * 
   * @return
   */
  public static String getMediaSuffix() {
    return getResourceBundle("mediaSuffix");
  }

  /**
   * 是否为测试模式
   * 
   * @return
   */
  public static String getIsTestModel() {
    return getResourceBundle("isTestModel");
  }

  public static boolean isLocalDeploy() {
    if (StringUtils.isBlank(getDeployMode()) || getDeployMode().equalsIgnoreCase("local"))
      return true;
    else
      return false;
  }

  public static boolean isALIDeploy() {
    return "aliyun".equalsIgnoreCase(getDeployMode());
  }
  /**
   * deploy mode
   * 
   * @return deploy mode
   */
  public static String getDeployMode() {
    return getResourceBundle("deployMode");
  }
}
