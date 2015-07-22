package com.kelly.base.framework;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.csnt.util.validate.Assert;

@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport {

	public Session getCurrentSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

	public T load(Class<T> clazz, Serializable id) {
		return getHibernateTemplate().load(clazz, id);
	}

	public List<T> loadAll(Class<T> clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	public List<T> loadAll(Class<T> clazz, String orderBy, boolean isAsc) {
		Assert.hasLength(orderBy);

		if (isAsc) {
			return (List<T>) getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(clazz).addOrder(
							Order.asc(orderBy)));
		} else {
			return (List<T>) getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(clazz).addOrder(
							Order.desc(orderBy)));
		}
	}

	public T get(Class<T> clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public void save(Object entity) {
		getHibernateTemplate().save(entity);
	}

	public void update(Object entity) {
		getHibernateTemplate().update(entity);
	}

	public void merge(Object entity) {
		getHibernateTemplate().update(entity);
	}

	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
	public void saveOrUpdate(Collection<T> entities){
		getHibernateTemplate().saveOrUpdateAll(entities);
	}
	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public void delete(Class<T> clazz, Serializable id) {
		delete(load(clazz, id));
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void clear() {
		getHibernateTemplate().clear();
	}

	public List<T> findByLike(Class<T> entityClass, String fieldName,
			String fieldValue) {
		return (List<T>) this.find("From " + entityClass.getName() + " WHERE "
				+ fieldName + " like '" + fieldValue + "'");
	}

	public List<T> findByLike(Class<T> entityClass, String fieldName,
			String fieldValue, String orderFieldName) {
		return (List<T>) this.find("From " + entityClass.getName() + " WHERE "
				+ fieldName + " like '" + fieldValue + "' Order by "
				+ orderFieldName);
	}

	public List<?> find(String hql, Object... values) {
		Assert.hasLength(hql);
		return getHibernateTemplate().find(hql, values);
	}

	public List<T> find(String hql) {
		return (List<T>) getHibernateTemplate().find(hql);
	}
	
	public List<?> findUnion(String hql){
		return getHibernateTemplate().find(hql);
	}

	public List<T> find(String hql, int index, int length) {
		final String queryString = hql;
		final int _first = index;
		final int _maxLength = length;
		List<T> voList = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {
					public List<T> doInHibernate(Session session)
							throws HibernateException {
						Query q = session.createQuery(queryString);
						q.setMaxResults(_maxLength);
						q.setFirstResult(_first);
						List<T> page = q.list();
						return page;
					}
				});
		return voList;
	}

	public int getRecordCount(String queryString) {
		final String countQueryString = DaoUtil.getHQL4Count(queryString);
		Long count = (Long) getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query q = session.createQuery(countQueryString);
						return q.uniqueResult();
					}
				});
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}

	public int updateByHql(String hql) {
		final String hqlFinal = hql;
		Integer count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query q = session.createQuery(hqlFinal);
						return new Integer(q.executeUpdate());
					}
				});
		return count.intValue();
	}

	public T findFirstVO(String hql) {
		List<?> list = this.find(hql);
		if (list == null || list.size() == 0)
			return null;
		return (T) list.get(0);
	}

	public List<?> findWithSQL(final String sql) {
		List<?> list = (List<?>) this.getHibernateTemplate().execute(
				new HibernateCallback<List<?>>() {
					public List<?> doInHibernate(Session session)
							throws HibernateException {
						SQLQuery query = session.createSQLQuery(sql);
						List<?> children = query.list();
						return children;
					}
				});
		return list;
	}
}
