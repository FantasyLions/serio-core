package com.serio.core.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	protected static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	// 指定文件读写缓冲区大小
	public static final int BUFFER_SIZE = 16 * 1024;
	// 用于存放最后一次生成的唯一值
	private static long uniqueVale = 0;
	// 转码中的文件
	public static Set<String> transcodingFile = new HashSet<String>();
	
	/**
	 * 添加到转码文件列表
	 * @param fileName
	 */
	public synchronized static void putTranscodingFile(String fileName) {
		transcodingFile.add(fileName);
	}
	
	/**
	 * 从转码文件列表删除
	 * @param fileName
	 */
	public synchronized static void removeTranscodingFile(String fileName) {
		transcodingFile.remove(fileName);
	}
	
	/**
	 * 是否存在转码文件列表中
	 * @param fileName
	 * @return
	 */
	public static boolean contains(String fileName) {
		return transcodingFile.contains(fileName);
	}

	/**
	 * 自动生成文件名，文件名=文件唯一标识+扩展名
	 * @param extendName
	 * @return
	 */
	public static String getFileName(String extendName) {
		return getUniqueValue() + extendName;
	} 
	
	/**
	 * 获取唯一值
	 * @return
	 */
	public static synchronized long getUniqueValue() {
		long curTime = System.currentTimeMillis();
		if (curTime <= uniqueVale) {
			uniqueVale = uniqueVale + 1;
		} else {
			uniqueVale = curTime;
		}
		return uniqueVale;
	}
	
	/**
	 * 判断是否是文件
	 * @param path
	 * @return
	 */
	public static boolean isFile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}
	
	public static void download(HttpServletResponse response, String dirName, String fileName,String fileDownloadName) throws IOException{
		String path =dirName+fileName; // 获取的物理路径		
		File file = new File(path);
       /*		
        1.录制管理:文件不存在,已发布,则去recordVideoMiddleDir中找
		2.视频维护:文件不存在,可能来源是录制视频,则去recordVideoMiddleDir
		*/
		if (!file.exists()) { 
			path = FileConfig.getRecordVideoMiddleDir()+fileName;
			File file1 = new File(path);
			if(!file1.exists()){
			   response.getWriter().write("<script>alert('文件不存在!');window.close();</script>");
			return;
			}
		}
		
		response.reset();
		response.setContentType("application/x-download");
		//response.setContentType("application/octet-stream;");
		response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileDownloadName, "UTF-8"));
		java.io.OutputStream out = null;
		java.io.InputStream in = null;
		try {
			out = response.getOutputStream();
			in = new FileInputStream(path);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				out.write(b, 0, i);
			}
			out.flush();
		} catch (Exception e) {
			System.out.println("====Error!==");
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
		}
	}
	/**
	 * 文件拷贝：chen-11-28 补充 ，此为使用springmvc文件上传机制,可是直接使用MultipartFile的file.transferTo(distFile)方法
	 * @param src 源文件
	 * @param dist 目标文件
	 * @return copy结果：true-拷贝成功；false-拷贝失败
	 */
	public static boolean copy(MultipartFile src, File dist) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(src.getInputStream(), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dist), BUFFER_SIZE);
				/*byte [] buffer = new byte [BUFFER_SIZE];
				while (in.read(buffer) > 0 )  {
					out.write(buffer);
				}*/
				int buf = 0;
				while ((buf = in.read()) > -1 )  {
					out.write(buf);
				}
				out.flush();
				return true;
			} finally {
				if ( null != in)  {
					in.close();
					in = null;
				} 
				if ( null != out)  {
					out.close();
					out = null;
				} 
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * 文件复制，是复制本地文件到目标目录chen-11-28，另外apache的API中FileUtils.copy方法
	 * @param src 源文件
	 * @param dest 目标文件
	 * @return copy结果：true-拷贝成功；false-拷贝失败
	 */
	public static boolean copyFile(File src, File dest){
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	    FileOutputStream out = null;
		FileChannel channel = null;
		FileLock lock = null;
		try {
		    out = new FileOutputStream(dest);
			bis = new BufferedInputStream(new FileInputStream(src),BUFFER_SIZE);
			bos = new BufferedOutputStream(out,BUFFER_SIZE);
			channel = out.getChannel();
			lock = channel.lock();
			int buf = 0;
			while ((buf = bis.read()) > -1 )  {
				bos.write(buf);
			}
			bos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		} finally {
			if ( null != bis)  {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bis = null;
			} 
			if ( null != bos)  {
				try {
					lock.release();
					channel.close();
	                bos.close();
	                out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bos = null;
			} 
		
		}
	} 
	
	/**
	 * 文件移动，将源文件移动到目标文件
	 * @param src 源文件
	 * @param dest 目标文件
	 * @return 结果：true-成功；false-失败
	 */
	public static boolean moveFile(File src, File dest) {
		boolean res = true;
		if (!dest.exists()) 
			res = FileUtils.copyFile(src, dest);
		
		if (res)
			src.delete();
		
		logger.info("文件" + src.getAbsolutePath() + "剪切到" + dest.getAbsolutePath() + " 结果 " + res);
		
		return res;
	}
	
	/**
	 * 获取文件扩展名（英文小写）
	 * @param fileName 文件名
	 * @return .name(.png, .mp4)
	 */
	public static String getExtention(String fileName)  {
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {	// 存在扩展名
			String suffixName = fileName.substring(pos);
			if (StringUtils.isNotBlank(suffixName) ) {
				suffixName = suffixName.toLowerCase();	//转化为小写
			}
			return suffixName;
		}
		// 不存在扩展名，返回空字符串
		return "";
	}
	
	/**
     * 获取文件扩展名（英文小写）
     * @param fileName 文件名
     * @return format name (png, mp4..)
     */
    public static String getSuffix(String fileName)  {
        int pos = fileName.lastIndexOf(".");
        if (pos > -1) { // 存在扩展名
            String suffixName = fileName.substring(pos + 1);
            if (StringUtils.isNotBlank(suffixName) ) {
                suffixName = suffixName.toLowerCase();  //转化为小写
            }
            return suffixName;
        }
        // 不存在扩展名，返回空字符串
        return "";
    }
	
    /**
     * 获取文件的简单名称,不含路径
     * 
     * @param fileName 文件的整个路径(包括文件的名称和路径)
     * @return 文件的简单名称
     */
    public static String getSimpleName(String fileName) {
      fileName = fileName.replace("\\", "/");
      int index = fileName.lastIndexOf("/");
      if (index != -1) {
        return fileName.substring(index + 1);
      }
      return fileName;
    }
    
	/**
	 * 去除文件的扩展名
	 * @param fileName
	 * @return
	 */
	public static String getSimpleFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {	// 存在扩展名
			String simpleName = fileName.substring(0, fileName.lastIndexOf("."));
			return simpleName;
		}
		// 不存在扩展名，返回原文件名
		return fileName;
	}
	
	/**
	 * 将上传到临时目录的视频文件拷贝到transcodingSrcDir/AladdinAuto
	 * @param fileName
	 * @return 
	 */
	public static boolean copyMediaFile(String newFileName,Constants.FolderType folderType) {
		String uploadTmpDir = FileConfig.getTmpDir(folderType);// 上传的临时目录
	    File srcFile = new File(uploadTmpDir + newFileName);
		//String distFileName = FileConfig.getTranscodingSrcDir()+newFileName;//将源文件复制到transcodingSrcDir目录下
    	//copyFile(srcFile,new File(distFileName));
    	//logger.info("将文件 " + newFileName + " 上传至transcodingSrcDir");
    	String aladdinFileName = FileConfig.getAladdinAutoTranscodeDir()+newFileName;//将源文件复制到AladdinAuto目录下
    	copyFile(srcFile,new File(aladdinFileName));
    	logger.info("将文件 " + newFileName + " 上传至AladdinAuto");
		return true;
	}
	/**
	 * 文件上传，负责文件上传工作，
	 * 首先上传到临时目录，然后根据成功与否分类到成功目录和失败目录
	 * @param file
	 * @return 返回生成的文件名，上传失败则返回null
	 */
	public static String uploadFile(MultipartFile file, Constants.FolderType folderType) {
		//String uploadSuccessDir = FileConfig.getSuccessDir(folderType);
		return uploadFileWithMonth(file, folderType, null);
	}
	
	/**
	 * 文件上传
	 * @param file 文件对象
	 * @param folderType 文件类型
	 * @param uploadDate 上传日期
	 * @return 返回生成的文件名，上传失败则返回null
	 */
	public static String uploadFile(MultipartFile file, Constants.FolderType folderType, String uploadDate) {
	  return uploadFileWithMonth(file, folderType, uploadDate);
	}
	
	/**
	 * 文件上传，负责文件上传工作，
	 * 首先上传到临时目录，然后根据成功与否分类到成功目录和失败目录
	 * @param file
	 * @return 返回生成的文件名，上传失败则返回null
	 */
	public static String uploadFileWithMonth(MultipartFile file, Constants.FolderType folderType, String uploadDate) {
		String oriFileName = file.getOriginalFilename();	// 上传的文件名
		String uploadTmpDir = "";
		if(folderType.equals(Constants.FolderType.TRANSCODING)){ //只有 修改封面 folderType= transcoding
			uploadTmpDir = FileConfig.getTmpDir(Constants.FolderType.UPLOAD_IMAGE);	// 上传的临时目录
		}else{
			uploadTmpDir = FileConfig.getTmpDir(folderType);	// 上传的临时目录
		}
		// 新文件名
		String extendName = getExtention(oriFileName);
		String newFileName = getFileName(extendName);
		String uploadTmpFileName = uploadTmpDir + newFileName;
	    File distFile = new File(uploadTmpFileName);
	    logger.info("上传文件 " + oriFileName + " 重命名为 " + uploadTmpDir+newFileName);
	    // 如果目录不存在,创建目录
	    createFolder(distFile);
	    boolean success = copy(file, distFile);
	    String uploadSuccessFileName;
	    String uploadSuccessDir;
	    if (success) {
	        if(uploadDate != null){
	        	uploadSuccessDir = FileConfig.getSuccessDir(folderType)+uploadDate;	// 上传成功目录
	        	uploadSuccessFileName = FileConfig.getSuccessDir(folderType)+uploadDate+"/"+newFileName;
	        }else{
	        	uploadSuccessDir = FileConfig.getSuccessDir(folderType);	// 上传成功目录
	        	uploadSuccessFileName = FileConfig.getSuccessDir(folderType)+newFileName;
	        }
	        //TODO mkdir 无法创建多级目录 使用mkdirs
	        if(!new File(uploadSuccessDir).exists()) new File(uploadSuccessDir).mkdirs();
	    	copyFile(distFile,new File(uploadSuccessFileName));
	    	logger.info("将文件 " + newFileName + " 上传至VpAladdinAuto");
	    	return newFileName;
	    } else {
	    	logger.warn("上传文件失败 " + oriFileName + " 重命名为 " +uploadTmpDir+ newFileName);
	    	if (distFile.exists()) {
	    		String uploadFailureDir = FileConfig.getFailureDir(folderType);	// 上传失败目录
		    	String uploadFailureFileName = uploadFailureDir + newFileName;
		    	//distFile.renameTo(new File(uploadFailureFileName));
		    	copyFile(distFile,new File(uploadFailureFileName));
		    	logger.warn("文件上传失败 " + newFileName + " 移到目录 " + uploadFailureDir);
	    	}
	    }
		return null;
	}
	
    /**
     * 当文件对应的文件夹不存在时创建文件夹
     * 
     * @param file 文件
     */
    public static void createFolder(File file) {
      File folder = null;
      // 是文件夹
      if (file.isDirectory()) {
        folder = file;
      } else {
        // 获取文件所在目录
        folder = file.getParentFile();
      }
      // 文件夹不存在
      if (!folder.exists()) {
        folder.mkdir();
      }
    }

  /**
	 * 获取视频文件时长
	 * @param file
	 * @return 源文件时长
	 */
//	public static long getDuration(File file){
//	  long duration=0;
//	  if(file.exists()){
//	    Encoder encoder = new Encoder();
//        try {
//             MultimediaInfo m = encoder.getInfo(file);
//             duration = m.getDuration();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	  }
//	  return duration;
//	}
	
	/**
	 * 文件上传，负责文件上传工作，
	 * 首先上传到临时目录，然后根据成功与否分类到成功目录和失败目录
	 * @param file
	 * @return 返回生成的文件名，上传失败则返回null
	 */
//	public static String uploadImageFile(MultipartFile file) {
//		String oriFileName = file.getOriginalFilename();	// 上传的文件名
//		String uploadTmpDir =  getImageUploadTmpDir();	// 上传的临时目录
//		// 新文件名
//		String extendName = getExtention(oriFileName);
//		String newFileName = getFileName(extendName);
//		String uploadTmpFileName = uploadTmpDir + newFileName;
//	    File distFile = new File(uploadTmpFileName);
//	    logger.info("上传文件 " + oriFileName + " 重命名为 " + newFileName);
//	    boolean success = copy(file, distFile);
//
//	    if (success) {
//	    	String uploadSuccessDir = getImageUploadSuccessDir();	// 上传成功目录
//	    	String uploadSuccessFileName = uploadSuccessDir + newFileName;
//	    	//distFile.renameTo(new File(uploadSuccessFileName));
//	    	copyFile(distFile, new File(uploadSuccessFileName));
//		   
//	    	ConvertPic cPic = new ConvertPic(uploadSuccessFileName,"","");
//	    	cPic.convertToJPG();
//	    	String PicPath = FileUtils.getSimpleFileName(newFileName) + ".jpg";
//	    	logger.info("文件上传成功 " + newFileName + " 移到目录 " + uploadSuccessDir);
//	    	return PicPath;
//	    } else {
//	    	logger.warn("上传文件失败 " + oriFileName + " 重命名为 " + newFileName);
//	    	if (distFile.exists()) {
//	    		String uploadFailureDir = getImageUploadFailureDir();	// 上传失败目录
//		    	String uploadFailureFileName = uploadFailureDir + newFileName;
//		    	//distFile.renameTo(new File(uploadFailureFileName));
//		    	copyFile(distFile, new File(uploadFailureFileName));
//		    	logger.warn("上传文件失败 " + newFileName + "移到目录" + uploadFailureDir);
//	    	}
//	    }
//	   
//		return null;
//	}
//	
//	/**
//	 * 文件上传，负责文件上传工作，
//	 * 首先上传到临时目录，然后根据成功与否分类到成功目录和失败目录
//	 * @param file
//	 * @return 返回生成的文件名，上传失败则返回null
//	 */
//	public static String uploadDownFile(MultipartFile file){
//		logger.info("上传文件开始...");
//		/* 页面控件的文件流中获取上传文件的原始文件名*/
//		String oriFileName = file.getOriginalFilename();
//		/* 上传的临时目录 */
//		String uploadTmpDir = getfileUploadTmpDir();
//		
//		/* 获取原文件的扩展名 */
//		String extendName = getExtention(oriFileName);
//		/* 获取新文件名 */
//		String newFileName = getFileName(extendName);
//		/* 获取新文件的上传后的目标文件 */
//		String uploadTmpFileName = uploadTmpDir + newFileName;
//		File destFile = new File(uploadTmpFileName);
//		/* 将网络流中原文件复制为目标文件 */
//		boolean success = copy(file, destFile);
//		if(success){
//			logger.info("文件上传成功，文件为" + newFileName);
//			String uploadSuccessDir = getfileUploadSuccessDir();
//			String uploadSuccessFileName = uploadSuccessDir + newFileName;
//			//destFile.renameTo(new File(uploadSuccessFileName));
//			copyFile(destFile, new File(uploadSuccessFileName));
//			logger.info("文件" + newFileName + "移动目录" + uploadSuccessDir);
//			return newFileName;
//		}else {
//			logger.info("文件上传失败，文件为" + newFileName);
//			if(destFile.exists()){
//				String uploadFailureDir  =  getfileUploadFailureDir();
//			    String uploadFailureFileName = uploadFailureDir + newFileName;
//			    //destFile.renameTo(new File(uploadFailureFileName));
//			    copyFile(destFile, new File(uploadFailureFileName));
//			    logger.info("文件" + newFileName + "移到目录" + uploadFailureDir);
//			}else {
//				logger.info("文件" + newFileName + "不存在！");
//			}
//		}
//		
//		return null;
//		
//		
//	}
	
	/**
	 * 删除文件或目录，目录包括子目录
	 * @param filePath
	 * @param isDelDir 是否删除根目录
	 */
	public static boolean delete(String filePath, boolean isDelDir) {
		File file = new File(filePath);
		boolean success = true;
		if (file.exists()) {
			if (file.isDirectory()) {	// 目录
				File[] files = file.listFiles();
				File childFile = null;
				for (int i = 1; i <= files.length; i++) {
					childFile = files[i-1];
					if (childFile.isDirectory()) {	// 如果是目录
						success = success && delete(childFile.getPath(),	true);
					} else {	// 如果是文件
						success = success && childFile.delete();
					}
				}
				if (isDelDir) {
					success = success &&  file.delete();
				} 
			} else {	// 文件
				success = success &&  file.delete();
			}
		}
		return success;
	}
	/**
     * 验证字符串
     * @param type:0 验证输入的type是否属于当前分类（1新闻 2专辑 4栏目等等；）
     *             1 验证输入的是否为给定日期格式
     *             2 验证字符串中是否含有危险字符
     * @param check:验证规则
     */
    public static boolean checkStr(String[] input,String type,String[] check){
        if(check == null || type == null || input == null) return false;
        if(type == "0")
            return checkInt(input[0],check);
        if(type == "1")
            return checkDate(input, check);
        if(type == "2")
            return checkSensitive(input,check);
        return true;
    }
    /**验证字符串是否为给定范围内的整数*/
    private static boolean checkInt(String input,String[] check){
        Integer inputInt = null;
        try {
            inputInt = Integer.parseInt(input);//将字符串转为整形，如果失败则返回false
        } catch (NumberFormatException e) {
            return false;
        }
        for(String c:check){
            if(inputInt == Integer.parseInt(c)) return true;//判断输入的值是否属于要查询的范围
        }
        return false;
    }
    /**验证字符串是否为给定类型的日期*/
    private static boolean checkDate(String[] input,String[] check){
        for(String in:input){
            if(StringUtils.isNotBlank(in)){//若in为空，表示该查询条件没有值，满足条件
                for(String c:check){
                    try {
                        new SimpleDateFormat(c).parse(in);//判断输入是否为给定格式的日期类型
                    } catch (ParseException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /**验证字符串是否包含给定的危险字符*/
    private static boolean checkSensitive(String[] input,String[] check){
        for(String in:input){
            if(StringUtils.isNotBlank(in)){
                for(String c:check){
                    if(in.toLowerCase().contains(c.toLowerCase()))
                        return false;//判断输入中是否含有危险字符
                }
            }
        }
        return true;
    }
    
    /**
     * 将文件 按照UTF-8写入文件中
     * 
     * @param fileName 文件名称
     * @param data 需要写入的数据
     */
    public static void witeToFile(String fileName, String data) {
      File file = new File(fileName);
      try {
        org.apache.commons.io.FileUtils.writeStringToFile(file, data, "UTF-8");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    /**
     * 将文件按UTF-8编码读入字符串
     * 
     * @param fileName 需要 读取的文件名称
     * @return 文件内容的字符串形式
     */
    public static String readFile(String fileName) {
      File file = new File(fileName);
      try {
        return org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
    
    /*
     * get user home path. C:\Users\Admin on Windows
     * @param path: subPath under home path
     */
    public static String getHomepath(String path){
      String wspath = System.getProperty("user.home");
      if (System.getProperty("os.name").contains("Windows")) {
        wspath = wspath.replace('\\', '/');
      }
      
      if (path != null && path.length() > 0)
          wspath += "/" + path;
      return wspath;
    }
}
