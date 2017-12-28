package com.serio.core.utils.media;
//失败的话把文件移到失败文件目录
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serio.core.utils.ConfigFileParams;
import com.serio.core.utils.FileConfig;

public class ConvertAudio {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	String srcAudioFilePath = null;
	String dstAudioFilePath = null;
	private String ffmpeg = System.getProperty("os.name").startsWith("Windows")? "ffmpeg.exe":"ffmpeg";			// ffmpeg转码

	public ConvertAudio(){
	}
	public ConvertAudio(String srcAudioFilePath, String dstAudioFilePath) {
		InitParameters(srcAudioFilePath, dstAudioFilePath);
	}

	
	public void InitParameters(String srcAudioFilePath, String dstAudioFilePath) {
		this.srcAudioFilePath = srcAudioFilePath;
		this.dstAudioFilePath = dstAudioFilePath; 		
	}


	/**
	 * 转码
	 * @return 转码结果，右起第一个四位表示标清转码结果（0000表示失败；0001表示成功）
	 */
	public int convert() {
		logger.info(srcAudioFilePath + "开始转码...");
		int result = 0x00;		
		result = process();
		return result;
	}
	
	/**
	 * @return 转码结果，右起第一个四位表示标清转码结果（0000表示失败；0001表示成功）
	 */
	protected int process() {
		int result = 0x00;	// 转码结果
		boolean status = false;
		status = ffmpegConvertToMp3(srcAudioFilePath, dstAudioFilePath, ConfigFileParams.getAudioConvertBitRate(),ConfigFileParams.getAudioConvertFreq());
		if (status) {
			result = result | 0x01;	// 成功标志
		} else {	// 失败的话把文件移到失败文件目录
			result = result & 0x10;	// 失败标志
    	File file = new File(dstAudioFilePath);
    	if (file.exists())
    		file.delete();
		} 
		return result;
	}

	
	protected boolean ffmpegConvertToMp3(String srcVideoFile, String dstAudioFilePath, String bitRate, String freq) {
		long startTime = System.currentTimeMillis();
		List<String> commend = new ArrayList<String>();
		commend.add(FileConfig.getConvertToolsDir() + ffmpeg);
		commend.add("-i"); 
		commend.add(srcVideoFile); 

		commend.add("-ab");
		commend.add(bitRate);
		commend.add("-ar");	
		commend.add(freq);
				
		commend.add("-y"); 	// 覆盖输出文件
		commend.add(dstAudioFilePath);
		
		logger.info(srcVideoFile + " -> " + dstAudioFilePath + "转码命令: " + commend.toString());

		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			long endTime = System.currentTimeMillis();
			logger.info(srcVideoFile + " -> " + dstAudioFilePath + " 转码成功,耗时 : (ms)" + (endTime-startTime));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * @param p
	 * @return
	 */
	protected int doWaitFor(Process p) {
		int exitValue = -1; // returned to caller when p is finished
		try {
			InputStream in = p.getInputStream();
			InputStream err = p.getErrorStream();
			boolean finished = false; // Set to true when p is finished

			while (!finished) {
				try {
					while (in.available() > 0) {
						// Print the output of our system call
						Character c = new Character((char) in.read());
						System.out.print(c);
					}
					while (err.available() > 0) {
						// Print the output of our system call
						Character c = new Character((char) err.read());
						System.out.print(c);
					}

					// Ask the process for its exitValue. If the process
					// is not finished an IllegalThreadStateException
					// is thrown. If it is finished we fall through and
					// the variable finished is set to true.
					exitValue = p.exitValue();
					finished = true;

				} catch (IllegalThreadStateException e) {
					// Process is not finished yet;
					// Sleep a little to save on CPU cycles
					//e.printStackTrace();
					TimeUnit.MILLISECONDS.sleep(500L);
				}
			}
		} catch (Exception e) {
			// unexpected exception! print it out for debugging...
			e.printStackTrace();
		}

		// return completion status to caller
		return exitValue;
	}
	
}
