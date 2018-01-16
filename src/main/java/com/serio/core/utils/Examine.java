package com.serio.core.utils;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

/**
 * @author zl.shi
 */
public class Examine {
	
	public static boolean isNoElementEmpty( Object obj, String[] fieldNames, Class<?> objClass ) {
		
		try {
			Object result = null;
			for ( String fieldName : fieldNames ) {
				result = ReflectionUtils.getObjectAttr(obj, fieldName, objClass);
				
				if ( isEmpty( result ) ) {
					return false;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}

	
	public static boolean isEmpty( Object result ) {
		if ( result == null ) {
			return true;
		}
		
		if ( result instanceof String ) {
			if ( StringUtils.isEmpty( (String)result ) ) {
				return true;
			}
		}
		
		if ( result instanceof Collection<?> ) {
			return ((Collection) result).isEmpty();
		}
		
		return false;
	}
}
