package com.serio.core.parser;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serio.core.annotation.AnnotationProcesser;
import com.serio.core.annotation.parser.ArgName;
import com.serio.core.model.Result;
import com.serio.core.utils.ReflectionUtils;
import com.serio.core.utils.ResultUtils;

/**
 * 用于处理接口返回的结果默认解析器，默认接口返回的是一个集合
 * @author zl.shi
 *
 */
public class DefaultResultParser implements ResultParser{
	
	private static final Logger	log	= LoggerFactory.getLogger(DefaultResultParser.class);

	ResultFactory resultFactory;
	
	public DefaultResultParser() {
		this.resultFactory = new DefaultResultFactory();
	}
	
	/**
	 * 处理返回的结果list，将其转换成指定的对象
	 * @see com.serio.core.parser.ResultParser#parseResult(java.util.List, java.lang.Class)
	 * @author zl.shi
	 * @param resultList
	 * @param returnComClass	这个接口所对应的类eg:GivexResultCommon994.class
	 * @return
	 */
	public <T>Result<T> parseResult( List<String> resultList, Class<?> returnComClass ) {
		
		Map<Field, Object> filedsMap = AnnotationProcesser.getAllAnnotation(returnComClass, ArgName.class);
		
		if ( filedsMap.size() <= 0 ) {
			return parseResultList( resultList, returnComClass );
		} else {
			return parseResultAnnotation( resultList, returnComClass);
		}
		
	}
	
	
	/**
	 * Parse the annotation result.
	 * @author zl.shi
	 * @param resultList
	 * @param returnComClass
	 * @return
	 */
	public <T>Result<T> parseResultAnnotation( List<String> resultList, Class<?> returnComClass) {
		
		Map<Field, Object> filedsMap = AnnotationProcesser.getAllAnnotation(returnComClass, ArgName.class);
		String[] fieldNames = annotationFieldsList( filedsMap );
		
		return buildResult( new Result(), resultList, returnComClass, fieldNames );
		
	}
	
	
	/**
	 * Find all the fields order list
	 * @author zl.shi
	 * @param filedsMap
	 * @return
	 */
	public String[] annotationFieldsList( Map<Field, Object> filedsMap ) {
		
		String[] fieldNames = new String[filedsMap.size()];
		
		ArgName annotation = null;Field field  = null; 
		for ( Entry<Field, Object> entry : filedsMap.entrySet() ) {
			annotation = (ArgName)entry.getValue();
			if ( StringUtils.isEmpty(annotation.value()) ) {
				field  = entry.getKey();
				fieldNames[annotation.index()] = field.getName();
			} else {
				fieldNames[annotation.index()] = annotation.value();
			}
		}
		
		return fieldNames;
	}
	
	/**
	 * 没有使用注解，通过继承的方式找到需要赋值的字段
	 * @author zl.shi
	 * @param resultList
	 * @param returnComClass
	 * @return
	 */
	public <T>Result<T> parseResultList( List<String> resultList, Class<?> returnComClass ) {
		Result result = new Result();
		
		try {
			
			result.setOriginalResult(resultList);
			if ( !ResultUtils.checkResultList( resultList, result ) ) {
				return result;
			}
			
			return buildResult( result,resultList, returnComClass );
			
		} catch (Exception e) {
			return setExceptionResult( result, e );
		}
	}
	
	
	
	/**
	 * 构建Result，通过继承的方式找到需要赋值的字段
	 * @param result
	 * @param resultCom
	 * @param resultList
	 * @param successMethodNames
	 * @param errorMethodNames
	 * @return
	 */
	public <T>Result<T> buildResult( Result<T> result, List<String> resultList, Class<?> resultComClass ) {
		
		try {
			
			Object resultComObj = resultComClass.newInstance();
			String[] successMethodNames = (String[])ReflectionUtils.invokeMethod(resultComObj, "getFieldsSuccessNames", null);
			String[] errorMethodNames = (String[])ReflectionUtils.invokeMethod(resultComObj, "getFieldsErrorNames", null);
			
			
			if ( ResultUtils.checkSuccesss( resultList ) ) {
				ReflectionUtils.setObjectAttrs(resultList, successMethodNames, resultComObj, resultComClass);
			} else {
				ReflectionUtils.setObjectAttrs(resultList, errorMethodNames, resultComObj, resultComClass);
			}
			
			return resultFactory.buildResultArg( result, resultComObj );
			
		} catch (Exception e) {
			return setExceptionResult( result, e );
		}
	}
	
	
	/**
	 * Build the result.
	 * @author zl.shi
	 * @param result
	 * @param resultList
	 * @param resultComClass
	 * @param fieldNames
	 * @return
	 */
	public <T>Result<T> buildResult( Result<T> result, List<String> resultList, Class<?> resultComClass,  String[] fieldNames ) {
		
		try {
			
			Object resultComObj = resultComClass.newInstance();
					
			ReflectionUtils.setObjectAttrs(resultList, fieldNames, resultComObj, resultComClass);

			return resultFactory.buildResult(result, resultComObj);
			
		} catch (Exception e) {
			return setExceptionResult( result, e );
		}
	}
	

	/**
	 * 异常信息默认的处理方式
	 * @param result
	 * @param e
	 * @return
	 */
	public static <T>Result<T> setExceptionResult( Result<T> result, Exception e ) {
		
		if ( result == null ) {
			result = new Result();
		}
		
		result.setSuccess(false);
		result.setErrorMessage(e.getLocalizedMessage());
		result.setHasException(true);
		result.setException(e);
		log.error("Something abnormal", e);
		return result;
		
	}


	public void setResultFactory(ResultFactory resultFactory) {
		this.resultFactory = resultFactory;
	}

}
