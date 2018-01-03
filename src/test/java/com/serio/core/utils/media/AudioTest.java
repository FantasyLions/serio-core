package com.serio.core.utils.media;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 * @author zl.shi
 */
public class AudioTest  extends TestCase {
	protected static Logger logger = LoggerFactory.getLogger(AudioTest.class);
	@Test
	public void testOne() {
		VideoProcessor videoProcessor = new VideoProcessor();
		try {
			videoProcessor.transcode( "C:\\Users\\zhengliang.shi\\Videos\\testVideo.3gp", "32k" , "940*560", "C:\\Users\\zhengliang.shi\\Videos\\testVideoo.mp4" );
		} catch (IOException e) {
			logger.error("Error",e);
			e.printStackTrace();
		}
	}
	
//	D:\software_package\ffmpeg-20171225-613f789-win64-static\bin\ffmpeg.exe -ss 00:00:03 -i C:\\Users\\zhengliang.shi\\Videos\\3zhzI640.mp4 -f mjpeg -r 1 -vframes 1 -s 640*360 -y C:\\Users\\zhengliang.shi\\Videos\\3zhzI640.jpg
//	@Test
//	public void testCut() {
//		
//		File source = new File("C:\\Users\\zhengliang.shi\\Videos\\3zhzI640.mp4");
//		File target = new File("C:\\Users\\zhengliang.shi\\Videos\\3zhzI6407.jpg");
//		try {
//			VideoProcessor videoProcessor = new VideoProcessor();
//			videoProcessor.videoCapture(source, target, 240f, 940, 560);
//		} catch (EncoderException e) {
//			e.printStackTrace();
//		}
//		
//	}
}
