package com.dahuangit.base.dto;


public class PageTag {
	/** 不加分页参数的url */
	private String baseUrl = null;

	/** 当前页 */
	private Integer curPage = 1;

	/** 页大小(一页显示的大小) */
	private Integer pageSize = 25;

	/** 起始记录下标 */
	private Integer start = 0;

	/** 每次获取数量 */
	private Integer limit = null;

	/** 当前页的记录数量 */
	private Integer curPageCount = null;

	/** 总共记录数量 */
	private Integer totalCount = null;

	/** 总页数 */
	private Integer totalPage = null;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getTotalPage() {
		double d = (double) this.getTotalCount() / (double) this.getPageSize();
		return (int) Math.ceil(d);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		return (this.getCurPage() - 1) * this.getLimit();
	}

	public Integer getLimit() {
		return this.getPageSize();
	}

	public Integer getCurPageCount() {
		return curPageCount;
	}

	public void setCurPageCount(Integer curPageCount) {
		this.curPageCount = curPageCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
