package org.flywind.widgets.test.dao.example.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.flywind.widgets.test.dao.base.AbstractFBaseDao;
import org.flywind.widgets.test.dao.example.GridHeaderDao;
import org.flywind.widgets.test.entities.example.GridHeader;
import org.springframework.stereotype.Repository;

@Repository
public class GridHeaderDaoImpl extends AbstractFBaseDao<GridHeader> implements GridHeaderDao {
	
	public List<GridHeader> getGridHeader(String tableName, String customerCode){
		StringBuffer hql = new StringBuffer("from GridHeader");
		hql.append(" where tableName =:tableName");
		hql.append(" and customerCode =:customerCode");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", tableName);
		params.put("customerCode", customerCode);
		
		return this.query(hql.toString(), params);
	}
	
	public GridHeader getGridHeader(String tableName, String field, String customerCode){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuffer hql = new StringBuffer("from GridHeader");

		hql.append(" where tableName =:tableName");
		hql.append(" and customerCode =:customerCode");
		hql.append(" and field =:field");

		params.put("tableName", tableName);
		params.put("customerCode", customerCode);
		params.put("field", field);
		
		List<GridHeader> res = this.query(hql.toString(), params);
		if(res.size() > 0){
			GridHeader gh = res.get(0);
			return gh;
		}
		
		return null;
	}

}
