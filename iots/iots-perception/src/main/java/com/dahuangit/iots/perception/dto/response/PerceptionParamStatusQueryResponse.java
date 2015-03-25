package com.dahuangit.iots.perception.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;
import com.dahuangit.iots.perception.dto.request.ParamInfo;

/**
 * 设备状态查询响应类
 * 
 * @author 大黄
 * 
 *         2015年1月22日下午10:24:07
 */
public class PerceptionParamStatusQueryResponse extends Response {

	/** 设备id */
	private Integer perceptionId = null;

	/** 是否在线状态 */
	private boolean isOnline = true;

	/** 在线状态描述 */
	private String onlineStatusDesc = null;

	/** 最后通信时间 */
	private String lastCommTime = null;

	/** 设备地址 */
	private String perceptionAddr = null;

	/** 设备名称 */
	private String perceptionName = null;

	/** 设备类型id */
	private Integer perceptionTypeId = null;

	/** 设备类型名称 */
	private String perceptionTypeName = null;

	/** 告警参数状态列表 */
	private List<ParamInfo> warningParamInfos = new ArrayList<ParamInfo>();

	/** 控制参数状态列表 */
	private List<ParamInfo> ctrlParamInfos = new ArrayList<ParamInfo>();

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getLastCommTime() {
		return lastCommTime;
	}

	public void setLastCommTime(String lastCommTime) {
		this.lastCommTime = lastCommTime;
	}

	public List<ParamInfo> getWarningParamInfos() {
		return warningParamInfos;
	}

	public void setWarningParamInfos(List<ParamInfo> warningParamInfos) {
		this.warningParamInfos = warningParamInfos;
	}

	public List<ParamInfo> getCtrlParamInfos() {
		return ctrlParamInfos;
	}

	public void setCtrlParamInfos(List<ParamInfo> ctrlParamInfos) {
		this.ctrlParamInfos = ctrlParamInfos;
	}

	public String getOnlineStatusDesc() {
		return onlineStatusDesc;
	}

	public void setOnlineStatusDesc(String onlineStatusDesc) {
		this.onlineStatusDesc = onlineStatusDesc;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public String getPerceptionName() {
		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public String getPerceptionTypeName() {
		return perceptionTypeName;
	}

	public void setPerceptionTypeName(String perceptionTypeName) {
		this.perceptionTypeName = perceptionTypeName;
	}

}
