package com.dahuang.water.proxy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.base.dto.Response;

public class LoginResponse extends Response {

	private List<PowerInfo> powerInfos = new ArrayList<PowerInfo>();

	public List<PowerInfo> getPowerInfos() {
		return powerInfos;
	}

	public void setPowerInfos(List<PowerInfo> powerInfos) {
		this.powerInfos = powerInfos;
	}

}
