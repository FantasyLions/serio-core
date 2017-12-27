package com.serio.core.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class NumberUtils {
	
	/**
	 * 字符串转为Integer范围的自然数
	 * @param str 要转换的字符串
	 * @param defalut 转换失败默认返回的数字
	 * @return
	 */
	public static Integer parseStr2NaturalInteger(String str, Integer defalut) {
		return parseStr2NaturalInteger(str, defalut, Integer.MAX_VALUE);
	}
	
	/**
	 * 字符串转为Integer范围的自然数
	 * @param str 要转换的字符串
	 * @param defalut 转换失败默认返回的数字
	 * @param maxValue 限制最大值
	 * @return
	 */
	public static Integer parseStr2NaturalInteger(String str, Integer defalut, Integer maxValue) {
		Integer number = defalut;
		if (StringUtils.isNotBlank(str)) {
			Pattern p = Pattern.compile("[1-9]\\d*");
			Matcher m = p.matcher(str);
			boolean b = m.matches();
			if (b) {	// 如果匹配
				String maxInteger = String.valueOf(maxValue);
				int maxStrLength = maxInteger.length();
				int strLength = str.length();
				int offset = 0;
				if (maxStrLength > strLength) {	// 
					offset = maxStrLength - strLength;
					str = fillPlaceholder("0", offset) + str;
				} else if (maxStrLength < strLength) {
					offset = strLength - maxStrLength;
					maxInteger = fillPlaceholder("0", offset) + maxInteger;
				}
				
				// 不超过Integer.MAX_VALUE
				if (maxInteger.compareTo(str) > -1) {
					number = Integer.parseInt(str);
					return number;
				}
			}
		}
		
		return number;
	}
	
	/**
	 * 拼接占位符
	 * @param placeholder 占位符
	 * @param phNum 占位符个数
	 * @return 
	 */
	public static String fillPlaceholder(String placeholder, int phNum) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < phNum; i++) {
			sb.append(placeholder);
		}
		return sb.toString();
	}
	
	/**
	 * 格式化数字
	 * @param number
	 * @param pattern #,##0.###
	 * @return
	 */
	public static String formatNumber(double number, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(number);
	}
	
    /**
     * 将字符串数组替换为int数组
     * 
     * @param numStrs 字符串数组
     * @return 转换后的int数组
     */
    public static Integer[] convert(String[] numStrs) {
      int length = numStrs.length;
      Integer[] idInts = new Integer[length];
      for (int i = 0; i < length; i++) {
        idInts[i] = Integer.parseInt(numStrs[i]);
      }
      return idInts;
    }
	  
	public static void main(String[] args) {
		String s1 = "12.3";
		String s2 = "-123";
		String s3 = "0";
		String s4 = "000";
		String s5 = "2147483648";
		String s8 = "asd123";
		String s6 = "2147483647";
		String s7 = "321";
		
		System.out.println(parseStr2NaturalInteger(s1, 1));
		System.out.println(parseStr2NaturalInteger(s2, 1));
		System.out.println(parseStr2NaturalInteger(s3, 1));
		System.out.println(parseStr2NaturalInteger(s4, 1));
		System.out.println(parseStr2NaturalInteger(s5, 1));
		System.out.println(parseStr2NaturalInteger(s8, 1));
		System.out.println(parseStr2NaturalInteger(s6, 1));
		System.out.println(parseStr2NaturalInteger(s7, 1));
		
		double d = 2147483.64856d;
		System.out.println(formatNumber(d, "#,##0.###"));
		
	}

}
