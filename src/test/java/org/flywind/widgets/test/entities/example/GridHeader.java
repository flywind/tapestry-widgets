package org.flywind.widgets.test.entities.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.flywind.widgets.test.entities.base.FBase;

@Entity
@Table(name="td_d_grid_header")
public class GridHeader extends FBase {

	private static final long serialVersionUID = 4842578860532959764L;

	/**
	 * datagrid表头field的值
	 */
	private String field;
	
	/**
	 * datagrid表头title的值
	 */
	private String titleCn;
	
	/**
	 * datagrid表头title的值
	 */
	private String titleEn;
	
	/**
	 * 表的名字
	 */
	private String tableName;

	@Column(name="field",length=100)
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Column(name="table_name",length=100)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name="title_zh_cn",length=100)
	public String getTitleCn() {
		return titleCn;
	}

	public void setTitleCn(String titleCn) {
		this.titleCn = titleCn;
	}

	@Column(name="title_en",length=100)
	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	
}
