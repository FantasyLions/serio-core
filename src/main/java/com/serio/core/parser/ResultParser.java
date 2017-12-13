package com.serio.core.parser;

import java.util.List;

import com.serio.core.model.Result;


/**
 * 对接口返回的结果进行解析
 * @author zl.shi
 *
 */
public interface ResultParser {

	/**
	 * 解析接口返回回来的list
	 * @param resultList
	 * @return
	 */
	public <T> Result<T> parseResult(List<String> resultList, Class<?> returnComClass);


	
}
