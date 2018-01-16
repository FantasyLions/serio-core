package com.serio.core.utils;

import org.junit.Test;

/**
 * @author zl.shi
 */
public class HttpTest {
	
	@Test
	public void getTest() {
		System.out.println(HttpTools.doGetURL("https://staging.converse.com.cn/"));
	}
	
	
	public void test() {
		String[] str = "TLSv1.2".split(" *, *");
		System.out.println(str[0]);
	}
}
