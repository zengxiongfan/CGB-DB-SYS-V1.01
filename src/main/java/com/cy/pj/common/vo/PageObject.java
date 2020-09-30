package com.cy.pj.common.vo;
import java.io.Serializable;
import java.util.List;
/**
 * VO:Value Object
 * 通过此对象封装分页数据
 * @author Administrator
 * @param <T>
 */
public class PageObject<T> implements Serializable{
	private static final long serialVersionUID = -5182062012834261998L;
	/**总记录数*/
	private Integer rowCount;
	/**当前页记录*/
	private List<T> records;
	/**总页数*/
	private Integer pageCount;
	/**当前页码值*/
	private Integer pageCurrent;
	/**页面大小*/
	private Integer pageSize=3;
	public PageObject() {
	}
	public PageObject(Integer rowCount, List<T> records,Integer pageCurrent, Integer pageSize) {
		super();
		this.rowCount = rowCount;
		this.records = records;
		this.pageCount = (rowCount-1)/pageSize+1;
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}







