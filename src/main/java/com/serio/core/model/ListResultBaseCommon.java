package com.serio.core.model;

/**
 * <p>接口返回的值如果是个集合，需要依赖这个类用于解析结果。</p>
 * @author zl.shi
 *
 */
public abstract class ListResultBaseCommon {

	/**
	 * 接口返回字段的名字
	 * @author zl.shi
	 * @return
	 */
	public abstract String[] getFieldsSuccessNames();
	
	public abstract  String[] getFieldsErrorNames();
	
	
}
