package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.opm.request.OpRequest;

public class PerceptionParamOpRequest extends OpRequest {
	/** 感知端设备主键ID */
	private Integer pId = null;

	/** 知端设备参数参数键 */
	private Integer paramKey = null;

	/** 感知端设备参数参数值 */
	private Integer paramValue = null;

	private String machineAddr = null;

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Integer getParamValue() {
		return paramValue;
	}

	public void setParamValue(Integer paramValue) {
		this.paramValue = paramValue;
	}

	public String getMachineAddr() {
		return machineAddr;
	}

	public void setMachineAddr(String machineAddr) {
		this.machineAddr = machineAddr;
	}

	public Integer getParamKey() {
		return paramKey;
	}

	public void setParamKey(Integer paramKey) {
		this.paramKey = paramKey;
	}

}
