package com.serio.core.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.serio.core.model.Result;

public class ResultUtils {
	
	private static final Logger	log	= LoggerFactory.getLogger(ResultUtils.class);
	
	/**
	 * <p>检查接口响应的状态是否为成功，目前所有接口的result code 为0时表示成功，之后新添加的接口需要看具体的接口文档</p>
	 * @param resultList
	 * @return
	 */
	public static boolean checkSuccesss( List<String> resultList ) {
		
		if ( null == resultList || resultList.size() < 2 ) {
			return false;
		}
		
		return "0".equals(resultList.get(1));
		
	}
	
	
	/**
	 * 验证接口返回结果是否有效
	 * @param resultList	Givex响应的原始数据
	 * @param result		封装后的结果对象
	 * @return
	 */
	public static boolean checkResultList( List<String> resultList, Result result ) {
		
		if ( null == resultList || resultList.size() <= 0 ) {
			result.setSuccess(false);
			result.setErrorMessage("Array reseponse from is null or size is less than 0.");
			log.error("Array reseponse from Givex is null or size is 0.");
			return false;
		}
		
		return true;
	}
}
