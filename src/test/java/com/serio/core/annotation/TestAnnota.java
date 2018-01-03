package com.serio.core.annotation;

import org.junit.Test;

import com.serio.core.utils.media.Encoder;
import com.serio.core.utils.media.VideoAttributes;
import com.serio.core.utils.media.VideoSize;

import junit.framework.TestCase;

/**
 * @author zl.shi
 */
public class TestAnnota extends TestCase{
	
//	@Test
//	public void test3() {
//		AnnotationProcesser.getAnnotationInfos(ArgNameEntry.class, ArgName.class, "value");
//		try {
//			System.out.println(AnnotationProcesser.getAnnotationInfo(ArgNameEntry.class.getDeclaredField("video"), ArgName.class, "value"));
//		} catch (NoSuchFieldException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//	}
	
//	@Test
//	public void test4() {
//		Annotation[] annotations1 = EncodingAttributes.class.getAnnotations();
//		Annotation[] annotations2 = EncodingAttributes.class.getDeclaredAnnotations();
//		System.out.println("----" + annotations1.length);
//		System.out.println("----" + annotations2.length);
//		
//	}

//	@Test
//	public void test4() {
//		
//		AudioAttributes audioAttributes = new AudioAttributes();
//		audioAttributes.setBitRate(22);
//		audioAttributes.setChannels(11);
//		audioAttributes.setCodec("bin246");
//		audioAttributes.setSamplingRate(44);
//		audioAttributes.setVolume(66);
//		
//		Encoder encoder = new Encoder();
//		encoder.setAudioAttributes(null, audioAttributes);
//	}
	
	@Test
	public void test5() {
		
		VideoAttributes videoAttributes = new VideoAttributes();
		videoAttributes.setBitRate(33);
		videoAttributes.setCodec("bin246");
		videoAttributes.setFrameRate(56);
		videoAttributes.setSize(new VideoSize(640, 360));
		Encoder encoder = new Encoder();
		
		encoder.setVideoAttriutes( null, videoAttributes );
	}
	
}
