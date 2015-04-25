package com.dahuangit.water.proxy.dto.response;

public class PageBean {
	private Integer totalPage = 0;

	private Integer curPage = 0;

	private Integer nextPage = 0;

	private Integer prevPage = 0;

	public PageBean(Integer curPage, Integer totalPage) {
		this.curPage = curPage;
		this.totalPage = totalPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getNextPage() {
		return this.curPage == 0 ? 0 : curPage + 1;
	}

	public Integer getPrevPage() {
		return this.curPage == 0 ? 0 : curPage - 1;
	}

}
