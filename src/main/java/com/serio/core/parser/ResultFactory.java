package com.serio.core.parser;

import com.serio.core.model.Result;

/**
 * @author zl.shi
 */
public interface ResultFactory<T> {
	
	/**
	 * 构建result参数
	 * @author zl.shi
	 * @param result
	 * @param resultComObj
	 * @return
	 */
	public Result<T> buildResultArg( Result<T> result, Object resultComObj );
	
}
