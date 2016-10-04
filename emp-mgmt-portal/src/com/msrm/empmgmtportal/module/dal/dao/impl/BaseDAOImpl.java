package com.msrm.empmgmtportal.module.dal.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.msrm.empmgmtportal.module.dal.dao.BaseDAO;

public class BaseDAOImpl<T> implements BaseDAO<T> {

	@Override
	public Integer insert(T t) {
		try (SessionFactory sf = new Configuration().configure().buildSessionFactory();
				Session s = sf.getCurrentSession();) {
			Transaction tx = s.beginTransaction();
			try {
				Integer id = (Integer) s.save(t);
				tx.commit();
				return id;
			} catch (Throwable e) {
				tx.rollback();
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void update(T t) {
		try (SessionFactory sf = new Configuration().configure().buildSessionFactory();
				Session s = sf.getCurrentSession();) {
			Transaction tx = s.beginTransaction();
			try {
				s.saveOrUpdate(t);
				tx.commit();
			} catch (Throwable e) {
				tx.rollback();
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(T t) {
		try (SessionFactory sf = new Configuration().configure().buildSessionFactory();
				Session s = sf.getCurrentSession();) {
			Transaction tx = s.beginTransaction();
			try {
				s.delete(t);
				tx.commit();
			} catch (Throwable e) {
				tx.rollback();
				e.printStackTrace();
			}
		}
	}

	@Override
	public T selectById(Class<T> type, int id) {
		try (SessionFactory sf = new Configuration().configure().buildSessionFactory();
				Session s = sf.getCurrentSession();) {
			return s.get(type, id);
		}
	}

	@Override
	public List<T> selectAll(Class<T> type) {
		List<T> result = new ArrayList<>();
		try (SessionFactory sf = new Configuration().configure().buildSessionFactory();
				Session s = sf.getCurrentSession();) {
			
			// defining builder object to construct SQL
			CriteriaBuilder builder = s.getCriteriaBuilder();

			// for safe-type operation, creating criteriaQuery object with
			// object type
			CriteriaQuery<T> query = builder.createQuery(type);

			// setting table type that is going to be returned when completion
			// of execution of query
			Root<T> t = query.from(type);

			// setting from clause of SELECT query
			query.select(t);

			// passing the constructed query to execute
			Query<T> q = s.createQuery(query);
			
			// fetching the result, fired query against data source
			List<T> fetchResult = q.getResultList();
			
			if (fetchResult != null && !fetchResult.isEmpty()) {
				result.addAll(fetchResult);
			}
		}
		return result;
	}

}
