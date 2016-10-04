package com.msrm.empmgmtportal.module.dal.dao;

import java.util.List;

public interface BaseDAO<T> {

	Integer insert(T t);

	void update(T t);

	void delete(T t);

	T selectById(Class<T> type, int id);

	List<T> selectAll(Class<T> type);

}
