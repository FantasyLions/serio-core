package com.serio.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;



/**
 * 反射的工具类
 * @author zl.shi
 *
 */
public class ReflectionUtils {
	
	public static final String SETTER_METHOD_NAME_PREFIX = "set";
	
	
	public static final String GETTER_METHOD_NAME_PREFIX = "get";
	
	/**
	 * 设置参数属性，字段名和属性值一一对应，设置到指定对象的字段中，
	 * <p>Note:
	 * 	调用时，需要自行数组判空，处理异常
	 * </p>
	 * @param resultList
	 * @param fieldNames
	 * @param obj
	 * @param objClass
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 */
	public static void setObjectAttrs( List<String> values, String[] fieldNames, Object obj, Class<?> objClass )
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Assert.notNull(values, "values must not be null");
		Assert.notNull(fieldNames, "fieldNames must not be null");
		
		if ( values.size() > fieldNames.length ) {
			throw new RuntimeException("Attribute and value quantity do not match!");
		}
		
		for ( int i =0; i < values.size(); i++ ) {
            setObjectAttr( obj, fieldNames[i], values.get(i),objClass );
        }
	}
	
	
	/**
	 * 设置字段值到制定对象的字段中
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @param objClass
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException 
	 */
	public static void setObjectAttr( Object obj, String fieldName, String value, Class<?> objClass )
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		Field field = objClass.getDeclaredField(fieldName);
		
		Class<?> fieldtype = field.getType();
		
		Method method = objClass.getMethod( SETTER_METHOD_NAME_PREFIX + StringUtils.capitalize(fieldName), fieldtype );
		
        method.invoke( obj, TypeConverter.stringToType(value, fieldtype) );
	}
	
	
	/**
	 * 调用字段对应的getter方法
	 * @author zl.shi
	 * @param obj
	 * @param fieldName
	 * @param objClass
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException
	 */
	public static Object getObjectAttr( Object obj, String fieldName, Class<?> objClass ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		Field field = objClass.getDeclaredField(fieldName);
		
		return getObjectAttr( obj, field, objClass );
	}
	
	
	/**
	 * 调用字段对应的getter方法
	 * @author zl.shi
	 * @param obj
	 * @param field
	 * @param objClass
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Object getObjectAttr( Object obj, Field field, Class<?> objClass ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		
		Method method = objClass.getMethod( GETTER_METHOD_NAME_PREFIX + StringUtils.capitalize(field.getName()), null );
		
        return method.invoke( obj, null );
	}
	
	
	/**
	 * 执行某对象方法
	 * 
	 * @param owner
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param params
	 *            参数
	 * @return 方法返回值
	 */
	public static Object invokeMethod(Object owner,String methodName,Object...params) {
		Class<?> ownerClass = owner.getClass();
		Method method = getMethod(ownerClass, methodName, params);
		if (null == method){
			return null;
		}
		try{
			return method.invoke(owner, params);
		}catch (IllegalArgumentException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (InvocationTargetException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获得指定的方法
	 * @author zl.shi
	 * @param ownerClass
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static Method getMethod( Class<?> ownerClass, String methodName, Object...params) {
		try {
			return ownerClass.getMethod( methodName, findTypes(params) );
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 找到指定的类型
	 * @author zl.shi
	 * @param params
	 * @return
	 */
	public static Class<?>[] findTypes( Object...params ) {
		
		if ( params == null || params.length <= 0 ) {
			return null;
		}
		
		int len = params.length;
		Class<?>[] paramsClass = new Class[len];
		Object param = null;
		Class<?> clz = null;
		
		for (int i = 0; i < len; ++i){
			param = params[i];
			// 是否是boolean类型
			if (param instanceof Boolean){
				paramsClass[i] = boolean.class;
				// 是不是Integer类型
			}else if (param instanceof Integer){
				paramsClass[i] = int.class;
			}else{
				clz = param.getClass();
				if (clz.getName().equals("org.jfree.data.category.DefaultCategoryDataset")){
					try{
						paramsClass[i] = Class.forName("org.jfree.data.category.CategoryDataset");
					}catch (ClassNotFoundException e){
						e.printStackTrace();
					}
				}else{
					paramsClass[i] = clz;
				}
			}
		}
		
		return paramsClass;
	}

}
