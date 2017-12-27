package com.serio.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.ObjectMetadata;

public class AliUtils {
  protected static Logger logger = LoggerFactory.getLogger("Aliyun");
  /**
   * 将单个文件上传至阿里云
   * 
   * @param file 需要上传的文件
   * @param bucketName 是一个路径,一般是用户的bucketName
   * @param path 文件的子路径(包括文件名)
   */
  public static void upload(OSSClient client, File file, String objectName, String bucketName,
      String path) {
    if (objectName != null)
      path = objectName + "/" + path;
    
    UploadSingleFile(client, bucketName, file, path);
  }
  
  /**
   * 上传单个文件
   * @param bucketName
   * @param filePath: local absolute file path with suffix
   * @param aliPath: upload directory
   */
  public static boolean UploadSingleFile(OSSClient client, String bucketName, String filePath, String aliPath) {
    // 需要上传的文件对象封装,包括输入流和文件长度
    File file = new File(filePath);
    return UploadSingleFile(client, bucketName, file, aliPath);
  }
  
  
  /**
   * 上传单个文件
   * @param bucketName
   * @param file: local absolute file
   * @param aliPath: upload directory
   */
  public static boolean UploadSingleFile(OSSClient client, String bucketName, File file, String aliPath) {
    // 需要上传的文件对象封装,包括输入流和文件长度
    boolean bUpload = false;
    if (client == null)
      client = new OSSClient(Constants.OSS_ENDPOINT,Constants.ACCESS_ID, Constants.ACCESS_KEY);
    ObjectMetadata objectMeta = new ObjectMetadata();
    objectMeta.setContentLength(file.length());
    try {
      InputStream input = new FileInputStream(file);
      bUpload = true;
      // 执行文件上传
      logger.info("\tbucketName:{} and path:{}", bucketName, aliPath);
      client.putObject(bucketName, aliPath, input, objectMeta);
    } catch (FileNotFoundException e) {
      logger.warn("\tupload file {} error, message:{}", file.getAbsoluteFile(), e.getMessage());
    }
          
    return bUpload;
  }
  
  
  /**
   * 下载单个文件
   * @param bucketName
   * @param aliFilePath: full path in aliyun
   * @param localFileDir: local file directory
   * @param fileName: local file name
   */
  public static boolean DownloadWithDir(OSSClient client, String bucketName, String aliFilePath, String localFileDir, String fileName) {
    File localFile = new File(localFileDir + "/" + fileName + ".mp4");
    return DownloadWithFile(client, bucketName, aliFilePath, localFile);
  }
  
  /**
   * 下载单个文件
   * @param bucketName
   * @param aliFilePath: full path in aliyun
   * @param localFile: local file
   */
  public static boolean DownloadWithFile(OSSClient client, String bucketName, String aliFilePath, File localFile) {
    boolean bDownload = false;
    try {
      if (!localFile.isFile()) {
        // download file and update status
        File file = new File(localFile.getParent());
        if(!file.exists()) file.mkdirs();
        
        if (client == null)
          client = new OSSClient(Constants.OSS_ENDPOINT,Constants.ACCESS_ID, Constants.ACCESS_KEY);
        client.getObject(new GetObjectRequest(bucketName, aliFilePath), localFile);
      }
      bDownload = true;
    } catch (ClientException e) {
      logger.warn("ClientException: can't download {}", aliFilePath);
    } catch (OSSException e) {
      logger.warn("OSSException: can't download {}", aliFilePath);
    }
    
    return bDownload;  
  }
  
  /**
   * 下载单个文件
   * @param bucketName
   * @param aliFilePath: full path in aliyun
   * @param localFilePath: local file path
   */
  public static boolean DownloadWithPath(OSSClient client, String bucketName, String aliFilePath, String localFilePath) {
    File localFile = new File(localFilePath);
    return DownloadWithFile(client, bucketName, aliFilePath, localFile);
  }
}
