package com.dahuangit.iots.app.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Request;
import com.dahuangit.base.dto.Response;

/**
 * 获取感知端列表请求类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月10日 上午9:46:01
 */
public class AppGetPerceptionListRequest extends Request {

	/** 用户id */
	private Integer userId = null;

	/** 本地感知端id的最大值 */
	private Integer localMaxPerceptionId = null;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getLocalMaxPerceptionId() {
		return localMaxPerceptionId;
	}

	public void setLocalMaxPerceptionId(Integer localMaxPerceptionId) {
		this.localMaxPerceptionId = localMaxPerceptionId;
	}

}
