package com.serio.core.utils;

import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zl.shi
 */
public class TypeConverter {
	
	
	public static Object stringToType( String str, Class<?> type ) {
		
		if ( type.equals(String.class) ) {
			return str;
		}
		
		return JSON.parseObject(str, type);
		
	}
	
	
	public static boolean convertToBoolean( String str ) {
		return "true".equalsIgnoreCase(str);
	}
	
	
	public static Map convertToMap( String str ) {
		JSONObject jsonObj = JSON.parseObject(str);
		return jsonObj.toJavaObject(Map.class);
	}
	
}
