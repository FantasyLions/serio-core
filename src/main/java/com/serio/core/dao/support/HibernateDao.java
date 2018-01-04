package com.serio.core.dao.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.serio.core.utils.BeanUtils;
import com.serio.core.utils.GenericsUtils;
import com.serio.core.utils.LogUtils;
import com.serio.core.view.Page;


/**
 * Hibernate Dao的泛型基类.
 * <p/>
 * 继承于Spring的<code>HibernateDaoSupport</code>,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 *
 * @author calvin
 * @author tin
 * @see HibernateDaoSupport
 * @see HibernateDao
 */
@SuppressWarnings("unchecked")
public class HibernateDao<T> extends HibernateDaoSupport {
    private static final Class hibernateDaoClass=HibernateDao.class;
	protected Class<T> entityClass;// DAO所管理的Entity类型.
    
	protected class Wrapper{
      StringBuilder hql;
      List<Object> values;
      public Wrapper(){}
      public StringBuilder getHql() {
        return hql;
      }
      public void setHql(StringBuilder hql) {
        this.hql = hql;
      }
      public List<Object> getValues() {
        return values;
      }
      public void setValues(List<Object> values) {
        this.values = values;
      }
      
    }
	/**
	 * 使用注解注入sessionFactory
	 * @param sessionFactory
	 */
	@Resource(name="sessionFactory") 
	public void setSessionFactory0(SessionFactory sessionFactory){ 
	  super.setSessionFactory(sessionFactory); 
	} 
	
	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	public HibernateDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 取得entityClass.JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}
	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.get()方法返回实体对象. 如果对象不存在，则返回null.
	 */
	public T get(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}
	
	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 */
	public T load(Serializable id){
		return (T) getHibernateTemplate().load(entityClass, id) ;
	}
	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 获取全部对象,带排序字段与升降序参数.
	 */
	public List<T> getAll(String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc)
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(Order.asc(orderBy)));
		else
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(Order.desc(orderBy)));
	}

	/**
	 * 保存对象.
	 */
	public void save(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	/**
	 * 删除对象.
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}
	
	/**
	 * 根据ID删除对象.
	 */
	public void removeById(Serializable id) {
		remove(get(id));
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 调用方式如下：
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 *
	 * @param values 可变参数.
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 创建Criteria对象.
	 *
	 * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
	 */
	public Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 *
	 * @see #createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		Assert.hasText(orderBy);

		Criteria criteria = createCriteria(criterions);

		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 *
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 */
	public List<T> find(String hql, Object... values) {
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return createCriteria(Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询唯一对象.
	 *
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public T findUniqueBy(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * 分页查询函数，使用hql.
	 *
	 * @param pageNo 页号,从1开始.
	 */
	public Page<T> pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		// Count查询
		String countQueryString = " select count(*) " + removeSelect(removeOrders(hql));
		List<Long> countlist = getHibernateTemplate().find(countQueryString, values);
		int totalCount = ((Long) countlist.get(0) ).intValue();
		int totalPage =totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
		if (totalCount < 1){
			return new Page<T>();
        }else if(totalPage < pageNo){
            pageNo=totalPage;
        }
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page<T>(startIndex, totalCount, pageSize, list);
	}
	/**
	 * 模糊查询分页方法
	 * 
	 * @param hql 原始hql语句
	 * @param dataMap 需要传入的标准数据
	 *  keyWord 要查询的关键词
	 *  keyWordField 需要查询的所有关键词字段
	 *  timeField 需要查询的时间范围字段
	 *  fromTime 查询的起始时间
	 *  toTime 查询的结束时间
	 *  pageNo 当前页数
	 *  pageSize 每页的记录数
	 *  props 需要排序的字段
	 *  isAsc 升序或降序,支持 多字段
	 * @param values 需要传入的占位符数据
	 * @return
	 */
    public Page<T> pagedFuzzyQuery(String srcHql, Map<String, String> dataMap, Object... values) {
      StringBuilder hql = new StringBuilder(srcHql);
      /** 关键词 */
      String keyWord = dataMap.get("keyWord");
      if (StringUtils.isNotBlank(keyWord)) {
        String keyWordField = dataMap.get("keyWordField");
        if (keyWordField == null) {
          LogUtils.error("keyWordField不能为空",hibernateDaoClass);
        }
        String hqlLike = concatHql(keyWordField, keyWord);
        hql.append(hqlLike);
      }
      /** 起始时间 */
      String timeField = dataMap.get("timeField");
      if (StringUtils.isNotBlank(timeField)) {
        String fromTime = dataMap.get("fromTime");
        if (StringUtils.isNotBlank(fromTime)) {
          hql.append(" and ");
          hql.append(timeField);
          hql.append(">'");
          hql.append(fromTime);
          hql.append("'");
        }
        String toTime = dataMap.get("toTime");
        if (StringUtils.isNotBlank(toTime)) {
          hql.append(" and ");
          hql.append(timeField);
          hql.append("<'");
          hql.append(toTime);
          hql.append("'");
        }
      }
      /** 分页 */
      String strPageNo = dataMap.get("currentPage");// 必须,默认为1
      Integer pageNo = 1;
      if (StringUtils.isNotBlank(strPageNo)) {
        pageNo = Integer.parseInt(strPageNo);
      }
      String strpageSize = dataMap.get("pageSize");// 必须
      Integer pageSize=10;
      if (StringUtils.isNotBlank(strpageSize)) {
        pageSize = Integer.parseInt(strpageSize);
      } else {
        LogUtils.error("pageSize不能为空",hibernateDaoClass);
      }
      /** 排序 */
      String props = dataMap.get("props");
      String isAsc = dataMap.get("isAsc");
      String orderHql = orderHql(props, isAsc);
      hql.append(orderHql);
      return pagedQuery(hql.toString(), pageNo, pageSize, values);
    }
	/**
	 * 多字段排序
	 * @param props 需要排序的字段
	 * @param isAsc 升序或降序
	 * @return 拼接好的hql
	 */
    private String orderHql(String props, String isAsc) {
      if (StringUtils.isNotBlank(props)) {
        if (isAsc == null) {
          LogUtils.error("isAsc不能为空",hibernateDaoClass);
        }
        String[] propFields = props.split(",");
        String[] isAscs = isAsc.split(",");
        if (propFields.length != isAscs.length) {
          LogUtils.error("props与isAsc长度不匹配",hibernateDaoClass);
        }
        StringBuilder hql = new StringBuilder(" order by ");
        for (int i = 0; i < propFields.length; i++) {
          hql.append(propFields[i]);
          hql.append(" ");
          if ("false".equalsIgnoreCase(isAscs[i]) || "desc".equalsIgnoreCase(isAscs[i])) {
            hql.append("desc");
          } else {
            hql.append("asc");
          }
          if (i != propFields.length - 1) {
            hql.append(",");
          }
        }
        return hql.toString();
      } else {
        LogUtils.error("props不能为空",hibernateDaoClass);
      }
      return "";
    }
	/**
	 * 拼接模糊查询hql
	 * @param keyWordField 关键词字段
	 * @param keyWord 关键词
	 * @return 拼接好的hql
	 */
    private String concatHql(String keyWordField, String keyWord) {
      StringBuilder hql = new StringBuilder("and (");
      String[] keyWordFields = keyWordField.split(",");
      for (int i = 0; i < keyWordFields.length; i++) {
        hql.append(keyWordFields[i]);
        hql.append(" like '%");
        hql.append(keyWord);
        hql.append("%'");
        // 不是最后一个
        if (i != keyWordFields.length - 1) {
          hql.append(" or ");
        }
      }
      hql.append(")");
      return hql.toString();
    }

     /**
      * 分页查询并按属性排序函数，使用hql.
	  *
	  * @param pageNo 页号,从1开始.
	  */
	public Page<T> pagedQuery(String hql, int pageNo, int pageSize, String orderBy, boolean isAsc, Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		// Count查询
		String countQueryString = " select count(*) " + removeSelect(removeOrders(hql));
		List<Long> countlist = getHibernateTemplate().find(countQueryString, values);
		int totalCount = ((Long) countlist.get(0) ).intValue();
		int totalPage =totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
		if (totalCount < 1){
			return new Page<T>();
		}else if(totalPage < pageNo){
		    pageNo=totalPage;
		}
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		if ( StringUtils.isNotBlank(orderBy) ) {
			if ( isAsc ) {
				hql = hql + " order by " + orderBy + " asc ";
			} else {
				hql = hql + " order by " + orderBy + " desc ";
			}
		}
		Query query = createQuery(hql, values);
		List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page<T>(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page<T> pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		Assert.notNull(criteria);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List<CriteriaImpl.OrderEntry>) BeanUtils.forceGetProperty(impl, "orderEntries");
			BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList<Object>());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 执行查询
		int totalCount = 
			( (Long) criteria.setProjection(Projections.rowCount() )
								.uniqueResult() ).intValue();

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			/*如果为空，则表示返回结果的每一行都代表一个实体的实例*/
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		try {
			BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 返回分页对象
		if (totalCount < 1)
			return new Page<T>();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<T> list = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page<T>(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page<T> pagedQuery(int pageNo, int pageSize, Criterion... criterions) {
		Criteria criteria = createCriteria(criterions);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page<T> pagedQuery(int pageNo, int pageSize, String orderBy, boolean isAsc,
						   Criterion... criterions) {
		Criteria criteria = createCriteria(orderBy, isAsc, criterions);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 *
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames) {
		Assert.hasText(uniquePropertyNames);
		Criteria criteria = createCriteria().setProjection(Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getIdName(entityClass);

			// 取得entity的主键值
			Serializable id = getId(entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (Integer) criteria.uniqueResult() == 0;
	}

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	public Serializable getId(Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Assert.notNull(entity);
		Assert.notNull(entityClass);
		return (Serializable) PropertyUtils.getProperty(entity, getIdName(entityClass));
	}

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	public String getIdName(Class<T> clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
		Assert.notNull(meta, "Class " + clazz + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName() + " has no identifier property define.");
		return idName;
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos).replaceAll("fetch", "");
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
}