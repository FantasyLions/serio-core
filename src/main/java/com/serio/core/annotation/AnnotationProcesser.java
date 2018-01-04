package com.serio.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.serio.core.utils.ReflectionUtils;

/**
 * @author zl.shi
 */
public class AnnotationProcesser {
	
	/**
	 * Gets the value of annotation for all of the fields,
	 * @author zl.shi
	 * @param appClazz
	 * @param annotationClazz
	 * @param annotationName
	 * @return	Map< Field, Annotation value >
	 */
    public static Map<Field, Object> getAnnotationInfos( Class<?> appClazz, Class<? extends Annotation> annotationClazz, String annotationName ){
    	
    	Map<Field, Object> mapKey = new HashMap<Field, Object>();
    	
        Field[] fields = appClazz.getDeclaredFields();
        
        for(Field field :fields) {
        	Object annotation = getAnnotation( field , annotationClazz);
        	
        	if ( annotation != null ) {
        		mapKey.put( field, ReflectionUtils.invokeMethod(annotation, annotationName, null) );
        	}
        }
        
        return mapKey;
    }
    
    
    /**
     * Find the value of annotation for the specified field
     * @author zl.shi
     * @param field
     * @param annotationClazz
     * @param annotationName
     * @return
     */
    public static Object getAnnotationInfo( Field field, Class<? extends Annotation> annotationClazz, String annotationName ){
    	
    	Object annotation = getAnnotation( field , annotationClazz);
    	
    	if ( annotation != null ) {
    		return ReflectionUtils.invokeMethod(annotation, annotationName, null);
    	}
        
        return null;
    }
    
    
    /**
     * Find the annotation of the field, if not exist return null;
     * @author zl.shi
     * @param field
     * @param annotationClass
     * @return
     */
    public static <T extends Annotation> T getAnnotation( Field field , Class<T> annotationClass) {
    	
    	if(field.isAnnotationPresent(annotationClass)){
        	return (T)field.getAnnotation(annotationClass);
        }
    	
		return null;
    	
    }
    
    
    /**
     * 
     * @author zl.shi
     * @param appClazz
     * @param annotationClass
     * @param annotationName
     * @param value
     * @return	Map< Field, Annotation >
     */
    public static Map<Field, Object> getSpecifiedAnnotation( Class<?> appClazz, Class<? extends Annotation> annotationClass, String annotationName, Object value ) {
    	
    	Map<Field, Object> map = new HashMap<Field, Object>();
    	
    	Field[] fields = appClazz.getDeclaredFields();
    	
    	for ( Field field : fields ) {
    		
    		if( field.isAnnotationPresent(annotationClass) ) {
    			Annotation annotation = field.getAnnotation(annotationClass);
    			if ( annotation == null ) {
    	    		continue;
    	    	}
    			
    			Object annotationValue = ReflectionUtils.invokeMethod(annotation, annotationName, null);
    			
	    		if (Objects.equals(annotationValue, value) ) {
	    			map.put( field, annotation );
	    		}
    		}
    		
    	}
    	
		return map;
    	
    }
    
    
    /**
     * Find all of the filed in the <code>appClazz</code>
     * @author zl.shi
     * @param appClazz
     * @param annotationClass
     * @return	Map< Field, Annotation >
     */
    public static Map<Field, Object> getAllAnnotation( Class<?> appClazz, Class<? extends Annotation> annotationClass ) {
    	Map<Field, Object> map = new HashMap<Field, Object>();
    	
    	Field[] fields = appClazz.getDeclaredFields();
    	
    	for ( Field field : fields ) {
    		
    		if( field.isAnnotationPresent(annotationClass) ) {
    			map.put( field, field.getAnnotation(annotationClass) );
    		}
    		
    	}
    	
		return map;
    }
    		

}
