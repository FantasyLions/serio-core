package com.serio.core.utils.dao.support;

import java.io.Serializable;
import java.util.List;

import com.serio.core.utils.Page;

public interface BaseService<T> {
	
	/**
	 * 保存实体
	 * @param o
	 * @return
	 */
	public T saveObject(T o);
	
	/**
	 * 删除实体
	 * @param id
	 * @return
	 */
	public void deleteObject(Serializable id);
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public T getObject(Serializable id);
	
	/**
	 * 查询实体列表
	 * @param o 查询条件
	 * @param prop 排序字段
	 * @param isAsc 排序规则：升级或降序
	 * @return
	 */
	public List<T> listObjects(T o, String prop, boolean isAsc);
	
	/** 
	 * 分页查询实体列表
	 * @param o 查询条件
	 * @param prop 排序字段
	 * @param isAsc 排序规则：升序或降序
	 * @param pageNo 当前页
	 * @param pageSize 一页的记录数
	 * @return
	 */
	public Page<T> pageObjects(T o, String prop, boolean isAsc, int pageNo, int pageSize);
	
	/**
	 * 更新实体
	 * @param o
	 * @return
	 */
	public T updateObject(T o);
	
	/**
	 * 删除实体
	 * @param ids 多个ids用“,”分隔
	 * @return
	 */
	public void deleteObejcts(String ids) ;

}
