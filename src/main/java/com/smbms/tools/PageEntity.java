package com.smbms.tools;

import java.util.List;

/**
 * 分页的工具类
 * @author 元大神
 *
 */
public class PageEntity {

	/**当前页码,从页面上获取*/
	private Integer pageIndex = 1;
	/**页面容量,从页面上获取*/
	private Integer pageSize = 2;
	/**符合条件的总记录数*/
	private Integer totalCount;
	/**总页码*/
	private Integer totalPage;
	/**符合条件的数据集合*/
	private List<?> dataList;

	
	
	
	
	
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		//符合条件的总页码(totalPage),
		this.totalPage = this.totalCount%this.pageSize==0 
							? this.totalCount/this.pageSize
							: this.totalCount/this.pageSize +1;
	}
	
	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	
	
	
	
}
