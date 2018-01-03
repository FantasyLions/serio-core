package com.serio.core.utils.media;

import java.io.File;
import java.io.IOException;

/**
 * @author zl.shi
 */
public class VideoProcessor {
	
	/**
	 * The locator of the ffmpeg executable used by this encoder.
	 */
	private FFMPEGLocator locator;

	/**
	 * It builds an encoder using a {@link DefaultFFMPEGLocator} instance to
	 * locate the ffmpeg executable to use.
	 */
	public VideoProcessor() {
		this.locator = new DefaultFFMPEGLocator();
	}

	/**
	 * It builds an encoder with a custom {@link FFMPEGLocator}.
	 * 
	 * @param locator
	 *            The locator picking up the ffmpeg executable used by the
	 *            encoder.
	 */
	public VideoProcessor(FFMPEGLocator locator) {
		this.locator = locator;
	}
	
	
	/**
	 * Cut the video to image by the specify time.
	 * @author zl.shi
	 * @param videoSource
	 * @param imgSource
	 * @param time			like 0.2f
	 * @param sizeWitdh		like 640
	 * @param sizeHeight	like 360
	 * @throws InputFormatException 
	 * @throws IllegalArgumentException 
	 * @throws EncoderException
	 */
	public void videoCapture( File videoSource, File imgSource, float time, Integer sizeWitdh, Integer sizeHeight ) throws IllegalArgumentException, InputFormatException, EncoderException {
		
		VideoAttributes video = new VideoAttributes();
		video.setCodec(MediaConstant.PARAMETER_CODEC_MJPEG);
		video.setFrameRate(1);
		video.setVideoFrames(1);
		video.setSize(new VideoSize(sizeWitdh, sizeHeight));

		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat(MediaConstant.PARAMETER_CODEC_MJPEG);
		attrs.setOffset(time);
		attrs.setVideoAttributes(video);
		Encoder encoder = new Encoder();
		encoder.encode( videoSource, imgSource, attrs );
		
	}
	
	
	/**
	 * 转码
	 * @author zl.shi
	 * @param srcVideoPath
	 * @param bitRate
	 * @param size
	 * @param destVideoFile
	 * @throws IOException 
	 */
	public void transcode( String srcVideoPath, String bitRate, String size, String destVideoFile ) throws IOException {
		FFMPEGExecutor ffmpeg = locator.createExecutor();
		ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_SOURCE);
		
		ffmpeg.addArgument(srcVideoPath); 
        // 视频选项
		ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_FMT);
        ffmpeg.addArgument("mp4");     // 输出mp4格式
        ffmpeg.addArgument("-vcodec"); // 编码器
        ffmpeg.addArgument("libx264");
        ffmpeg.addArgument("-b:v");    // 视频码率
        ffmpeg.addArgument(bitRate);
        ffmpeg.addArgument("-b:a");    // 音频码率
        ffmpeg.addArgument("32k");
        ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_SIZE);  // 图像分辨率
        ffmpeg.addArgument(size);
                
        ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_DESTINATION);  // 覆盖输出文件
        ffmpeg.addArgument(destVideoFile);
        
        ffmpeg.execute();
	}
	

}
