package com.serio.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CucStringUtils {
	/**
     * 过滤 文本中的特定字符 例如过滤掉文本中包含的 html标签
     * @param str 需要过滤的文本 
     * @param regType 要替换的正则类型  例如 "<([^>]*)>" 过滤所有以"<"开头     以">"结尾的标签 
     * @return 新的文本对象
     */
    public static String filterHtml(String str,String regType) { 
    	if(StringUtils.isNotBlank(str)){
	    	//定义 正则生成器 pattern 对象
	    	Pattern pattern = Pattern.compile(regType); 
	    	//检测 字符串是否符合 pattern 
	    	Matcher matcher = pattern.matcher(str);
	    	StringBuffer sf = new StringBuffer();
	    	while (matcher.find()) { 
	    		//用空字符串替换出现 符合pattern规则的 字符串内容，再保存从开始到替换后之间的字符串到 sb 中
	    		matcher.appendReplacement(sf, "");
	    	} 
	    	//将str 中没被检测到的字符串保存到 sb中
	    	matcher.appendTail(sf); 
	    	//替换str中多个空格和包含 &nbsp;(或者 &rdquo;)
	    	return sf.toString().replaceAll("\\s+|\\&\\w+\\;", ""); 
    	}else{
    		return "";
    	}
   }
    
}
