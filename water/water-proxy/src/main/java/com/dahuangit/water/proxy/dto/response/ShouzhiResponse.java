package com.dahuangit.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

/**
 * 收支响应
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:12:35
 */
public class ShouzhiResponse extends Response {

	private List<ShouzhiInfo> shouzhiInfos = new ArrayList<ShouzhiInfo>();

	public List<ShouzhiInfo> getShouzhiInfos() {
		return shouzhiInfos;
	}

	public void setShouzhiInfos(List<ShouzhiInfo> shouzhiInfos) {
		this.shouzhiInfos = shouzhiInfos;
	}

}
