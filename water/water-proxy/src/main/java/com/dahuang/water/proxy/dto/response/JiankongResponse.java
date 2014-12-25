package com.dahuang.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

/**
 * 监控信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月25日 下午4:23:59
 */
public class JiankongResponse extends Response {

	private List<JiankongInfo> jiankongInfos = new ArrayList<JiankongInfo>();

	public List<JiankongInfo> getJiankongInfos() {
		return jiankongInfos;
	}

	public void setJiankongInfos(List<JiankongInfo> jiankongInfos) {
		this.jiankongInfos = jiankongInfos;
	}

}
