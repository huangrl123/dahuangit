package com.dahuangit.iots.perception.dto.request;

import com.dahuangit.base.dto.Request;

public class RemoteCtrlPerceptionRequest extends Request {
	private Integer perceptionId = null;

	private Integer opt = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public Integer getOpt() {
		return opt;
	}

	public void setOpt(Integer opt) {
		this.opt = opt;
	}

}
