package com.serio.core.parser;

import com.serio.core.model.ListResultBaseCommon;
import com.serio.core.model.Result;

/**
 * @author zl.shi
 */
public class DefaultResultFactory implements ResultFactory<ListResultBaseCommon> {

	/* (non-Javadoc)
	 * @see com.serio.core.parser.ResultFactory#buildResultArg(com.serio.core.model.Result, java.lang.Object)
	 */
	public Result<ListResultBaseCommon> buildResultArg(Result<ListResultBaseCommon> result, Object resultComObj) {
		result.setSuccess(true);
		result.setResultCom((ListResultBaseCommon)resultComObj);
		return result;
	}

}
