package com.dahuangit.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

/**
 * 预警响应类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:21:40
 */
public class YujingResponse extends Response {

	private Integer nextPageId = 1;

	private List<YujingInfo> yujingInfos = new ArrayList<YujingInfo>();

	public List<YujingInfo> getYujingInfos() {
		return yujingInfos;
	}

	public void setYujingInfos(List<YujingInfo> yujingInfos) {
		this.yujingInfos = yujingInfos;
	}

	public Integer getNextPageId() {
		return nextPageId;
	}

	public void setNextPageId(Integer nextPageId) {
		this.nextPageId = nextPageId;
	}

}
