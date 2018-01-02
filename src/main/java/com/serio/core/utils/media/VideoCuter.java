package com.serio.core.utils.media;

import java.io.File;
import java.io.IOException;

/**
 * @author zl.shi
 */
public class VideoCuter {
	
	/**
	 * The locator of the ffmpeg executable used by this encoder.
	 */
	private FFMPEGLocator locator;

	/**
	 * It builds an encoder using a {@link DefaultFFMPEGLocator} instance to
	 * locate the ffmpeg executable to use.
	 */
	public VideoCuter() {
		this.locator = new DefaultFFMPEGLocator();
	}

	/**
	 * It builds an encoder with a custom {@link FFMPEGLocator}.
	 * 
	 * @param locator
	 *            The locator picking up the ffmpeg executable used by the
	 *            encoder.
	 */
	public VideoCuter(FFMPEGLocator locator) {
		this.locator = locator;
	}
	
	
	/**
	 * Cut the video to image by the specify time.
	 * @author zl.shi
	 * @param videoSource
	 * @param imgSource
	 * @param time	like this 00:00:05
	 * @param size	like this 640*360
	 * @throws EncoderException
	 */
	public void cutTime( File videoSource, File imgSource, String time, String size ) throws EncoderException {
		
		FFMPEGExecutor ffmpeg = locator.createExecutor();
		ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_SECONDS);
		ffmpeg.addArgument(time);
		ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_SOURCE);
		ffmpeg.addArgument(videoSource.getAbsolutePath());
		ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_FMT);
		ffmpeg.addArgument("image2");
		ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_SIZE);
		ffmpeg.addArgument(size);
		ffmpeg.addArgument(MediaConstant.PARAMETER_NAME_DESTINATION);
		ffmpeg.addArgument(imgSource.getAbsolutePath());
		
		try {
			ffmpeg.execute();
		} catch (IOException e) {
			throw new EncoderException(e);
		}
		
	}

}
