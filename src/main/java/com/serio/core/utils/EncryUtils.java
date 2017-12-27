package com.serio.core.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryUtils {
  /**
   * 加密方式 DES/CBC/PKCS5Padding
   */
  private static final String DES = "DES";
  
  /**
   * 密钥 ENCRY_KEY 最少为8个字节 ，是DES算法的工作密钥
   */
  private static final String ENCRY_KEY = "hetaosoft";
  
  /**
   * 根据键值ENCRY_KEY  加密
   * @param data 需要加密的字符串
   * @return
   * @throws Exception
   */
  public static String desEncrypt(String data) throws Exception {
    if (data == null)
      return null;
    // 获取加密结果
    byte[] bt = getCipher(data.getBytes(),true);
    String strs = new BASE64Encoder().encode(bt);
    return strs;
  }
  
  /**
   * 根据键值 ENCRY_KEY  解密
   * @param data 需要解密的字符串
   * @return
   * @throws Exception
   */
  public static String desDecrypt(String data) throws Exception {
      if (data == null)
          return null;
      data = data.replace(" ", "+");
      BASE64Decoder decoder = new BASE64Decoder();
      byte[] buf = decoder.decodeBuffer(data);
      //获取解密结果
      byte[] bt = getCipher(buf,false);
      return new String(bt);
  }   
  
  /**
   * 获取Cipher对象  执行加密/解密
   * @param data 
   * @param type Boolean true-加密  ;false-解密
   * @return
   * @throws Exception
   */
  private static byte[] getCipher(byte[] data,boolean type) throws Exception{
    // 生成一个可信任的随机数源
    SecureRandom sr = new SecureRandom();
    // 从原始密钥数据 ENCRY_KEY 创建DESKeySpec对象
    DESKeySpec dks = new DESKeySpec(ENCRY_KEY.getBytes());

    // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey securekey = keyFactory.generateSecret(dks);
    // Cipher对象实际完成加密操作
    Cipher cipher = Cipher.getInstance(DES);
    //根据 type（加密还是解密） 用密钥初始化Cipher对象
    if(type){
      cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
    }else{
      cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
    }
      
    return cipher.doFinal(data);
  }

  
  
  /**
   * 加密工具类(不可逆)
   * 
   * @param str 需要加密的字符串
   * @param encryName 加密算法名称
   * @return 加密后的字符串
   */
  public static String encry(String str, String encryName) {
    if (str == null) {
      return null;
    }
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance(encryName);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte[] bytes = md.digest(str.getBytes());
    return toHexString(bytes);
  }

  // 将 字节数组 转为 16进制字符串
  private static String toHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      int by = bytes[i] & 0xff;
      // 如果是一位,前面补0
      if (by < 16) {
        sb.append("0");
      }
      sb.append(Integer.toHexString(by));
    }
    return sb.toString();
  }

  /**
   * 加密 工具类(可逆)
   * 
   * @param str 需要加密的 字符串
   * @return 加密后的字符串
   */
  public static String encry(String str) {
    if (str == null) {
      return null;
    }
    try {
      String retVal = new BASE64Encoder().encode(str.getBytes("UTF-8"));
      // 处理URL的特殊字符
      retVal = retVal.replace("+", "#");
      retVal = retVal.replace("/", "$");
      retVal = retVal.replace("=", "-");
      return retVal;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 解密 工具类(可逆)
   * 
   * @param str 需要解密的 字符串
   * @return 解密后的字符串
   */
  public static String decry(String str) {
    if (str == null) {
      return null;
    }
    try {
      // 处理URL的特殊字符
      str = str.replace("#", "+");
      str = str.replace("$", "/");
      str = str.replace("-", "=");
      String retVal = new String(new BASE64Decoder().decodeBuffer(str), "UTF-8");
      return retVal;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * 过滤特殊字符
   * @param str
   * @return
   */
  public static String escapeHtml(String str) {
    str=str.replace("&", "&amp;");
    str=str.replace("<", "&lt;");
    str=str.replace(">", "&gt;");
    return str;
  }
}
