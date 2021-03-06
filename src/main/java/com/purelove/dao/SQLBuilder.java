package com.purelove.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purelove.util.StringUtils;

public class SQLBuilder {

	private final static Logger log = LoggerFactory.getLogger(SQLBuilder.class);

	public static Object[] insertSQL(Object obj) {
		String tablename = BeanProcessor.getTableName(obj);
		StringBuffer colSB = new StringBuffer();
		StringBuffer parSB = new StringBuffer();
		List<Object> param = new ArrayList<Object>();
		try{
		List<Field> fields = BeanProcessor.findAllField(obj);
		for (int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			colSB.append(BeanProcessor.getFieldName(field));
			parSB.append("?");
			if (i < fields.size() - 1) {
				colSB.append(",");
				parSB.append(",");
			}
			param.add(field.get(obj));
		}
		}catch(IllegalAccessException e){
			log.error("IllegalAccessException", e);
			return null;
		}
		String insert = "insert into " + tablename + " (" + colSB.toString()
				+ " ) " + " values (" + parSB.toString() + ")";
		log.debug("insertSQL : " + insert);

		return new Object[] { insert, param.toArray()};
	}

	// /id...
	public static Object[] updateSQL(Object obj){
		String tablename = BeanProcessor.getTableName(obj);

		StringBuffer colSB = new StringBuffer();
		StringBuffer parSB = new StringBuffer();
		List<Object> param = new ArrayList<Object>();
		try{
			List<Field> fields = BeanProcessor.findAllField(obj);
			
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				colSB.append(BeanProcessor.getFieldName(field));
				colSB.append(" = ?");
				if (i < fields.size() - 1) {
					colSB.append(",");
				}
				param.add(field.get(obj));
			}
			// 拿到主键列表
			List<Field> primaryKeys = BeanProcessor.getAllPrimaryKeys(obj
					.getClass());
			// 如果没有主键
			if (primaryKeys.isEmpty()) {
				throw new IllegalArgumentException("key value for table '"
						+ tablename + "' cannot be null.");
			} else {
				for (int i = 0; i < primaryKeys.size(); i++) {
					Field field = primaryKeys.get(i);
					parSB.append(BeanProcessor.getFieldName(field));
					parSB.append(" = ?");
					if (i < primaryKeys.size() - 1) {
						parSB.append(" AND ");
					}
					param.add(field.get(obj));
				}
			}
		}catch(IllegalAccessException e){
			log.error("Failed to get value",e);
		}
		String update = "UPDATE " + tablename + " SET " + colSB.toString() + " "
				+ "WHERE " + parSB.toString() + "";
		log.debug("updateSQL : " + update);
		return new Object[] { update, param.toArray() };

	}

	public static Object[] deleteSQL(Object obj){
		String tablename = BeanProcessor.getTableName(obj);
		StringBuffer parSB = new StringBuffer();
		List<Object> param = new ArrayList<Object>();
		// 拿到主键列表
		try{
		List<Field> primaryKeys = BeanProcessor.getAllPrimaryKeys(obj
				.getClass());
		if (primaryKeys.isEmpty()) {
			throw new IllegalArgumentException("key value for table '"
					+ tablename + "' cannot be null.");
		} else {
			for (int i = 0; i < primaryKeys.size(); i++) {
				Field field = primaryKeys.get(i);
				parSB.append(BeanProcessor.getFieldName(field));
				parSB.append(" = ?");
				if (i < primaryKeys.size() - 1) {
					parSB.append(" AND ");
				}
				param.add(field.get(obj));
			}
		}
		}catch(IllegalAccessException e){
			log.error("Failed to get value",e);
		}

		String delete = "DELETE FROM " + tablename + " WHERE "
				+ parSB.toString();
		log.debug("deleteSQL : " + delete);
		return new Object[] { delete, param.toArray() };
	}
	
	public static String findByIdSql(Class<?> obj) {
		String tableName = BeanProcessor.getTableName(obj);
		List<Field> primaryKeys = BeanProcessor.getAllPrimaryKeys(obj);

		StringBuffer sql = new StringBuffer();

		sql.append("select * from ");
		sql.append(tableName);
		sql.append(" where ");
		
		for(int i = 0; i < primaryKeys.size(); i++){
			Field field = primaryKeys.get(i);
			sql.append(BeanProcessor.getFieldName(field) + "= ? ");
			if(i < primaryKeys.size() -1){
				sql.append(" and ");
			}

		}
		log.debug("genarte findById SQL: " + sql.toString());
		return sql.toString();

	}
	
	public static String countAll(Class<?> clazz){
		String tableName = BeanProcessor.getTableName(clazz);
		if(StringUtils.isEmpty(tableName)){
			return null;
		}
		String sql = "select count(*) from "+tableName;
		
		log.debug("genarte countAll SQL: " + sql.toString());
		
		return sql;
	}
}
