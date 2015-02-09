package com.dahuangit.iots.perception.dto.request;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传参数
 * 
 * @author 大黄
 * 
 *         2015年1月20日上午10:23:55
 */
public class ParamInfoList {

	private List<ParamInfo> paramInfos = new ArrayList<ParamInfo>();

	public List<ParamInfo> getParamInfos() {
		return paramInfos;
	}

	public void setParamInfos(List<ParamInfo> paramInfos) {
		this.paramInfos = paramInfos;
	}

}
