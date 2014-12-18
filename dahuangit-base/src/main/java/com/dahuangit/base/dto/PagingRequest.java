package com.dahuangit.base.dto;


/**
 * 分页请求(非ajax分页请求)
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月18日 下午2:33:31
 */
public class PagingRequest extends Request {

	private PageTag page = new PageTag();

	public PageTag getPage() {
		return page;
	}

	public void setPage(PageTag page) {
		this.page = page;
	}

}
