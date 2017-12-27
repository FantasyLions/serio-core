package com.serio.core.utils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ProcessUtils {
	private static Logger logger = LoggerFactory.getLogger(ProcessUtils.class);
	
	/**
	 * 读取进程中文件流信息 
	 * @param p
	 * @return 
	 */
	public static  int doWaitFor(Process p) {
		int exitValue = -1; // returned to caller when p is finished
		StringBuilder serr = new StringBuilder();
		try {
			InputStream in = p.getInputStream();
			InputStream err = p.getErrorStream();
			boolean finished = false; // Set to true when p is finished

			while (!finished) {
				try {
					StringBuilder sin = new StringBuilder();
					while (in.available() > 0) {
						// Print the output of our system call
						Character c = new Character((char) in.read());
						sin.append(c);
					}
					//logger.info("读取输入流 sin信息.."+sin.toString());
					while (err.available() > 0) {
						// Print the output of our system call
						Character c = new Character((char) err.read());
						serr.append(c);
					}
					//logger.info("读取缓存错误信息.."+serr.toString());
					
					// Ask the process for its exitValue. If the process
					// is not finished an IllegalThreadStateException
					// is thrown. If it is finished we fall through and
					// the variable finished is set to true.
					exitValue = p.exitValue();
					logger.info("-----p.exitValue()-----:"+exitValue);
					finished = true;

				} catch (IllegalThreadStateException e) {
					// Process is not finished yet;
					// Sleep a little to save on CPU cycles
					//e.printStackTrace();
					//logger.info("process 进程阻塞.. ");
					TimeUnit.MILLISECONDS.sleep(500L);
				}
			}
		} catch (Exception e) {
			// unexpected exception! print it out for debugging...
			e.printStackTrace();
		}

		logger.info("读取标准错误流信息..:"+serr.toString());
		//直播录制时候 无直播流判断 y 
		if(serr.indexOf("failed to read RTMP packet header") >-1){
			exitValue = -1;
			logger.warn("------没有文件流-----返回值 exitValue = "+exitValue);	
		}
		
		return exitValue;
	}
	
	
}
