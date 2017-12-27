package com.serio.core.utils.dao.support;

import java.io.Serializable;
import java.util.List;

import com.serio.core.utils.Page;

public interface BaseDao<T> {
	
	public T saveObject(T o);
	
	public void deleteObject(Serializable id);
	
	public T getObject(Serializable id);
	
	public List<T> listObjects(T o, String prop, boolean isAsc);
	
	public Page<T> pageObjects(T o, String prop, boolean isAsc, int pageNo, int pageSize);
	
	public T updateObject(T o);

}
