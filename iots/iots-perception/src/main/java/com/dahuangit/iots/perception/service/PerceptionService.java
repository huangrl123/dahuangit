package com.dahuangit.iots.perception.service;

import com.dahuangit.base.dto.ComboboxData;
import com.dahuangit.base.dto.opm.response.PageQueryResult;
import com.dahuangit.iots.perception.dto.opm.request.FindPerceptionRuntimeLogByPageReq;
import com.dahuangit.iots.perception.dto.opm.request.RemoteCtrlPerceptionRequest;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionOpResponse;
import com.dahuangit.iots.perception.dto.opm.response.PerceptionRuntimeLogResponse;
import com.dahuangit.iots.perception.tcpserver.frame.PerceptionFrame;

public interface PerceptionService {

	PageQueryResult<PerceptionOpResponse> findPerceptionByPage(Integer start, Integer limit);

	public void remoteCtrlPerception(RemoteCtrlPerceptionRequest req) throws Exception;

	/**
	 * 添加日志
	 * 
	 * @param addr
	 * @param opt
	 * @param param
	 *            感知端设备参数,单一参数远程控制类传0，例如远程开，远程正转
	 * @param value
	 *            实际值
	 */
	public void addPerceptionRuntimeLog(String addr, int opt, int param, int value);

	public PageQueryResult<PerceptionRuntimeLogResponse> findPerceptionRuntimeLogByPage(
			FindPerceptionRuntimeLogByPageReq req);

	public ComboboxData getPerceptionParamValueListByParam(Integer paramId);

	public void saveLog(PerceptionFrame frame);
}
