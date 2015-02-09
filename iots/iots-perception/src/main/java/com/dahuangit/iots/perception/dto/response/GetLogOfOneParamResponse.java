package com.dahuangit.iots.perception.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

/**
 * 获取某个具体参数下的日志列表
 * 
 * @author 大黄
 * 
 *         2015年1月23日下午2:38:08
 */
public class GetLogOfOneParamResponse extends Response {

	private List<LogOfOneParam> logs = new ArrayList<LogOfOneParam>();

	public List<LogOfOneParam> getLogs() {
		return logs;
	}

	public void setLogs(List<LogOfOneParam> logs) {
		this.logs = logs;
	}

}
