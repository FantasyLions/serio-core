package com.serio.core.parser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.serio.core.model.Result;
import com.serio.core.utils.ResultUtils;
import com.serio.core.utils.ReflectionUtils;

/**
 * 用于处理接口返回的结果默认解析器，默认接口返回的是一个集合
 * @author zl.shi
 *
 */
public class DefaultResultParser implements ResultParser{
	
	private static final Logger	log	= LoggerFactory.getLogger(DefaultResultParser.class);

	ResultFactory resultFactory;
	
	/**
	 * 处理返回的结果list，将其转换成指定的对象
	 * @see com.serio.core.parser.ResultParser#parseResult(java.util.List, java.lang.Class)
	 * @author zl.shi
	 * @param resultList
	 * @param returnComClass	这个接口所对应的类eg:GivexResultCommon994.class
	 * @return
	 */
	public <T>Result<T> parseResult( List<String> resultList, Class<?> returnComClass ) {
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
	 * 构建Result
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
