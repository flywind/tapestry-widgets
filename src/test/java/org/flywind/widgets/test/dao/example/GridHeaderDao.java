package org.flywind.widgets.test.dao.example;

import java.util.List;

import org.flywind.widgets.test.dao.base.FBaseDao;
import org.flywind.widgets.test.entities.example.GridHeader;

public interface GridHeaderDao extends FBaseDao<GridHeader>{
	
	public List<GridHeader> getGridHeader(String tableName, String customerCode);
	
	public GridHeader getGridHeader(String tableName, String field, String customerCode);

}
