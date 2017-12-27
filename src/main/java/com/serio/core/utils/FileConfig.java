package com.serio.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileConfig {
	protected static Logger logger = LoggerFactory.getLogger(FileConfig.class);
	public static final String configFileName="fileDirectories.properties";
	public static final String configParamName="configFileParams.properties";

	/**
	 * 2013.04.17新添加
	 * 数据同步文件列表
	 * @param fileName
	 */
	public static String getfileSync(){
	  return PropertiesUtils.getConfig(configFileName, "fileSync",FileConfig.class);
	}
	
	/**
	 * 附件/图片/视频上传的临时目录
	 * @return 目录文件
	 */
	public static String getTmpDir(Constants.FolderType folderType) {
		return PropertiesUtils.getConfig(configFileName, folderType+"TmpDir",FileConfig.class);
	}
	
	/** 
	 * 附件/图片/视频上传成功存放的目录
	 * @return 目录文件
	 */
	public static String getSuccessDir(Constants.FolderType folderType) {
		return PropertiesUtils.getConfig(configFileName, folderType + "SuccessDir",FileConfig.class);
	}
	
	/**
	 * 附件/图片/视频上传失败存放的目录
	 * @return 目录文件
	 */
	public static String getFailureDir(Constants.FolderType folderType) {
		return PropertiesUtils.getConfig(configFileName,folderType + "FailureDir",FileConfig.class);
	}
	
	/**
	 * 已转码的源文件的目录
	 * @return
	 */
	public static String getTranscodingSrcDir() {
		return PropertiesUtils.getConfig(configFileName,"transcodingSrcDir",FileConfig.class);
	}
	
	/**
	 * Aladdin自动转码目录
	 * @return
	 */
	public static String getAladdinAutoTranscodeDir() {
		return PropertiesUtils.getConfig(configFileName,"aladdinAutoTranscodeDir",FileConfig.class);
	}
	
	/**
	 * 转码成功存放的目录
	 * @return
	 */
	public static String getTranscodingSuccessDir() {
		return PropertiesUtils.getConfig(configFileName,"transcodingSuccessDir",FileConfig.class);
	}
	
	/**
	 * 转码失败存放的目录
	 * @return
	 */
	public static String getTranscodingFailureDir() {
		return PropertiesUtils.getConfig(configFileName,"transcodingFailureDir",FileConfig.class);
	}
	
	/**
	 * 转码工具目录
	 * convertToolsDir
	 * @return
	 */
	public static String getConvertToolsDir() {
		return PropertiesUtils.getConfig(configFileName,"convertToolsDir",FileConfig.class);
	}
	
	/**
	 * 关闭进程工具目录
	 * closeToolsDir
	 * @return
	 */
	public static String getCloseToolsDir() {
		return PropertiesUtils.getConfig(configFileName, "closeToolsDir",FileConfig.class);
	}
	
	/**
	 * 录制的文件目录
	 * recordToolsDir
	 * @return
	 */
	public static String getRecordVideoDir() {
		return PropertiesUtils.getConfig(configFileName,"recordVideoDir",FileConfig.class);
	}
	
	/***
	 * 录制存放源文件目录 recordVideoMiddleDir
	 * @return
	 */
	public static String getRecordVideoMiddleDir() {
		return PropertiesUtils.getConfig(configFileName, "recordVideoMiddleDir",FileConfig.class);
	}
	
	/**
	 * 媒资存放目录
	 * convertToolsDir
	 * @return
	 */
	public static String getMediaDataDir() {
		return PropertiesUtils.getConfig(configFileName, "mediaDataDir",FileConfig.class);
	}
	
	/**
	 * 媒资导入中间过渡目录
	 * convertToolsDir
	 * @return
	 */
	public static String getMediaDataMiddleDir() {
		return PropertiesUtils.getConfig(configFileName, "mediaDataMiddleDir",FileConfig.class);
	}
	
	/**
	 * 富文本框上传的文件
	 * @return
	 */
	public static String getCkeditorUploadDir() {
		return PropertiesUtils.getConfig(configFileName,"ckeditorUploadDir",FileConfig.class);
	}
	
	/**
	 * 转码时生成的过渡文件目录
	 * @return
	 */
	public static String getTransitionDir() {
		return PropertiesUtils.getConfig(configFileName, "transitionDir",FileConfig.class);
	}
	/**
	 * 转码完成时存放的位置--AladdinOutput
	 * @return
	 */
	public static String getAladdinOutputDir() {
		return PropertiesUtils.getConfig(configFileName, "aladdinOutput",FileConfig.class);
	}
	/**
	 * AladdinOutput下标清目录
	 * @return
	 */
	public static String getVpSdDir() {
		return PropertiesUtils.getConfig(configFileName,"vpSdDir",FileConfig.class);
	}
	/**
	 * AladdinOutput下高清目录
	 * @return
	 */
	public static String getVpHdDir() {
		return PropertiesUtils.getConfig(configFileName, "vpHdDir",FileConfig.class);
	}
	
	/**
	 * 音频目录
	 * @return
	 */
	public static String getAudioDir() {
	  return PropertiesUtils.getConfig(configFileName, "audioDir",FileConfig.class);
	}
	
	/**
     * 模板文件目录
     * @return
     */
    public static String getTemplateDir() {
      return PropertiesUtils.getConfig(configFileName, "templateDir",FileConfig.class);
    }
    
    /**
     * 与模板相关的json文件
     * @return
     */
    public static String getJobJsonDir() {
      return PropertiesUtils.getConfig(configFileName, "jobJsonDir",FileConfig.class);
    }
    
    
    /**
     * 转码工具目录
     * convertVideoDir
     * @return
     */
    public static String getConcatVideoDir() {
        return PropertiesUtils.getConfig(configFileName,"convertVideoDir",FileConfig.class);
    }
    
    /**
     * 是否为模板模式
     * @return 是否为模板模式
     */
    public static boolean isTemplateModel(){
      String isTemplateModel=PropertiesUtils.getConfig(configParamName, "isTemplateModel", FileConfig.class);
      return "true".equalsIgnoreCase(isTemplateModel);
    }
    
    /**
     * 获取默认的工程id
     * 
     * @return 默认的工程id
     */
    public static String getPublicJobId() {
      return PropertiesUtils.getConfig(configParamName, "publicJobId", FileConfig.class);
    }
    
    /**
     * 获取超级管理员id
     * 
     * @return 超级管理员id
     */
    public static String getAdminUserId() {
      return PropertiesUtils.getConfig(configParamName, "adminUserId", FileConfig.class);
    }
    
    /**
     * AE 原始json文件目录
     * 
     * @return AE 原始json文件目录
     */
    public static String getAEJsonDir() {
      return PropertiesUtils.getConfig(configFileName, "AEJsonDir", FileConfig.class);
    }
    
    /**
     * jenkins IP地址
     * 
     * @return jenkins IP地址
     */
    public static String getJenkinsIP() {
      return PropertiesUtils.getConfig(configParamName, "jenkensIP", FileConfig.class);
    }
    
    /**
     * ossEndpoint 阿里云域名
     * 
     * @return ossEndpoint 阿里云域名
     */
    public static String getOssEndpoint() {
      return PropertiesUtils.getConfig(configParamName, "ossEndpoint", FileConfig.class);
    }
    
    /**
     * 判断是否为微信模式
     * @return
     */
    public static boolean isWeixinModel() {
      return "true".equals( PropertiesUtils.getConfig(configParamName, "bWeixingModel", FileConfig.class) );
    }

    /**
     * ossEndpoint vpes ip
     * 
     * @return 
     */
    public static String getVpesIP() {
      return PropertiesUtils.getConfig(configParamName, "vpesIP", FileConfig.class);
    }
    
    /**
     * 微信模式下的bucketName
     * @return
     */
    public static String getWxBucketName() {
      return PropertiesUtils.getConfig(configParamName, "wxBucketName", FileConfig.class);
    }
    
    /**
     * 普通模式(自由模式)下的bucketName
     * @return
     */
    public static String getNormBucketName() {
      return PropertiesUtils.getConfig(configParamName, "bucketName", FileConfig.class);
    }
}
