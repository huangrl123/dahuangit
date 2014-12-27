package com.dahuangit.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

/**
 * 获取楼栋列表响应类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午3:58:22
 */
public class GetLdListResponse extends Response {

	private List<LdInfo> ldInfos = new ArrayList<LdInfo>();

	public List<LdInfo> getLdInfos() {
		return ldInfos;
	}

	public void setLdInfos(List<LdInfo> ldInfos) {
		this.ldInfos = ldInfos;
	}

}
