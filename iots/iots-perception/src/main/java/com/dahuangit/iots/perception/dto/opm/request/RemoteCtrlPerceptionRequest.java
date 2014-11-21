package com.dahuangit.iots.perception.dto.opm.request;

import com.dahuangit.base.dto.Request;

public class RemoteCtrlPerceptionRequest extends Request {
	private Integer perceptionId = null;

	private Integer switchStatus = null;

	private Integer rotateStatus = null;

	//操作类型，与通信文档的一样
	private Integer opt = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public Integer getRotateStatus() {
		return rotateStatus;
	}

	public void setRotateStatus(Integer rotateStatus) {
		this.rotateStatus = rotateStatus;
	}

	public Integer getOpt() {
		return opt;
	}

	public void setOpt(Integer opt) {
		this.opt = opt;
	}

}
