package org.flywind.widgets.core.dao;

import java.io.Serializable;

/**
 * <p>分页参数对象</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FPage implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4536458165540069462L;

	/**
	 * 默认一页显示的记录(行)数
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 每页显示的记录(行)数
	 */
	private int pageSize;
	
	/**
	 * 当前显示第几页
	 */
	private int pageNumber = 1;
	
	/**
	 * 总页数
	 */
	private int pageCount = 0;
	
	/**
	 * 总记录数
	 */
	private int rowCount = 0;
	
	/**
	 * 按哪个字段排序
	 */
	private String sortName;
	
	/**
	 * 排序方式(升序ASC， 降序DESC)
	 */
	private String sortOrder = "ASC";
	
	/**
	 * 无参构造方法，默认一页显示10条记录
	 */
	public FPage(){
		this.pageSize = DEFAULT_PAGE_SIZE;
	}
	
	/**
	 * 可以指定当前显示第几页的构造方法，默认一页显示10条记录
	 * 
	 * @param pageNow
	 * 			当前显示第几页
	 */
	public FPage(int pageNumber) {
		this.pageNumber = pageNumber;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}
	
	/**
	 * 可以指定当前显示第几页以及第页显示的记录数
	 * 
	 * @param pageNow
	 * 			当前显示第几页
	 * @param pageSize
	 * 			每页显示的记录数
	 */
	public FPage(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
	    this.pageSize = pageSize;
	}

	/**
	 * 获取每页显示的记录(行)数
	 * 
	 * @return
	 * 			每页显示的记录(行)数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示的记录(行)数
	 * 	·	
	 * @param pageSize
	 * 			每页显示的记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前显示第几页
	 * 
	 * @return
	 * 			页数
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 设置当前显示第几页
	 * 
	 * @param pageNow
	 * 			页数
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 * 			总页数
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 设置总页数
	 * 
	 * @param pageCount
	 * 			总页数
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return
	 * 			总记录数
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * 设置总记录数
	 * 
	 * @param rowCount
	 * 			总记录数
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * 获取排序字段的名称
	 * 
	 * @return
	 * 			字段名称
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * 设置排序字段名称
	 * 
	 * @param sortName
	 * 			字段名称
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	/**
	 * 获取排序方式(升序ASC， 降序DESC)
	 * 
	 * @return
	 * 			排序方式
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * 设置排序方式(升序ASC， 降序DESC)
	 * @param sortOrder
	 * 			排序方式
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	/**
	 * 当分页参数值异常时，设置默认值
	 */
	public void setDefaultValue() {
		if (pageNumber <= 0) {
			this.pageNumber = 1;
		}
		if (pageSize <= 0) {
			this.pageSize = DEFAULT_PAGE_SIZE;
		}
		if (rowCount < 0) {
			this.rowCount = 0;
		}
		this.pageCount = (int) Math.ceil((double)this.rowCount / (double)this.pageSize);
		if (this.pageCount == 0) {
			this.pageCount = 1;
		}
		if (this.pageNumber > this.pageCount) {
			this.pageNumber = this.pageCount;
		}
	}

}
