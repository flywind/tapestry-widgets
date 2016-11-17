package org.flywind.widgets.test.common.result;

import java.util.List;

import org.flywind.widgets.test.common.utils.JSONSerializer;
import org.flywind.widgets.test.entities.base.FBase;


/**
 * EasyUI DataGrid模型.
 */
@SuppressWarnings("serial")
public class Grid implements java.io.Serializable {


	
	private Long total = 0L;
	private String rows;
	
	public void setRows(List<? extends FBase> data) {
		this.rows = JSONSerializer.serialize(data);
	}
	/**
	 * @return the rows
	 */
	public String getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}
	/**
	 * 可选字段，主要用在参数等版本
	 */
	private String version;

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
}
